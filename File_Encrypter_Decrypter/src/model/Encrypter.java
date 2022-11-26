package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;


public class Encrypter {
	
	private String passwordAlgorithm;
	private String encoder;
	private byte[] createdSalt;
	
	
	public Encrypter(String passwordAlgorithm, String encoder) {
		this.passwordAlgorithm = passwordAlgorithm;
		this.encoder = encoder;
	}
	
	public SecretKey generateKeyFromPassword(String password, int saltBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		byte[] salt = generateSalt(saltBytes);	
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance(passwordAlgorithm);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), encoder);
		
		return key;
	}
	
	private SecretKey generateKeyForDecryption(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {	
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance(passwordAlgorithm);
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
		SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), encoder);
		
		return key;
	}
	
	
	
	public IvParameterSpec generateIV() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		return new IvParameterSpec(iv);
	}
	
	public byte[] generateSalt(int saltBytes) {
		byte[] salt = new byte[saltBytes];
		new SecureRandom().nextBytes(salt);
		createdSalt = salt;		
		return salt;
	}
	
	public byte[] encryptFile(String algorithm, SecretKey key, IvParameterSpec iv, File inputFile) throws IOException, NoSuchPaddingException,
		    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		
			byte[] fileContent = Files.readAllBytes(Paths.get(inputFile.getAbsolutePath()));
		
		
		    Cipher cipher = Cipher.getInstance(algorithm);
		    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
		    byte[] outputBytes = cipher.doFinal(fileContent);		    
		    byte[] outputBytesWithIvAndSalt = ByteBuffer.allocate(iv.getIV().length + createdSalt.length + outputBytes.length)
		    		.put(iv.getIV()).put(createdSalt).put(outputBytes).array();
		    
		    
		    return outputBytesWithIvAndSalt;
	}

	public byte[] decryptFile(String algorithm, String password, byte[] fileContent) throws IOException, NoSuchPaddingException,
    NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(fileContent);
		
		byte[] iv = new byte[16];
		byteBuffer.get(iv);
		
		byte[] salt = new byte [16];
		byteBuffer.get(salt);		
		
		byte[] remainder = new byte[byteBuffer.remaining()];
		byteBuffer.get(remainder);
		
		SecretKey key = generateKeyForDecryption(password, salt);
		
	    Cipher cipher = Cipher.getInstance(algorithm);
	    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
	    byte[] outputBytes = cipher.doFinal(remainder);
	    
	    return outputBytes;
	}

}

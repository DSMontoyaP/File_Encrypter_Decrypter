package model;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.InputMismatchException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Crypter {
	
	private File fileToUse;
	
	private String password;
	
	private Encrypter encrypter;
	
	private HashComputer hasher;
		
	public Crypter() {
		encrypter = new Encrypter("PBKDF2WithHmacSHA256", "AES");
		hasher = new HashComputer();
	}
	
	
	public void setFile(File file) {
		fileToUse = file;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void encryptFile() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IOException {
		
		//Generates random key based on given password and an Initialization vector
		SecretKey key = encrypter.generateKeyFromPassword(password, 16);
		IvParameterSpec iv = encrypter.generateIV();
		
		//Encrypts the file using AES/CBC/PKCS5Padding with previously created key and iv
		byte[] file = encrypter.encryptFile("AES/CBC/PKCS5Padding", key, iv, fileToUse);
		
		File outputFile1 = new File("Encrypted.enc");
		outputFile1.createNewFile();
		
		//Writes encrypted content to new file
		Files.write(Paths.get(outputFile1.getAbsolutePath()), file);
		
		//Reads bytes of original file and creates hash SHA-256
		byte[] fileContent = Files.readAllBytes(Paths.get(fileToUse.getAbsolutePath()));
		String hash256 = hasher.hashSHA256(fileContent);
		
		//Appends hash to output file, to sign it
		Files.write(Paths.get(outputFile1.getAbsolutePath()), hash256.getBytes(), StandardOpenOption.APPEND);
		
	}


	public String decryptFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {	

		//Separates encrypted content from hash signature
		byte[] encryptedSignedContent = Files.readAllBytes(Paths.get(fileToUse.getAbsolutePath()));		
		ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedSignedContent);
		
		byte[] fileContent = new byte[encryptedSignedContent.length - 64];
		byteBuffer.get(fileContent);
		
		byte[] signedHash = new byte[64];
		byteBuffer.get(signedHash);		
		
		//Decrypts the leftover content
		byte[] decrypt = encrypter.decryptFile("AES/CBC/PKCS5Padding", password, fileContent);
		
		File outputFile = new File("DecryptedFile.dec");
		outputFile.createNewFile();
		
		
		//Writes output to file
		Files.write(Paths.get(outputFile.getAbsolutePath()), decrypt);
		
		
		//Gets hash from resulting decrypted file
		byte[] outfileContent = Files.readAllBytes(Paths.get(outputFile.getAbsolutePath()));
		String newHash = hasher.hashSHA256(outfileContent);
		String bytes256Original = new String(signedHash, StandardCharsets.UTF_8);
		
		
		//Compares hashes, returning an alert if they mismatch
		try {
			if(!newHash.equals(bytes256Original)) {
				throw new InputMismatchException();
			}
				
			return newHash + " " + bytes256Original;
		}catch(Exception e) {
			return "Alert";
		}
		
		
		
	}

}

package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class Crypter {
	
	private File fileToUse;
	
	private String password;
	
	private Encrypter encrypter;
	
	public Crypter() {
		encrypter = new Encrypter("PBKDF2WithHmacSHA256", "AES");
	}
	
	
	public void setFile(File file) {
		fileToUse = file;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void encryptFile() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IOException {
		
		SecretKey key = encrypter.generateKeyFromPassword(password, 16);
		IvParameterSpec iv = encrypter.generateIV();
		
		byte[] file = encrypter.encryptFile("AES/CBC/PKCS5Padding", key, iv, fileToUse);
		
		File outputFile1 = new File("Encrypted.dec");
		outputFile1.createNewFile();
		System.out.println(outputFile1.getAbsolutePath());
		
		Files.write(Paths.get(outputFile1.getAbsolutePath()), file);
		
	}


	public void decryptFile() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {	

		byte[] fileContent = Files.readAllBytes(Paths.get(fileToUse.getAbsolutePath()));
		
		
		byte[] decrypt = encrypter.decryptFile("AES/CBC/PKCS5Padding", password, fileContent);
		
		System.out.println("Decrypted: ");
		for(byte b : decrypt) {
			System.out.println(b);	
		}
		
		File outputFile = new File("DecryptedFile.dec");
		outputFile.createNewFile();
		System.out.println(outputFile.getAbsolutePath());
		
		Files.write(Paths.get(outputFile.getAbsolutePath()), decrypt);
		
		
	}

}

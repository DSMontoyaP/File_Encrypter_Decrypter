package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashComputer {
	
	//Hashes given file content with SHA-256
	public String hashSHA256(byte[] fileContent) throws NoSuchAlgorithmException {
		
		MessageDigest dig = MessageDigest.getInstance("SHA-256");
		byte[] hash = dig.digest(fileContent);
		return hexToString(hash);
	}	
	
	//Translates hash to a more easily readable string
    public String hexToString(byte[] content) {
        StringBuilder hexString = new StringBuilder(2 * content.length);
        for (int i = 0; i < content.length; i++) {
            String hex = Integer.toHexString(0xff & content[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
	

}

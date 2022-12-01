package controller;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Crypter;

public class MainController {

    @FXML
    private Label decryptedHash;

    @FXML
    private Label signedHashLabel;
	
    @FXML
    private Button encryptButton;
    
    @FXML
    private Button decryptButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Label selectedFileLabel;
    
    private Stage primaryStage;
    
    private Crypter crypter;
    
    private File selectedFile;
    
    
    public MainController(Stage primaryStage, Crypter crypter) {
    	this.primaryStage = primaryStage;
    	this.crypter = crypter;
    }
    
    
    
    @FXML
    void Encrypt(ActionEvent event) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IOException {
    	
    	if(!passwordTextField.getText().isBlank() && selectedFile != null){
    		crypter.setPassword(passwordTextField.getText());
    		crypter.setFile(selectedFile);
    		crypter.encryptFile();
    		
    		Alert a = new Alert(AlertType.INFORMATION);
    		a.setTitle("Success");
    		a.setContentText("File has been encrypted and saved into project folder");
    		a.setHeaderText("Success");
    		a.show();
    	}
    	
    	
    }
    
    @FXML
    void Decrypt(ActionEvent event) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IOException {
    	
    	if(!passwordTextField.getText().isBlank() && selectedFile != null && selectedFile.getName().contains(".enc")){
    		crypter.setPassword(passwordTextField.getText());
    		crypter.setFile(selectedFile);
    		String result = crypter.decryptFile();
    		
    		
    		if(!result.equals("Alert")) {
	    		String[] hashes = result.split(" ");
	    		decryptedHash.setText(hashes[0]);
	    		signedHashLabel.setText(hashes[1]);
	    		
	    		decryptedHash.setTextFill(Color.GREEN);
	    		signedHashLabel.setTextFill(Color.GREEN);	
	    		
				Alert a = new Alert(AlertType.INFORMATION, "File has been decrypted successfully");
				a.setTitle("Success");
	    		a.setHeaderText("");
	    		a.show();
	    		
    		}else {
    				Alert a = new Alert(AlertType.WARNING, "Mismatch in the signature detected, file has been tampered with");
    				a.show();
    		}
    	}else {
			if(!selectedFile.getName().contains(".enc")) {
				Alert a = new Alert(AlertType.ERROR, "File does not have a .enc extension");
				a.setTitle("Not decrypted");
	    		a.setHeaderText("");
	    		a.show();
			}
    	}
    }

    @FXML
    void SelectFile(ActionEvent event) {
    	
    	try {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Select file to encrypt");
    	
    	fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        selectedFile = fileChooser.showOpenDialog(primaryStage);
    	
    	selectedFileLabel.setText(selectedFile.getName()); 
    	}catch(Exception e) {}
        
    }

}
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Crypter;

public class MainController {

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
    	}
    	
    	
    }
    
    @FXML
    void Decrypt(ActionEvent event) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, IOException {
    	if(!passwordTextField.getText().isBlank() && selectedFile != null){
    		crypter.setPassword(passwordTextField.getText());
    		crypter.setFile(selectedFile);
    		crypter.decryptFile();
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
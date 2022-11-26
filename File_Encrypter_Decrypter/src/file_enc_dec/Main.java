package file_enc_dec;

import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Crypter;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Crypter master = new Crypter();
        MainController controller = new MainController(primaryStage, master);
        fxmlLoader.setController(controller);

        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Encrypter and Decrypter");
        primaryStage.setResizable(false);

        primaryStage.show();
    }

	
	
	
	
}

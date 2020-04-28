package sample;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Controllers.MainController;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    @FXML
    private ImageView xmlLoaded;

    @FXML
    private ImageView xqueryLoaded;

    private MainController myMainController;

    private File xmlIconFile= new File("D:/BUREAU/el 9raya/MRI 1/Semestre2/Documents semi-structurés/Compte Rendu/Project1/xml-symbole-de-format-de-fichier_318-45852.jpg");

    private final String xmlIconFilePath=xmlIconFile.toURI().toString();

    private Image xmlIconImage = new Image(xmlIconFilePath);

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/MainScene.fxml"));
        Parent root = loader.load();
        myMainController =loader.getController();
        primaryStage.setTitle("Interpreter");
        primaryStage.setScene(new Scene(root, 1024, 620));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(xmlIconImage);
        primaryStage.centerOnScreen();
        primaryStage.show();
        xmlLoaded= myMainController.getXmlLoaded();
        xqueryLoaded= myMainController.getXqueryLoaded();
        xmlLoaded.setImage(myMainController.getRefusedImage());
        xqueryLoaded.setImage(myMainController.getRefusedImage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class HowToUse extends Application {

    private File xmlIconFile= new File("D:/BUREAU/el 9raya/MRI 1/Semestre2/Documents semi-structurés/Compte Rendu/Project1/xml-symbole-de-format-de-fichier_318-45852.jpg");
    private final String xmlIconFilePath=xmlIconFile.toURI().toString();
    private Image xmlIconImage = new Image(xmlIconFilePath);
    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(Class.class.getResource("HowToUseScene.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("How to use");
        primaryStage.setScene(new Scene(root, 500, 457));
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(xmlIconImage);
        primaryStage.centerOnScreen();
        stage=primaryStage;
        primaryStage.show();
    }

    public static Stage getStage(){
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

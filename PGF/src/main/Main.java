package main;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.java.DataController;

public class Main extends Application {

    @FXML
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layouts/eyeballDeform.fxml"));

        FXMLLoader dataLoader = new FXMLLoader(getClass().getResource("layouts/data.fxml"));
        dataLoader.load();
        ((DataController) dataLoader.getController()).setStage(primaryStage);

        primaryStage.setTitle("Posterior Globe Flattening");
        primaryStage.setScene(new Scene(root, 1280,720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

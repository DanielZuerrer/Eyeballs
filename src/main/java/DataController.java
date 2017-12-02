package main.java;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.*;

public class DataController {

    @FXML
    private Pane mainContainer;
    @FXML
    private Text leftValue;
    @FXML
    private Text rightValue;

    public void translate(double offsetX, double offsetY) {
        mainContainer.setTranslateX(offsetX);
        mainContainer.setTranslateY(offsetY);
    }

    public void setLeftValue(double value) {
        double percent = (100 * value);
        leftValue.setText(String.format("%.1f%%", percent));
    }

    public void setRightValue(double value) {
        double percent = (100 * value);
        rightValue.setText(String.format("%.1f%%", percent));    }

    public void chooseImagePressed(ActionEvent actionEvent) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../layouts/eyeballDeform.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        EyeballDeformController controller = loader.getController();
        controller.setImage();
    }
}

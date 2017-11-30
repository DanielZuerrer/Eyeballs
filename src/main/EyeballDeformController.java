package main;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class EyeballDeformController {

    @FXML
    private EyeballController eyeballLeftController;
    @FXML
    private EyeballController eyeballRightController;
    @FXML
    private DataController dataPanelController;
    @FXML
    private ImageView imageView;

    @FXML
    public void initialize(){
        eyeballLeftController.translate(140,360);
        eyeballRightController.translate(740,360);
        eyeballLeftController.setColor(new Color(0.1, 0.82, 0.29, 1));
        eyeballRightController.setColor(new Color(0.81, 0.17, 0.16, 1));

        dataPanelController.translate(1350,0);

        eyeballLeftController.indicatorValueProperty().addListener((obs, oldValue, newValue) -> {
            dataPanelController.setLeftValue(Math.max(0, newValue.doubleValue()));
        });

        eyeballRightController.indicatorValueProperty().addListener((obs, oldValue, newValue) -> {
            dataPanelController.setRightValue(Math.max(0, newValue.doubleValue()));
        });
    }

    public void dragDropped(DragEvent dragEvent) {
        try {
            Dragboard board = dragEvent.getDragboard();
            List<File> phil = board.getFiles();
            FileInputStream fis;
            fis = new FileInputStream(phil.get(0));
            Image image = new Image(fis);
            imageView.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }    }

    public void dragOver(DragEvent dragEvent) {
        Dragboard board = dragEvent.getDragboard();
        if (board.hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }
}

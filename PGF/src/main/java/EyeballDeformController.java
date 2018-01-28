package main.java;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class EyeballDeformController {

    @FXML
    private EyeballController eyeballRightController;
    @FXML
    private EyeballController eyeballLeftController;
    @FXML
    private DataController dataPanelController;
    @FXML
    private ImageView imageView;


    @FXML
    public void initialize() {
        eyeballRightController.translate(250, 260);
        eyeballLeftController.translate(650, 260);
        eyeballRightController.setColor(new Color(0.1, 0.82, 0.29, 1));
        eyeballLeftController.setColor(new Color(0.81, 0.17, 0.16, 1));

        dataPanelController.translate(900, 0);

        eyeballRightController.indicatorValueProperty().addListener((obs, oldValue, newValue) -> {
            dataPanelController.setRightValue(Math.max(0, newValue.doubleValue()));
        });

        eyeballLeftController.indicatorValueProperty().addListener((obs, oldValue, newValue) -> {
            dataPanelController.setLeftValue(Math.max(0, newValue.doubleValue()));
        });
    }

    public void dragDropped(DragEvent dragEvent) {
        try {
            Dragboard board = dragEvent.getDragboard();
            List<File> files = board.getFiles();
            FileInputStream fis;
            fis = new FileInputStream(files.get(0));
            Image image = new Image(fis);
            imageView.setImage(image);
            centerImage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void dragOver(DragEvent dragEvent) {
        Dragboard board = dragEvent.getDragboard();
        if (board.hasFiles()) {
            dragEvent.acceptTransferModes(TransferMode.ANY);
        }
    }

    private void centerImage() {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
}

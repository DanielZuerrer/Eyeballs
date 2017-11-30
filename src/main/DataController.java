package main;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

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

    public void setLeftValue(double value){
        leftValue.setText("LEFT: " + value);
    }

    public void setRightValue(double value){
        rightValue.setText("RIGHT: " + value);
    }
}

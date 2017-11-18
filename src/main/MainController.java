package main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.transform.Translate;

public class MainController {

    @FXML
    private EyeballController eyeballLeftController;
    @FXML
    private EyeballController eyeballRightController;

    @FXML
    public void initialize(){
        eyeballLeftController.translate(340,360);
        eyeballRightController.translate(940,360);
    }
}

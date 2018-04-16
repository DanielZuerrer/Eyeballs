package main.java;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class EyeballController {

    @FXML
    private Pane translateContainer;
    @FXML
    private Pane rotateContainer;
    @FXML
    private Pane baseLineContainer;
    @FXML
    private Line baseLine;
    @FXML
    private Pane thicknessLineContainer;
    @FXML
    private Line thicknessLine;
    @FXML
    private Pane tortuosityLineContainer;
    @FXML
    private Line tortuosityLine;

    private Delta dragDelta = new Delta();
    private double rotation = 0;


    // ============ CONTAINER TRANSLATIONS ============
    public void onTranslateDragged(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {
            translateContainer.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
            translateContainer.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
            keepTranslateContainerInWindow();
        }
    }

    public void onTranslateScroll(ScrollEvent scrollEvent) {
        double rotationAngle = scrollEvent.getDeltaY() / 10.0;
        if (scrollEvent.isAltDown()) {
            rotationAngle =  rotationAngle / 5.0;
        }

        double rotationY = thicknessLineContainer.getLayoutY() + thicknessLine.getLayoutY();

        translateContainer.getTransforms().add(new Rotate(rotationAngle, 150, rotationY));
        rotation += rotationAngle;
    }

    public void keepTranslateContainerInWindow() {
        try {
            if (translateContainer.getLayoutX() < -100) translateContainer.setLayoutX(-100);
            if (translateContainer.getLayoutX() > 700) translateContainer.setLayoutX(700);
            if (translateContainer.getLayoutY() < -10) translateContainer.setLayoutY(-10);
            if (translateContainer.getLayoutY() > 655) translateContainer.setLayoutY(655);
        } catch (Exception ex) {
            System.out.println("Couldn't keep within boundary");
        }
    }

    public void onTranslateEntered(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
    }

    public void onTranslateExited(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
    }

    public void onTranslatePressed(MouseEvent mouseEvent) {
        dragDelta.x = translateContainer.getLayoutX() - mouseEvent.getSceneX();
        dragDelta.y = translateContainer.getLayoutY() - mouseEvent.getSceneY();
        ((Node) mouseEvent.getSource()).setCursor(Cursor.CLOSED_HAND);
    }

    public void onTranslateReleased(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
    }

    // ============ THICKNESS LINE TRANSLATIONS ============
    public void onThicknessEntered(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).setCursor(Cursor.HAND);

    }

    public void onThicknessExited(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).setCursor(Cursor.DEFAULT);
    }

    public void onThicknessPressed(MouseEvent mouseEvent){
        dragDelta.y = mouseEvent.getSceneY();
        ((Node) mouseEvent.getSource()).setCursor(Cursor.CLOSED_HAND);
        mouseEvent.consume();

    }
    public void onThicknessReleased(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
        mouseEvent.consume();
    }
    public void onThicknessDragged(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {
            double yOffset = mouseEvent.getSceneY() - dragDelta.y;
            double xOffset = mouseEvent.getSceneX() - dragDelta.x;

            double r = rotation * Math.PI / 180;

            double offset = Math.cos(r) * yOffset + Math.sin(r) * (-xOffset);

            thicknessLineContainer.setLayoutY(thicknessLineContainer.getLayoutY() + offset);
        }
        dragDelta.y = mouseEvent.getSceneY();
        dragDelta.x = mouseEvent.getSceneX();
        keepThicknessLineContainerInBounds();
        mouseEvent.consume();
        System.out.println(thicknessLineContainer.getLayoutY());
    }

    private void keepThicknessLineContainerInBounds() {
        if (thicknessLineContainer.getLayoutY() < 10) thicknessLineContainer.setLayoutY(10);
        if (thicknessLineContainer.getLayoutY() > 75) thicknessLineContainer.setLayoutY(75);
    }

    // ============ TORTUOSITY LINE TRANSLATIONS ============

    public void onTortuosityEntered(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).setCursor(Cursor.HAND);

    }

    public void onTortuosityExited(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).setCursor(Cursor.DEFAULT);
    }

    public void onTortuosityPressed(MouseEvent mouseEvent){
        dragDelta.y = mouseEvent.getSceneY();
        ((Node) mouseEvent.getSource()).setCursor(Cursor.CLOSED_HAND);
        mouseEvent.consume();

    }
    public void onTortuosityReleased(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
        mouseEvent.consume();
    }
    public void onTortuosityDragged(MouseEvent mouseEvent) {
        if (mouseEvent.isPrimaryButtonDown()) {
            double yOffset = mouseEvent.getSceneY() - dragDelta.y;
            double xOffset = mouseEvent.getSceneX() - dragDelta.x;

            double r = rotation * Math.PI / 180;

            double offset = Math.cos(r) * yOffset + Math.sin(r) * (-xOffset);

            tortuosityLineContainer.setLayoutY(tortuosityLineContainer.getLayoutY() + offset);
        }
        dragDelta.y = mouseEvent.getSceneY();
        dragDelta.x = mouseEvent.getSceneX();
        keepTortuosityLineContainerInBounds();
        mouseEvent.consume();
    }

    private void keepTortuosityLineContainerInBounds() {
        if (tortuosityLineContainer.getLayoutY() < 10) tortuosityLineContainer.setLayoutY(10);
        if (tortuosityLineContainer.getLayoutY() > 75) tortuosityLineContainer.setLayoutY(75);
    }


    class Delta {
        double x, y;
    }
}

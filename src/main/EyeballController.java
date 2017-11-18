package main;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class EyeballController {

    @FXML
    private Circle circle;
    @FXML
    private Line centerLine;
    @FXML
    private Pane translateContainer;
    @FXML
    private Pane rotateContainer;
    @FXML
    private Pane indicatorLine;

    private double initialSceneX, initialSceneY;

    public void onCircleDragged(MouseEvent mouseEvent) {
        double currentSceneX = mouseEvent.getSceneX();
        double currentSceneY = mouseEvent.getSceneY();

        if (mouseEvent.isPrimaryButtonDown()) {
            if (mouseEvent.isAltDown()) {

                double centerX = translateContainer.getTranslateX();
                double centerY = translateContainer.getTranslateY();

                double initialCenterToSceneX = initialSceneX - centerX;
                double initialCenterToSceneY = initialSceneY - centerY;

                double currentCenterToSceneX = currentSceneX - centerX;
                double currentCenterToSceneY = currentSceneY - centerY;

                double angle = Math.toDegrees(Math.atan2(currentCenterToSceneY, currentCenterToSceneX) - Math.atan2(initialCenterToSceneY, initialCenterToSceneX));

                rotateContainer.getTransforms().add(new Rotate(angle));
            } else {
                double offsetX = currentSceneX - initialSceneX;
                double offsetY = currentSceneY - initialSceneY;

                translate(offsetX, offsetY);
            }
        } else if (mouseEvent.isSecondaryButtonDown()) {
            double scaling = (initialSceneY - currentSceneY) / 500 + 1;
            translateContainer.getTransforms().add(new Scale(scaling, scaling));
        }

        initialSceneX = currentSceneX;
        initialSceneY = currentSceneY;
    }

    public void translate(double offsetX, double offsetY) {
        translateContainer.setTranslateX(translateContainer.getTranslateX() + offsetX);
        translateContainer.setTranslateY(translateContainer.getTranslateY() + offsetY);

        try {
        if (translateContainer.getTranslateX() < 0) translateContainer.setTranslateX(0);
        if (translateContainer.getTranslateX() > translateContainer.getScene().getWidth())
            translateContainer.setTranslateX(translateContainer.getScene().getWidth());
        if (translateContainer.getTranslateY() < 0) translateContainer.setTranslateY(0);
        if (translateContainer.getTranslateY() > translateContainer.getScene().getHeight())
            translateContainer.setTranslateY(translateContainer.getScene().getHeight());
        } catch (Exception ex) {
            System.out.println("Couldn't keep within boundary");
        }
    }

    public void onCircleEntered(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
    }

    public void onIndicatorEntered(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.HAND);
    }

    public void onMouseExited(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.DEFAULT);
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        Node sourceNode = (Node) mouseEvent.getSource();

        sourceNode.setCursor(Cursor.CLOSED_HAND);

        initialSceneX = mouseEvent.getSceneX();
        initialSceneY = mouseEvent.getSceneY();
    }

    public void onCircleReleased(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.OPEN_HAND);
    }

    public void onIndicatorDragged(MouseEvent mouseEvent) {

        double currentSceneY = mouseEvent.getSceneY();

        double offsetY = currentSceneY - initialSceneY;

        double indicatorOffset = indicatorLine.getTranslateY() + offsetY;
        if (indicatorOffset > 0 ) indicatorOffset = 0;
        if (indicatorOffset < -200 ) indicatorOffset = -200;

        indicatorLine.setTranslateY(indicatorOffset);

        initialSceneY = currentSceneY;

        System.out.println(indicatorLine.getTranslateY() / -200);
    }

    public void onIndicatorReleased(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).setCursor(Cursor.HAND);
    }

    public void onCircleScroll(ScrollEvent scrollEvent) {
        double scaling = scrollEvent.getDeltaY() / 500 + 1;
        translateContainer.getTransforms().add(new Scale(scaling, scaling));
    }
}

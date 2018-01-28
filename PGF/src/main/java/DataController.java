package main.java;

import com.sun.javafx.tk.Toolkit;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class DataController {

    @FXML
    private Pane mainContainer;

    private double leftNumberValue;
    @FXML
    private Text leftValue;
    private double rightNumberValue;
    @FXML
    private Text rightValue;
    @FXML
    private TextField patientNumber;
    @FXML
    private TextField commentField;

    private static Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private File csvFile;


    public void translate(double offsetX, double offsetY) {
        mainContainer.setTranslateX(offsetX);
        mainContainer.setTranslateY(offsetY);
    }

    public void setLeftValue(double value) {
        leftNumberValue = value;
        double percent = (100 * value);
        leftValue.setText(String.format("%.1f%%", percent));
    }

    public void setRightValue(double value) {
        rightNumberValue = value;
        double percent = (100 * value);
        rightValue.setText(String.format("%.1f%%", percent));
    }

    public void saveClicked(ActionEvent actionEvent) {
        String rightValue = String.format("%.3f", rightNumberValue);
        String leftValue = String.format("%.3f", leftNumberValue);

        String filename = "pat#" + patientNumber.getText() + "_pgf_R_" + rightValue + "_L_" + leftValue;
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(filename);
        clipboard.setContent(content);

        boolean imageSelected = saveImage(filename);

        if (imageSelected) {
            saveCSV();
        }

    }

    private void saveCSV() {
        try {

            if (csvFile == null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save CSV");
                fileChooser.setInitialFileName("PGF.csv");

                csvFile = fileChooser.showSaveDialog(stage);
            }

            if (csvFile == null) {
                return;
            }

            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile.getAbsoluteFile(), true));

            if (csvFile.length() == 0) {
                writer.write("PatNumber,R,L\n");
            }

            writer.write(patientNumber.getText() + "," + String.format("%.3f", rightNumberValue) + "," + String.format("%.3f", leftNumberValue) + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private boolean saveImage(String filename) {

        Group root = new Group();

        WritableImage snapshot = stage.getScene().snapshot(null);
        PixelReader pixelReader = snapshot.getPixelReader();
        snapshot = new WritableImage(pixelReader, 0, 0, 900, 720);
        root.getChildren().add(new ImageView(snapshot));

        Rectangle box = new Rectangle(880, 75);
        box.setFill(Color.WHITE);
        box.setX(10);
        box.setY(635);
        box.setStroke(Color.BLACK);
        root.getChildren().add(box);

        Text mainInfo = new Text();
        mainInfo.setText("Patient #" + patientNumber.getText() + ": PGF right " + rightValue.getText() + ", PGF left " + leftValue.getText());
        mainInfo.setX(15);
        mainInfo.setY(666);
        mainInfo.setFont(Font.font("Roboto", 28));
        root.getChildren().add(mainInfo);

        Text comment = new Text();
        comment.setText(commentField.getText());
        comment.setX(15);
        comment.setY(700);
        comment.setFont(Font.font("Roboto", 22));
        root.getChildren().add(comment);

        Text date = new Text();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        date.setText(now.format(formatter));
        date.setX(695);
        date.setY(700);
        date.setFont(Font.font("Roboto", 22));
        root.getChildren().add(date);

        Scene scene = new Scene(root, 900, 720);
        snapshot = scene.snapshot(null);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.setInitialFileName(filename + ".png");

        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", file);
            } catch (IOException e) {
            }
            return true;
        } else {
            return false;
        }


    }
}

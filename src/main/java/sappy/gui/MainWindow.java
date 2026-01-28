package sappy.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sappy.Sappy;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Sappy sappy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/UserIcon.png"));
    private Image sappyImage = new Image(this.getClass().getResourceAsStream("/images/SappyIcon.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Sappy instance */
    public void setSappy(Sappy s) {
        sappy = s;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Sappy's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = sappy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSappyDialog(response, sappyImage)
        );
        userInput.clear();
    }
}

package sappy.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * An immutable custom control using {@code HBox} to represent a dialog box in the GUI.
 * A {@code DialogBox} consists of an {@code ImageView} to represent the speaker's face
 * and a {@code Label} containing the text message from the speaker.
 * By default, the dialog box is aligned to the top-right.
 * It can be flipped to the top-left (bot style) using internal methods.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }


    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Factory method to create a dialog box for the user.
     * The resulting box is aligned to the right by default.
     *
     * @param text The user's input text.
     * @param img The user's profile image.
     * @return A {@code DialogBox} configured for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Factory method to create a dialog box for Sappy (the bot).
     * The resulting box is flipped so that it is aligned to the left.
     *
     * @param text The bot's response text.
     * @param img The bot's profile image.
     * @return A {@code DialogBox} configured for the bot.
     */
    public static DialogBox getSappyDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}

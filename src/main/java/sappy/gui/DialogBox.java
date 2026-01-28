package sappy.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Label text;
    private ImageView displayPicture;

    private DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);

        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);

        this.setAlignment(Pos.TOP_RIGHT);
        this.getChildren().addAll(text, displayPicture);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    /**
     * Factory method to create a dialog box for the user.
     * The resulting box is aligned to the right by default.
     *
     * @param s The user's input text.
     * @param i The user's profile image.
     * @return A {@code DialogBox} configured for the user.
     */
    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    /**
     * Factory method to create a dialog box for Sappy (the bot).
     * The resulting box is flipped so that it is aligned to the left.
     *
     * @param s The bot's response text.
     * @param i The bot's profile image.
     * @return A {@code DialogBox} configured for the bot.
     */
    public static DialogBox getSappyDialog(String s, Image i) {
        var db = new DialogBox(s, i);
        db.flip();
        return db;
    }
}

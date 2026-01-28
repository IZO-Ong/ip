package sappy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sappy.gui.MainWindow;

/**
 * A GUI for Sappy using FXML.
 */
public class Main extends Application {

    private final Sappy sappy = new Sappy();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("Sappy Chatbot");

            fxmlLoader.<MainWindow>getController().setSappy(sappy);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

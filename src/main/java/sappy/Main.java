package sappy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * A GUI for Sappy using JavaFX.
 */
public class Main extends Application {
    private Sappy sappy = new Sappy();

    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello, I'm Sappy! How can I help you?");
        Scene scene = new Scene(helloWorld, 400, 300);

        stage.setScene(scene);
        stage.setTitle("Sappy Chatbot");
        stage.show();
    }
}

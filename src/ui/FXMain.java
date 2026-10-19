package ui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX application entry point.
 *
 * Keeps the start-up wiring only: it loads the View from FXML (which in turn
 * attaches the Controller) and shows it on the primary Stage. All UI behaviour
 * lives in the Controller, and all data lives in the Model, so this class stays
 * a thin launcher - consistent with the MVC separation.
 */
public class FXMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));

        Scene scene = new Scene(root);
        URL css = getClass().getResource("app.css");
        if (css != null) {
            scene.getStylesheets().add(css.toExternalForm());
        }

        primaryStage.setTitle("Lab 5 - JavaFX MVC Form");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

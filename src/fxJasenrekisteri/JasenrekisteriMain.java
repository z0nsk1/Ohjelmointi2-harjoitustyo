package fxJasenrekisteri;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author z0nsk1
 * @version 13.2.2020
 *
 */
public class JasenrekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("Aloitusikkuna.fxml"));
            final Pane root = ldr.load();
            //final JasenrekisteriGUIController jasenrekisteriCtrl = (JasenrekisteriGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("jasenrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Jasenrekisteri");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}
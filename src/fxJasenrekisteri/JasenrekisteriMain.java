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
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("Aloitusikkuna.fxml"));
            final Pane root = (Pane)ldr.load();
            final JasenrekisteriGUIController jasenrekisteriCtrl = (JasenrekisteriGUIController)ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("jasenrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Jasenrekisteri");
            
            Joukkue joukkue = new Joukkue();
            jasenrekisteriCtrl.setJoukkue(joukkue);
            
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}
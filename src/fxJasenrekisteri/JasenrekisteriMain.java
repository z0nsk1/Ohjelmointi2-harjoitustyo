package fxJasenrekisteri;

import Jasenrekisteri.Joukkue;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Jonni ja Mikko
 * @version 6.5.2020
 */
public class JasenrekisteriMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaOhjelmaHarjoitukset.fxml"));
            final Pane root = (Pane)ldr.load();
            final HarjoitusController harjoitusCtrl = (HarjoitusController)ldr.getController();
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("jasenrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Jasenrekisteri");
            
            Joukkue joukkue = new Joukkue();
            harjoitusCtrl.setJoukkue(joukkue);

            harjoitusCtrl.lueTiedosto("MahottomatMestarit");  

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei kaytossa
     */
    public static void main(String[] args) {
        launch(args);
    }
}
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
            /*Parent root0 = FXMLLoader.load(getClass().getResource("Aloitusikkuna.fxml"));
            Scene skenes = new Scene(root0);
            Stage stagel = new Stage();
            stagel.setScene(skenes);
            stagel.show(); */
            
            
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("PaaOhjelmaHarjoitukset.fxml")); // Tahan kuuluu normaalisti PaaOhjelmaHarjoitukset.fxml
            final Pane root = (Pane)ldr.load();
            final JasenrekisteriGUIController harjoitusCtrl = (JasenrekisteriGUIController)ldr.getController(); //ja naihin HarjoitusNakymaGUIController
            
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("jasenrekisteri.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Jasenrekisteri");
            
            /*primaryStage.setOnCloseRequest((event) -> {
                if (!harjoitusCtrl.voikoSulkea()) event.consume();
            }); */
            
            Joukkue joukkue = new Joukkue();
            harjoitusCtrl.setJoukkue(joukkue);
            
            Application.Parameters params = getParameters(); 
            if ( params.getRaw().size() > 0 ) 
                harjoitusCtrl.lueTiedosto(params.getRaw().get(0));  
            //else
               // if ( !harjoitusCtrl.avaa() ) Platform.exit();

            
            primaryStage.show();
            //if (!harjoitusCtrl.avaa()) Platform.exit();
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
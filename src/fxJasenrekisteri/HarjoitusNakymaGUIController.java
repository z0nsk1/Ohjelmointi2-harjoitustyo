package fxJasenrekisteri;

//import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
//import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

/**
 * @author z0nsk1
 * @version 14.2.2020
 *
 */
public class HarjoitusNakymaGUIController implements Initializable{
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
    }
    
    
    
    @FXML
    private void handleToggle() {
        ModalController.showModal(JasenrekisteriGUIController.class.getResource("JasenrekisteriGUIView.fxml"), "Mahottomat Mestarit", null, "");
        /*Parent root = FXMLLoader.load(getClass().getResource("JasenrekisteriGUIView.fxml"));
        Scene skenes = new Scene(root);
        
        Stage stagel = new Stage();
        stagel.setScene(skenes);
        stagel.show(); */
        
    }
    
    
    @FXML
    private void handleTallenna() {
        Dialogs.showMessageDialog("Ei osata viel� tallentaa.");
    } 
    
    
    @FXML
    private void handlePeruuta() {
        Dialogs.showMessageDialog("Muutokset peruttiin...Vai peruttiinko?");
    }
    
    
    @FXML
    private void handlePoistu() {
        Dialogs.showMessageDialog("Olet poistumassa! Tallennetaanko?");
        Platform.exit();
    }
    
    
    @FXML
    private void handleLisaaHarjoitus() {
        Dialogs.showMessageDialog("Ei osata viel� lis�t� harjoitusta");
    }
}
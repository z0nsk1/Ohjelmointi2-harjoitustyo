package fxJasenrekisteri;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
//import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.layout.Pane;
import javafx.stage.Stage;
//import javafx.scene.control.ComboBox;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author z0nsk1
 * @version 13.2.2020
 *
 */
public class JasenrekisteriGUIController implements Initializable {
      
    @FXML private ComboBoxChooser<String> haku;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    
    Stage stagel = new Stage();

    //private String joukkueennimi = "MahottomatMestarit";
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
    }
    
    
    @FXML
    private void handleTallenna() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
    } 
    

    @FXML
    private void handleLisaaJasen() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä jäsentä");
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
    private void handleAvaaPaaIkkuna() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PaaOhjelmaHarjoitukset.fxml"));
        Scene skenes = new Scene(root);
       
        stagel.setScene(skenes);
        stagel.show();
        
    }
    
   @FXML
   private void handleLuoJoukkue() {
       Dialogs.showMessageDialog("Ei osata vielä luoda joukkuetta");
   }
   
   
   @FXML 
   private void handleTakaisin() {
       //stagel.close();
       Dialogs.showMessageDialog("Tästä pitäisi sulkeutua 'Jäsenet' -ikkuna");
   }
    
   
   @FXML
   private void handleComboBox() {
       //   
       /*final ComboBox<String> haku1 = new ComboBox<String>();
       haku1.getItems().addAll(
               "Vaihtoehto 1",
               "Vaihtoehto 2",
               "Vaihtoehto 3" 
               ); */
   } 

}
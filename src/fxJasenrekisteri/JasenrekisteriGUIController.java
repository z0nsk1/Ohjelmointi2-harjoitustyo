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
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * @author z0nsk1
 * @version 13.2.2020
 *
 */
public class JasenrekisteriGUIController implements Initializable, ModalControllerInterface<String> {
      
    @FXML private ComboBoxChooser<String> haku;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ListChooser<Jasen> chooserJasenet;
    
    Stage stagel = new Stage();

    //private String joukkueennimi = "MahottomatMestarit";
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    @FXML
    private void handleTallenna() {
        Dialogs.showMessageDialog("Ei osata viel� tallentaa.");
    } 
    

    @FXML
    private void handleLisaaJasen() {
        Dialogs.showMessageDialog("Ei osata viel� lis�t� j�sent�");
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
       Dialogs.showMessageDialog("Ei osata viel� luoda joukkuetta");
   }
   
   
   @FXML 
   private void handleTakaisin() {
       //stagel.close();
       Dialogs.showMessageDialog("T�st� pit�isi sulkeutua 'J�senet' -ikkuna");
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

   
   // ==============================================================================================
   Joukkue joukkue;
   
   
   /**
    * alustaa 
    */
   private void alusta() {
       chooserJasenet.clear();
       chooserJasenet.addSelectionListener(e -> naytaJasen());
   }
   
    /**
     * TODO täytä
     */
   private void naytaJasen() {
       //
   }
   
   
   @Override
   public String getResult() {
       // TODO Auto-generated method stub
       return null;
   }
   
   
   @Override
   public void handleShown() {
       // TODO Auto-generated method stub
   }
   
   
   @Override
   public void setDefault(String arg0) {
       // TODO Auto-generated method stub
   }
   
   
   /**
    * Kontrollerin joukkueviite
    * @param joukkue Viitattu joukkue
    */
   public void setJoukkue(Joukkue joukkue) {
           this.joukkue = joukkue;
   } 
   
}
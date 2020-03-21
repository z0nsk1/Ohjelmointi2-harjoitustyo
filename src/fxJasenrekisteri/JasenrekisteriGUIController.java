package fxJasenrekisteri;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;

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
    @FXML private ScrollPane panelJasen;
    
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
        uusiJasen();
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
    
    
    // Aloitusikkunan handleri
    @FXML
    private void handleAvaaPaaIkkuna() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("PaaOhjelmaHarjoitukset.fxml"));
        Scene skenes = new Scene(root);
        stagel.setScene(skenes);
        stagel.show();
    }
    
    
    // Aloitusikkunan handleri
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
   
   private Jasen jasenKohdalla;
   private Joukkue joukkue;
   private TextArea areaJasen = new TextArea();
   
   /**
    * dxfg
    * @param joukkue johon viitataan
    */
   public void setJoukkue(Joukkue joukkue) {
       this.joukkue = joukkue;
       naytaJasen();
   }
   
   
   private void uusiJasen() {
       Jasen uusi = new Jasen();
       uusi.rekisteroi();
       uusi.tiedot();
       
       try {
           joukkue.lisaa(uusi);
       } catch (SailoException e) {
           Dialogs.showMessageDialog("Virhe j�senen lis�yksess�! " + e.getMessage());
           return;
       }
       hae(uusi.getTunnusNro());
   }
   
   
   
   private void naytaJasen() {
       jasenKohdalla = chooserJasenet.getSelectedObject();

       if (jasenKohdalla == null) return;

       areaJasen.setText("");
       try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaJasen)) {
           jasenKohdalla.tulosta(os);
       }
   }
   
   
   /**
    * 
    */
   protected void alusta() {
       panelJasen.setContent(areaJasen);
       areaJasen.setFont(new Font("Courier New", 12));
       panelJasen.setFitToHeight(true);
       
       chooserJasenet.clear();
       chooserJasenet.addSelectionListener(e -> naytaJasen());
   }
   
   
   private void hae(int jnro) {
       chooserJasenet.clear();
       
       int index = 0;
       for(int i = 0; i < joukkue.getJasenia(); i++) {
           Jasen jasen = joukkue.annaJasen(i);
           if (jasen.getTunnusNro() == jnro) index = i;
           chooserJasenet.add(jasen.getNimi(), jasen);
       }
       chooserJasenet.setSelectedIndex(index);
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
   
   
}
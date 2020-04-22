package fxJasenrekisteri;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Jasenrekisteri.Harjoitus;
import Jasenrekisteri.Jasen;
import Jasenrekisteri.Joukkue;
import Jasenrekisteri.SailoException;
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
import fi.jyu.mit.fxgui.ModalController;
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
    @FXML private ListChooser<Harjoitus> chooserHarjoitus;
    @FXML private ScrollPane panelJasen;
    @FXML private ScrollPane panelHarjoitus;
    
    Stage stagel = new Stage();

    @SuppressWarnings("unused")
    private String joukkueenNimi = "";
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    
    @FXML
    private void handleTallenna() {
        tallenna();
    } 
    

    @FXML
    private void handleLisaaJasen() {
        uusiJasen();
    }
    
    
    @FXML
    private void handleLisaaHarjoitus() {
        uusiHarjoitus();
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
   
   
   /**
    * Luo uuden jasenen
    */
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
   
   
   /**
    * Luo uuden harjoituksen
    */
   public void uusiHarjoitus() {       
       if (jasenKohdalla == null) return;
       Harjoitus har = new Harjoitus();
       har.hTiedot(jasenKohdalla.getTunnusNro());
       joukkue.lisaa(har);
       
       hae(jasenKohdalla.getTunnusNro());
   }
   
   
   private void naytaJasen() {
       jasenKohdalla = chooserJasenet.getSelectedObject();

       if (jasenKohdalla == null) return;

       areaJasen.setText("");
       try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaJasen)) {
           tulosta(os, jasenKohdalla);
       }
   }
   
   
   private void tulosta(PrintStream os, Jasen jasen) {
       jasen.tulosta(os);
       List<Harjoitus> loytyneet = joukkue.annaHarjoitukset(jasen);
       for (Harjoitus harjoitus : loytyneet) {
           harjoitus.tulosta(os);
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
   
   
   /**
    * 
    * @return
    */
   private String tallenna() {
       try {
           joukkue.tallenna();
           return null;
       } catch (SailoException ex) {
           Dialogs.showMessageDialog("Tallennus epaonnistui: " + ex.getMessage());
           return ex.getMessage();
       }
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
   
   
  /**
   * @param nimi tiedoston nimi
   * @return null tai virhe jos luku ep�onnistuu
   */
   protected String lueTiedosto(String nimi) {
       joukkueenNimi = nimi;
       try {
           joukkue.lueTiedostosta(nimi);
           hae(0);
           return null;
       } catch (SailoException e) {
           hae(0);
           String virhe = e.getMessage(); 
           if ( virhe != null ) Dialogs.showMessageDialog(virhe);
           return virhe;
       }

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
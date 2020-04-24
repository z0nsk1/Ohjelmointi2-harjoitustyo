package fxJasenrekisteri;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Jasenrekisteri.Harjoitus;
import Jasenrekisteri.Jasen;
import Jasenrekisteri.Joukkue;
import Jasenrekisteri.SailoException;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author z0nsk1
 * @version 13.2.2020
 *
 */
public class JasenrekisteriGUIController implements Initializable, ModalControllerInterface<String> {
      
    @FXML private ComboBox<String> haku;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ListChooser<Jasen> chooserJasenet;
    @FXML private ListChooser<Harjoitus> chooserHarjoitus;
    @FXML private ScrollPane panelJasen;
    @FXML private ScrollPane panelHarjoitus;
    @FXML private GridPane gridJasen;
    @FXML private TextField editNimi;
    @FXML private TextField editSvuosi;
    @FXML private TextField editPuh;
    @FXML private TextField editCooper;
    @FXML private TextField editPaikalla;
    @FXML private TextField editPoissa;
    @FXML private TextField editAktiivisuus;
    @FXML private TextField editLisatietoja;
    @FXML private TextField editPelinumero;
    @FXML private TextField editId;
    
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
        ModalController.<String, JasenrekisteriGUIController>showModal(JasenrekisteriGUIController.class.getResource("JasenrekisteriGUIView.fxml"), "Mahottomat Mestarit", null, "", 
               ctrl->ctrl.setJoukkue(joukkue));
        lueTiedosto("MahottomatMestarit");
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
    private void handleTallennaJaPoistu() {
        tallenna();
        Platform.exit();
    } 
    
    @FXML
    private void handleOhjeet() {
        Dialogs.showMessageDialog("Muokkaus onnistuu tuplaklikkaamalla kenttia. Vanhan tiedoston saat avattua syottamalla tiedoston nimen.");
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
       ModalController.closeStage(gridJasen);
   }
    
   
   @FXML
   private void handleComboBox() {
       /*haku.getItems().addAll(
               "Vaihtoehto 1",
               "Vaihtoehto 2",
               "Vaihtoehto 3" 
               ); */
   }

   
   // ==============================================================================================
   
   private Jasen jasenKohdalla;
   private Joukkue joukkue;
   private TextArea areaJasen = new TextArea();
   //private static Jasen apujasen = new Jasen(); 
   
   
   /**
    * dxfg
    * @param joukkue johon viitataan
    */
   public void setJoukkue(Joukkue joukkue) {
       this.joukkue = joukkue;
       naytaJasen();
       hae(0);
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
           Dialogs.showMessageDialog("Virhe jasenen lisayksessa! " + e.getMessage());
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
   
   
   /**
 * 
 */
/*private void tulosta(PrintStream os, Jasen jasen) {
       jasen.tulosta(os);
       List<Harjoitus> loytyneet = joukkue.annaHarjoitukset(jasen);
       for (Harjoitus harjoitus : loytyneet) {
           harjoitus.tulosta(os);
       }
   } 
   
   
   /**
    * vfhgbjnfvg
    */
   protected void alusta() {
       areaJasen.setFont(new Font("Courier New", 12));
       panelJasen.setFitToHeight(true);
       
       chooserJasenet.clear();
       chooserJasenet.addSelectionListener(e -> naytaJasen());

       /*haku.clear(); 
       for (int k = apujasen.ekaKentta(); k < apujasen.getKenttia(); k++) 
           haku.add(apujasen.getKysymys(k), null); 
       haku.getSelectionModel().select(0); 
       */
       TextField edits[] = new TextField[]{editNimi, editSvuosi, editPuh, editCooper, editPaikalla, editPoissa, editAktiivisuus, editLisatietoja, editPelinumero, editId};
       for(TextField edit : edits) {
           if(edit == null) break;
           edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                   if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                       if(mouseEvent.getClickCount() > 1){
                           edit.setEditable(true);
                       }
                   }
               }
           });
       }
       /*edits = luoKentat(gridJasen, apujasen);  
       for (TextField edit: edits)  
           if ( edit != null ) {  
               edit.setEditable(false);  
               edit.setOnMouseClicked(e -> { if ( e.getClickCount() > 1 ) muokkaa(getFieldId(e.getSource(),0)); });  
               edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
               edit.setOnKeyPressed( e -> {if ( e.getCode() == KeyCode.F2 ) muokkaa(kentta);}); 
           }    
       */
       
       
       /*int i = 0;
       for (TextField edit : edits) {
           final int k = ++i;
           edit.setOnKeyReleased( e -> kasitteleMuutosJaseneen(k, (TextField)(e.getSource())));
       }  */
   }
   
   
      /**
    * @param obj asd
    * @param oletus asd
    * @return asd
    */
   public static int getFieldId(Object obj, int oletus) {
          if ( !( obj instanceof Node)) return oletus;
          Node node = (Node)obj;
          return Mjonot.erotaInt(node.getId().substring(1),oletus);
      }
      
     
   private void naytaJasen() {
       jasenKohdalla = chooserJasenet.getSelectedObject();

       if (jasenKohdalla == null) return;
      
       editNimi.setText(String.valueOf(jasenKohdalla.getNimi()));
       editSvuosi.setText(String.valueOf(jasenKohdalla.getSVuosi()));
       editPuh.setText(jasenKohdalla.getPuh());
       editCooper.setText(String.valueOf(jasenKohdalla.getCooper()));
       editPaikalla.setText(String.valueOf(jasenKohdalla.getPaikalla()));
       editPoissa.setText(String.valueOf(jasenKohdalla.getPoissa()));
       editAktiivisuus.setText(String.valueOf(jasenKohdalla.getAktiivisuus()));
       editLisatietoja.setText(jasenKohdalla.getLisatietoja());
       editPelinumero.setText(String.valueOf(jasenKohdalla.getPelinumero()));
       editId.setText(String.valueOf(jasenKohdalla.getTunnusNro())); //TODO: poista id n�kyvist� ohjelmassa
   }
   
   
   /*private void kasitteleMuutosJaseneen(int k, TextField edit) {
       if (jasenKohdalla == null) return;
       String s = edit.getText();
       String virhe = null;
       switch (k) {
          case 1 : virhe = jasenKohdalla.setNimi(s); break;
          case 2 : virhe = jasenKohdalla.setSVuosi(s); break;
          case 3 : virhe = jasenKohdalla.setPuh(s); break;
          case 4 : virhe = jasenKohdalla.setCooper(s); break;
          default:
       }
       if (virhe == null) {
           Dialogs.setToolTipText(edit,"");
           edit.getStyleClass().removeAll("virhe");
       } else {
           Dialogs.setToolTipText(edit,virhe);
           edit.getStyleClass().add("virhe");
       } 
   } */
   
   
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
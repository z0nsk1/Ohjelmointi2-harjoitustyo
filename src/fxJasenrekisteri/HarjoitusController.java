package fxJasenrekisteri;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import Jasenrekisteri.Harjoitus;
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
public class HarjoitusController implements Initializable, ModalControllerInterface<Joukkue> {
      
    @FXML private ComboBox<String> haku;
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    //Harjoitukset
    @FXML private ListChooser<Harjoitus> chooserHarjoitukset;
    @FXML private ScrollPane panelHarjoitus;
    @FXML private GridPane gridHarjoitus;
    @FXML private TextField editPvm;
    @FXML private TextField editAloitus;
    @FXML private TextField editLopetus;
    @FXML private TextField edit;
    @FXML private TextField editJPaikalla;
    @FXML private TextField editJPoissa;
    @FXML private TextField editHLisatietoja;
    @FXML private TextField editHId;
    
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
    private void handleLisaaHarjoitus() {
        uusiHarjoitus();
    }
    
    
    @FXML
    private void handleToggle() {
        ModalController.<Joukkue, JasenrekisteriGUIController>showModal(JasenrekisteriGUIController.class.getResource("JasenrekisteriGUIView.fxml"), "Mahottomat Mestarit", null, joukkue, 
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
   private void handleComboBox() {
       /*haku.getItems().addAll(
               "Vaihtoehto 1",
               "Vaihtoehto 2",
               "Vaihtoehto 3" 
               ); */
   }

   
   // ===================================================================================================================================================================
   
   private Harjoitus harjoitusKohdalla;
   private Joukkue joukkue;
   private TextArea areaHarjoitus = new TextArea();
   //private static Jasen apujasen = new Jasen(); 
   
   
   /**
    * @param joukkue johon viitataan
    */
   public void setJoukkue(Joukkue joukkue) {
       this.joukkue = joukkue;
       naytaHarjoitus();
       haeHarjoitus(0);
   }
   
   
   /**
    * Luo uuden harjoituksen
    */
   public void uusiHarjoitus() {
       boolean viimeinenJ = false;
       for (int i = 1; i < 1+joukkue.getJasenia(); i++) {
           if (i == joukkue.getJasenia()) viimeinenJ = true;
           Harjoitus har = new Harjoitus();
           har.rekisteroi(viimeinenJ);
           har.hTiedot(i);
           joukkue.lisaa(har);    
           haeHarjoitus(har.getTunnusNro());
       }
   }
   
   
   /**
    * vfhgbjnfvg
    */
   protected void alusta() {
       areaHarjoitus.setFont(new Font("Courier New", 12));
       panelHarjoitus.setFitToHeight(true);

       chooserHarjoitukset.clear();
       chooserHarjoitukset.addSelectionListener(e -> naytaHarjoitus());
       /*haku.clear(); 
       for (int k = apujasen.ekaKentta(); k < apujasen.getKenttia(); k++) 
           haku.add(apujasen.getKysymys(k), null); 
       haku.getSelectionModel().select(0); 
       */
       TextField edits[] = new TextField[]{editPvm, editAloitus, editLopetus, editJPaikalla, editJPoissa, editHLisatietoja, editHId};
       for(@SuppressWarnings("hiding") TextField edit : edits) {
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

   
   private void naytaHarjoitus() {
       harjoitusKohdalla = chooserHarjoitukset.getSelectedObject();

       if (harjoitusKohdalla == null) return;
      
       editPvm.setText(String.valueOf(harjoitusKohdalla.getPvm()));
       editAloitus.setText(String.valueOf(harjoitusKohdalla.getAloitus()));
       editLopetus.setText(String.valueOf(harjoitusKohdalla.getLopetus()));
       editJPaikalla.setText(String.valueOf(harjoitusKohdalla.getJPaikalla()));
       editJPoissa.setText(String.valueOf(harjoitusKohdalla.getJPoissa()));
       editHLisatietoja.setText(harjoitusKohdalla.getHLisatietoja());
       editHId.setText(String.valueOf(harjoitusKohdalla.getTunnusNro()));
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

   
   /**
    * @param hnro valittu harjoitus
    */
   private void haeHarjoitus(int hnro) {
       chooserHarjoitukset.clear();
       
       int index = 0;
       for(int i = 0; i < joukkue.getHarjoituksia(); i++) {
           Collection<Harjoitus> har = joukkue.annaHarjoitukset(i);
           boolean lisattyH = false;
           for(Harjoitus h : har) {
               if (h.getTunnusNro() == hnro) index = i;
               if (!lisattyH) {
                   chooserHarjoitukset.add(Integer.toString(h.getPv()), h);
                   lisattyH = true;
               }
           }
       }
       chooserHarjoitukset.setSelectedIndex(index);
   }

   
   
  /**
   * @param nimi tiedoston nimi
   * @return null tai virhe jos luku ep�onnistuu
   */
   protected String lueTiedosto(String nimi) {
       joukkueenNimi = nimi;
       try {
           joukkue.lueTiedostosta(nimi);
           haeHarjoitus(0);
           return null;
       } catch (SailoException e) {
           haeHarjoitus(0);
           String virhe = e.getMessage(); 
           if ( virhe != null ) Dialogs.showMessageDialog(virhe);
           return virhe;
       }

   }

   
   @Override
   public Joukkue getResult() {
       // TODO Auto-generated method stub
       return null;
   }
   
   
   @Override
   public void handleShown() {
       // TODO Auto-generated method stub
   }
   
   
   @Override
   public void setDefault(Joukkue oletus) {
       // TODO Auto-generated method stub
   }
   
   
}
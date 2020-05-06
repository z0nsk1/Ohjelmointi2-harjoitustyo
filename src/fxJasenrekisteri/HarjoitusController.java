package fxJasenrekisteri;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jonni ja Mikko
 * @version 6.5.2020
 */
public class HarjoitusController implements Initializable, ModalControllerInterface<Joukkue> {
    
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private ComboBoxChooser<String> cbKentat;
    @FXML private TextField hakuehto;
    @FXML private ListChooser<Harjoitus> chooserHarjoitukset;
    @FXML private ListChooser<Harjoitus> chooserPaikalla;
    @FXML private ListChooser<Harjoitus> chooserPoissa;
    @FXML private ScrollPane panelHarjoitus;
    @FXML private GridPane gridHarjoitus;
    @FXML private TextField editPvm;
    @FXML private TextField editAloitus;
    @FXML private TextField editLopetus;
    @FXML private TextField edit;
    @FXML private TextArea editHLisatietoja;
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
    private void handlePoistaHarjoitus() {
        poistaHarjoitus();
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
   private void handleHakuehto() {
       haeHarjoitus(0);
   }

   
   // ===================================================================================================================================================================
   
   private Harjoitus harjoitusKohdalla;
   private Joukkue joukkue;
   private TextArea areaHarjoitus = new TextArea();
   
   
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
    * Poistaa harjoituksen muuttamalla sen ID:n negatiiviseksi
    */
   public void poistaHarjoitus() {
       harjoitusKohdalla = chooserHarjoitukset.getSelectedObject();
       if(harjoitusKohdalla == null) return;
       int harjoId = harjoitusKohdalla.getTunnusNro();
       
       Collection<Harjoitus> har = joukkue.annaHarjoitukset(harjoId);
       for (Harjoitus h : har) {
           h.setPoistaHarj();
       }
       haeHarjoitus(0);
   }
   
   
   /**
    * 
    */
   protected void alusta() {
       cbKentat.clear(); //TODO:
       cbKentat.add("Paivamaara", null);
       cbKentat.add("Aloitus kellonaika", null);
       cbKentat.add("Lopetus kellonaika", null);
       cbKentat.getSelectionModel().select(0);
       editHLisatietoja.setWrapText(true);
       
       areaHarjoitus.setFont(new Font("Courier New", 12));
       panelHarjoitus.setFitToHeight(true);

       chooserHarjoitukset.clear();
       chooserHarjoitukset.addSelectionListener(e -> naytaHarjoitus());
       chooserPaikalla.addSelectionListener(e -> jasenSiirto(chooserPaikalla));
       chooserPoissa.addSelectionListener(e -> jasenSiirto(chooserPoissa));
       /*haku.clear(); 
       for (int k = apujasen.ekaKentta(); k < apujasen.getKenttia(); k++) 
           haku.add(apujasen.getKysymys(k), null); 
       haku.getSelectionModel().select(0); 
       */
       TextField edits[] = new TextField[]{editPvm, editAloitus, editLopetus, editHId};
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
           editHLisatietoja.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent mouseEvent) {
                   if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                       if(mouseEvent.getClickCount() > 1){
                           editHLisatietoja.setEditable(true);
                       }
                   }
               }
        });
       } 

       
       int i = 0;
       for (TextField muok : edits) {
           final int k = ++i;
           muok.setOnKeyReleased( e -> kasitteleMuutosH(k, (TextField)(e.getSource())));
       }  
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

   
   /**
    * nayttaa harjoituksen tiedot kayttajalle
    */
   private void naytaHarjoitus() {
       harjoitusKohdalla = chooserHarjoitukset.getSelectedObject();

       if (harjoitusKohdalla == null) return;
      
       editPvm.setText(String.valueOf(harjoitusKohdalla.getPvm()));
       editAloitus.setText(String.valueOf(harjoitusKohdalla.getAloitus()));
       editLopetus.setText(String.valueOf(harjoitusKohdalla.getLopetus()));
       editHLisatietoja.setText(harjoitusKohdalla.getHLisatietoja());
       editHId.setText(String.valueOf(harjoitusKohdalla.getTunnusNro()));
       naytaPaikalla();
       naytaPoissa();
   }
   
   
   private void naytaPoissa() {
       chooserPoissa.clear();
       harjoitusKohdalla = chooserHarjoitukset.getSelectedObject();
       
       if (harjoitusKohdalla == null) return;

       int harjoId = harjoitusKohdalla.getTunnusNro();
       Collection<Harjoitus> har = joukkue.annaHarjoitukset(harjoId);
       for(Harjoitus h : har) {
           int i = h.getJPoissa();
           if (i<0) continue;
           Jasen jasen = joukkue.annaJasen(i-1);
           if ( jasen.getTunnusNro() < 0 ) continue;
           chooserPoissa.add(jasen.getNimi(), h);
       }
   }
   
   
   private void jasenSiirto(ListChooser<Harjoitus> cho) {
       harjoitusKohdalla = cho.getSelectedObject();
       
       if (harjoitusKohdalla == null) return;
       
       int jasenPai = harjoitusKohdalla.getJasenNro();
       int jasenPoi = harjoitusKohdalla.getJPoissa();
       harjoitusKohdalla.setPaikalla(jasenPoi);
       harjoitusKohdalla.setPoissa(jasenPai);
       
       naytaPaikalla();
       naytaPoissa();
   }
   
   
   /**
    * nayttaa listassa jasenet, jotka on paikalla
    */
   private void naytaPaikalla() {
       chooserPaikalla.clear();
       harjoitusKohdalla = chooserHarjoitukset.getSelectedObject();
       
       if (harjoitusKohdalla == null) return;
       
       int harjoId = harjoitusKohdalla.getTunnusNro();
       Collection<Harjoitus> har = joukkue.annaHarjoitukset(harjoId);
       for(Harjoitus h : har) {
           int i = h.getJasenNro();
           if (i<0) continue;
           Jasen jasen = joukkue.annaJasen(i-1);
           if ( jasen.getTunnusNro() < 0 ) continue;
           chooserPaikalla.add(jasen.getNimi(), h);
       }
   }
   
   
   private void kasitteleMuutosH(int k, TextField muok) {
       if (harjoitusKohdalla == null) return;
       String s = muok.getText();
       String virhe = null;
       switch (k) {
          case 1 : virhe = harjoitusKohdalla.setPvm(s); break;
          case 2 : virhe = harjoitusKohdalla.setAloitus(s); break;
          case 3 : virhe = harjoitusKohdalla.setLopetus(s); break;
          case 4 : virhe = harjoitusKohdalla.setLisat(s); break;
          default:
       }
       if (virhe == null) {
           Dialogs.setToolTipText(muok,"");
           muok.getStyleClass().removeAll("virhe");
       } else {
           Dialogs.setToolTipText(muok,virhe);
           muok.getStyleClass().add("virhe");
       } 
   } 
   
   
   /**
    * 
    */
   private String tallenna() {
       try {
           joukkue.tallenna();
           haeHarjoitus(0);
           return null;
       } catch (SailoException ex) {
           Dialogs.showMessageDialog("Tallennus epaonnistui: " + ex.getMessage());
           return ex.getMessage();
       }
   }

   
   /**
    * @param hnr valittu harjoitus
    */
   private void haeHarjoitus(int hnr) {
       int hnro = hnr;
       if (hnro <= 0) {
           Harjoitus kohdalla = harjoitusKohdalla;
           if(kohdalla != null) hnro = kohdalla.getPv();
       }
       
       int k = cbKentat.getSelectionModel().getSelectedIndex() + 1; //TODO:
       String ehto = hakuehto.getText();
       
       if(ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
       
       chooserHarjoitukset.clear();
       
       int index = 0;
       for(int i = 1; i < 1+joukkue.getHarjoituksia(); i++) {
           Collection<Harjoitus> har = joukkue.etsi(ehto, k, i);
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
       return null;
   }
   
   
   @Override
   public void handleShown() {
       //
   }
   
   
   @Override
   public void setDefault(Joukkue oletus) {
       //
   }
   
   
}
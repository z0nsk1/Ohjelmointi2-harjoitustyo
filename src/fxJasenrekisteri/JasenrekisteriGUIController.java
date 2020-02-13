package fxJasenrekisteri;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.Dialogs;

/**
 * @author z0nsk1
 * @version 13.2.2020
 *
 */
public class JasenrekisteriGUIController {
      // TODO
    

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private void handleTallenna() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa.");
    } 
    

    @FXML
    private void handleLisaaJasen() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä jäsentä");
    }

}
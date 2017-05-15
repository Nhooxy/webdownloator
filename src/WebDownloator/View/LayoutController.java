package WebDownloator.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LayoutController extends CommonViewController {

    @FXML
    protected Button boutonAccueil;

    @FXML
    protected Button boutonTelecharger;

    @FXML
    protected Button boutonVisualiser;

    @FXML
    protected Button boutonOption;

    /**
     * Constructeur par defaut.
     */
    public LayoutController() {
    }

    @FXML
    protected void telechargerView(ActionEvent event) {
        boutonColorProcess();
        boutonTelecharger.setStyle("-fx-background-color: #333; -fx-background-radius: 0;");
        app.ChangeView("Telecharger");
    }

    @FXML
    protected void visualiserView(ActionEvent event) {
        boutonColorProcess();
        boutonVisualiser.setStyle("-fx-background-color: #333; -fx-background-radius: 0;");
        app.ChangeView("Visualiser");
    }

    @FXML
    protected void optionView(ActionEvent event) {
        boutonColorProcess();
        boutonOption.setStyle("-fx-background-color: #333; -fx-background-radius: 0;");
        app.ChangeView("Option");
    }

    @FXML
    protected void accueilView(ActionEvent event) {
        boutonColorProcess();
        boutonAccueil.setStyle("-fx-background-color: #333; -fx-background-radius: 0;");
        app.ChangeView("Accueil");
    }

    private void boutonColorProcess() {
        boutonAccueil.setStyle("-fx-background-color: #555; -fx-background-radius: 0;");
        boutonOption.setStyle("-fx-background-color: #555; -fx-background-radius: 0;");
        boutonVisualiser.setStyle("-fx-background-color: #555; -fx-background-radius: 0;");
        boutonTelecharger.setStyle("-fx-background-color: #555; -fx-background-radius: 0;");
    }
}

package WebDownloator.View;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Classe Controller/Vue pour gerer la vue option.
 */
public class OptionController extends CommonViewController {

    /**
     * String designant le dossier de dl de l'application.
     */
    @FXML
    protected JFXTextField dossierTelechargement;

    /**
     * String de langue de l'application
     */
    @FXML
    protected JFXComboBox<String> langue = new JFXComboBox<String>();

    /**
     * Constructeur par defaut.
     */
    public OptionController() {
    }

    /**
     * Permet de sauvegarder les settings.
     *
     * @return
     */
    @FXML
    protected void save() {
        String langueConfig = this.langue.getValue();
        String dossierConfig = this.dossierTelechargement.getText();

        try {
            Properties props = new Properties();
            props.setProperty("langue", langueConfig);
            props.setProperty("dossier", dossierConfig);
            Writer out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("bin/WebDownloator/config.properties"), "UTF-8"));
            props.store(out, "config");
            this.app.reloadLangue(langueConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet d'annuler les modification et de retouner au menu accueil.
     */
    @FXML
    protected void retour() {
        initialize();
    }

    /**
     * Permet de parcourir avec une liste fichier style systeme.
     */
    @FXML
    public void parcourir() {
        DirectoryChooser choix = new DirectoryChooser();
        File file = choix.showDialog(this.app.getStage());
        if (null != file) {
            this.dossierTelechargement.setText(file.getAbsolutePath());
            choix.setInitialDirectory(file);
        }
    }

    /**
     * FXML exigence
     */
    @FXML
    @Override
    protected void initialize() {
        ResourceBundle config;
        config = ResourceBundle.getBundle("WebDownloator.config");
        String langueConfig = config.getString("langue");
        String dossierConfig = config.getString("dossier");
        config.clearCache();

        this.langue.setItems(FXCollections.observableArrayList("French", "English", "Japanese"));
        this.langue.setValue(langueConfig);
        this.dossierTelechargement.setPromptText(dossierConfig);
        this.dossierTelechargement.setText(dossierConfig);
    }
}

package WebDownloator.View;

import WebDownloator.Model.Telecharger;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Labeled;

import java.io.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class TelechargerController extends CommonViewController {

    protected String dossierConfig;

    /**
     * String designant l'url a dl.
     */
    @FXML
    protected JFXTextField url;

    /**
     * Checkbox pour savoir si on prend les images et les videos.
     */
    @FXML
    protected JFXCheckBox getImgVideo;

    /**
     * String a parser en int pour la recurcivite.
     */
    @FXML
    protected JFXComboBox<String> recurcivite = new JFXComboBox<String>();

    @FXML
    protected JFXSpinner wait = new JFXSpinner();

    @FXML
    protected Labeled wait2;

    /**
     * Constructeur par defaut.
     */
    public TelechargerController() {
    }

    /**
     * FXML exigence
     */
    @FXML
    protected void start() {
        JFXSpinner wwait = this.wait;
        wwait.setVisible(true);
        Labeled wwait2 = this.wait2;
        wwait2.setVisible(true);
        Telecharger T = new Telecharger(
                this.url.getText(),
                this.dossierConfig,
                this.getImgVideo.selectedProperty().isBound(),
                Integer.parseInt(this.recurcivite.getValue())
        );

        String primaryPath = this.dossierConfig + "/" + this.url.getText().replace("http://", "");
        primaryPath = primaryPath.replace("https:", "");
        primaryPath = primaryPath.replace("?", "");

        primaryPath = primaryPath + "/index.html";

        Properties props = new Properties();
        props.setProperty("lastPath", primaryPath);
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("bin/WebDownloator/path.properties"), "UTF-8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            assert out != null;
            props.store(out, "path");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread() {
            public void run() {
                T.start();
                try {
                    T.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    wwait.setVisible(false);
                    wwait2.setVisible(false);
                }
            }
        };
        t.start();
    }

    /**
     * FXML exigence
     */
    @FXML
    @Override
    protected void initialize() {
        ResourceBundle config;
        config = ResourceBundle.getBundle("WebDownloator.config");
        this.dossierConfig = config.getString("dossier");
        this.recurcivite.setItems(FXCollections.observableArrayList(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        );
        this.recurcivite.setValue("0");
        this.getImgVideo.setSelected(true);
    }
}

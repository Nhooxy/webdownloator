package WebDownloator.View;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class VisualiserController extends CommonViewController {
    @FXML
    protected TreeTableView<ObservableValue> tree;

//    @FXML
//    protected TreeTableColumn<DbVisualiser, String> Arborescence;

    @FXML
    protected WebView webView;

    /**
     * Constructeur par defaut.
     */
    public VisualiserController() {
    }

    @FXML
    public void selectWebPage() {
        ResourceBundle config;
        config = ResourceBundle.getBundle("WebDownloator.path");
        String pathConfig = config.getString("lastPath");

        File file = new File(pathConfig);
        URL url = null;
        try {
            url = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        WebEngine webEngine = webView.getEngine();
        if (null != url) {
            webEngine.load(url.toString());
        }
        this.webView = new WebView();
    }

    /**
     * FXML exigence
     */
    @FXML
    @Override
    protected void initialize() {
        selectWebPage();
    }
}

package WebDownloator.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import WebDownloator.View.*;

public class WebDownloator extends Application {

    private BorderPane layout;
    private ResourceBundle bundleLang;
    private Stage stage;
    private static ResourceBundle.Control UTF8_ENCODING_CONTROL = new ResourceBundle.Control() {
        /**
         * Permet de transformer en UTF-8 les fichiers properties (de langue).
         * source : https://gist.github.com/komiya-atsushi/2d9d8b2000358d4d004d
         *
         * UTF-8 エンコーディングのプロパティファイルから ResourceBundle オブジェクトを生成します。
         * <p>
         * 参考 :
         * <a href="http://jgloss.sourceforge.net/jgloss-core/jacoco/jgloss.util/UTF8ResourceBundleControl.java.html">
         * http://jgloss.sourceforge.net/jgloss-core/jacoco/jgloss.util/UTF8ResourceBundleControl.java.html
         * </a>
         * </p>
         *
         * @throws IllegalAccessException
         * @throws InstantiationException
         * @throws IOException
         */
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");

            try (InputStream is = loader.getResourceAsStream(resourceName);
                 InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                 BufferedReader reader = new BufferedReader(isr)) {
                return new PropertyResourceBundle(reader);
            }
        }
    };

    /**
     * main.
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * C'est le point d'entré de l'application javaFX.
     *
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("WebDownloator");
        this.stage.getIcons().add(new Image("/WebDownloator/icon.png"));

        this.bundleLang = getLangueBundleConfig(null);
        initLayout();
        ChangeView("Accueil");
    }

    /**
     * Initialise le layout de l'application.
     */
    public void initLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WebDownloator/View/Layout.fxml"), this.bundleLang);
            this.layout = (BorderPane) loader.load();
            Scene scene = new Scene(this.layout);
            this.stage.setScene(scene);
            LayoutController controller = loader.getController();
            controller.setApp(this);
            this.stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadLangue(String langueConfig) {
        this.stage.close();
        this.bundleLang = getLangueBundleConfig(langueConfig);

        initLayout();
        ChangeView("Option");
    }

    /**
     * Permet d'invoqué la vue demandé.
     *
     * @param name nom de le vue demandé
     */
    public void ChangeView(String name) {
        CommonViewController controller;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/WebDownloator/View/" + name + ".fxml"), this.bundleLang);
            AnchorPane extractionView = (AnchorPane) loader.load();
            this.layout.setCenter(extractionView);

            switch (name) {
                case "Accueil":
                    controller = new AccueilController();

                case "Telecharger":
                    controller = new TelechargerController();
                    controller = loader.getController();
                    controller.setApp(this);
                case "Option":
                    controller = new OptionController();
                    controller = loader.getController();
                    controller.setApp(this);
                case "Visualiser":
                    controller = new VisualiserController();
                    controller = loader.getController();
                    controller.setApp(this);
                default:
                    controller = new AccueilController();
                    controller = loader.getController();
                    controller.setApp(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Permet de renvoyer le bundle de la bonne langue en fonction
     * de ce qui est noté dans le fichier de configuration.
     *
     * @return ResourceBundle
     */
    private ResourceBundle getLangueBundleConfig(String langue) {
        ResourceBundle config;
        config = ResourceBundle.getBundle("WebDownloator.config");
        String langueConfig = null == langue ? config.getString("langue") : langue;
        config.clearCache();

        switch (langueConfig) {
            case ("French"):
                return ResourceBundle.getBundle("WebDownloator.Langue.FR", UTF8_ENCODING_CONTROL);
            case ("English"):
                return ResourceBundle.getBundle("WebDownloator.Langue.EN", UTF8_ENCODING_CONTROL);
            case ("Japanese"):
                return ResourceBundle.getBundle("WebDownloator.Langue.JP", UTF8_ENCODING_CONTROL);
            default:
                return ResourceBundle.getBundle("WebDownloator.Langue.FR", UTF8_ENCODING_CONTROL);
        }
    }

    /**
     * stage principale de l'app.
     *
     * @return
     */
    public Stage getStage() {
        return this.stage;
    }
}

package WebDownloator.View;

import WebDownloator.Controller.WebDownloator;
import javafx.fxml.FXML;

public class CommonViewController {

    protected WebDownloator app;

    /**
     * FXML exigence
     */
    @FXML
    protected void initialize() {
    }

    /**
     *
     */
    public void setApp(WebDownloator app) {
        this.app = app;
    }

    /**
     * @return
     */
    public WebDownloator getApp() {
        return this.app;
    }
}

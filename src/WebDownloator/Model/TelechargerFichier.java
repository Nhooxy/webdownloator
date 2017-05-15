package WebDownloator.Model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.jsoup.nodes.Element;

class TelechargerFichier {

    private String attr;
    private String nom;
    private File target;
    private String url;

    public void ecrireFichierCSS(Element src, String path) {
        setParamCSS(src, path);
        write(src, path);
    }

    public void ecrireFichierJS(Element src, String path) {
        setParamJS(src, path);
        write(src, path);
    }

    private void setParamJS(Element src, String path) {
        this.attr = "src";
        this.url = src.attr("abs:" + this.attr);
        String[] tab = this.url.split("/");
        this.nom = tab[tab.length - 1];
        this.target = new File(path + this.nom.replace("?", ""));
    }

    private void setParamCSS(Element src, String path) {
        this.attr = "href";
        this.url = src.attr("abs:" + this.attr);
        String[] tab = this.url.split("/");
        this.nom = tab[tab.length - 1];
        this.target = new File(path + this.nom.replace("?", ""));
    }

    private void write(Element src, String path) {
        try {
            src.attr(this.attr, "file://" + path + nom.replace("?", ""));
            URL urltest = new URL(this.url);

            InputStream fileIn = urltest.openStream();
            OutputStreamWriter fileOut = new OutputStreamWriter(new FileOutputStream(this.target), StandardCharsets.UTF_8);

            int b;
            while ((b = fileIn.read()) != -1) {
                fileOut.write(b);
            }

            fileOut.flush();
            fileOut.close();
            fileIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

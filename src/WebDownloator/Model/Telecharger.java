package WebDownloator.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class Telecharger extends Thread {

    private String url;
    private String path;
    private Boolean withMedia;
    private int rec;
    private TelechargerFichier TF;
    private TelechargerMedia TM;

    /**
     * @param url       l'url du site a télécharger.
     * @param path      le répertoire où enregistrer les fichiers.
     * @param withMedia Bool qui donne l'autorisation de télécharger les images/vidéos.
     * @param rec       Profondeur de la récursivitée.
     */
    public Telecharger(String url, String path, Boolean withMedia, int rec) {
        this.url = url;
        this.path = path;
        this.withMedia = true;
        this.rec = rec;
        this.TF = new TelechargerFichier();
        this.TM = new TelechargerMedia();
    }

    @Override
    public void run() {
        String urlSite = this.url;
        String primaryPath = this.path + "/" + this.url.replace("http://", "");
        primaryPath = primaryPath.replace("https:", "");
        primaryPath = primaryPath.replace("?", "");
        this.path = primaryPath;
        Document doc = null;
        try {
            doc = Jsoup.connect(urlSite).get();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        String secondPath;

        Elements lien = doc.select("a[href]");
        Elements css = doc.select("link[href]");
        Elements js = doc.select("script[src]");

        secondPath = creerPath(primaryPath + "/css/");

        for(Element src : lien) {
            if(this.rec > 0) {
                suivreLien(src);
            }
        }

        for (Element src : css) {
            if (src.attr("type").equals("text/css")) {
                this.TF.ecrireFichierCSS(src, secondPath);
            }
        }

        secondPath = creerPath(primaryPath + "/js/");

        for (Element src : js) {
            if (src.attr("type").equals("text/javascript")) {
                this.TF.ecrireFichierJS(src, secondPath);
            }
        }

        if (withMedia) {
            Elements media = doc.select("img[src]");
            Elements media2 = doc.select("video[src]");

            secondPath = creerPath(primaryPath + "/img/");

            for (Element src : media) {
                this.TM.ecrireMedia(src, secondPath, false);
            }

            secondPath = creerPath(primaryPath + "/video/");

            for (Element src : media2) {
                this.TM.ecrireMedia(src, secondPath,true);
            }
        }


        File file = new File(primaryPath + "/index.html");
        OutputStreamWriter fw;
        try {
            fw = new OutputStreamWriter(new FileOutputStream(file));
            fw.write(doc.toString());
            fw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void suivreLien(Element src){
        String url = src.attr("abs:href");
        Telecharger T = new Telecharger(
                url,
                this.path,
                this.withMedia,
                this.rec-1);
        String tmpPath = url.replace("http://", "");
        tmpPath = tmpPath.replace("https:", "");
        tmpPath = tmpPath.replace("?", "");
        src.attr("href", "file://" + this.path + "/" +tmpPath + "/index.html");
        T.start();
    }

    private String creerPath(String dossier) {
        File dir = new File(dossier);
        dir.mkdirs();
        return dossier;
    }
}
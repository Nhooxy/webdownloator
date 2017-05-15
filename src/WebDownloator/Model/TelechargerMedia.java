package WebDownloator.Model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.jsoup.nodes.Element;

class TelechargerMedia {

    public void ecrireMedia(Element src, String path, boolean isVideo) {
        try {
            URL url = new URL(src.attr("abs:src"));
            String tmp = src.attr("abs:src");
            String tab[] = tmp.split("/");
            String nom = tab[tab.length - 1];
            if (isVideo) {
                path = path + nom.replace("?", "") + ".mp4";
            } else {
                path = path + nom.replace("?", "");
            }

            src.attr("src", "file://" + path);
            InputStream fileIn = url.openStream();
            File target = new File(path);

            BufferedOutputStream fileOut = new BufferedOutputStream(new FileOutputStream(target));

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

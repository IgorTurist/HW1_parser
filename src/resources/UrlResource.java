package resources;


import parser.TextParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by igor on 07.02.2017.
 */
public class UrlResource extends TextResource {
    public UrlResource(){
    }

    public UrlResource(String path){
        this.path = path;
    }

    @Override
    public boolean isValid(){
        boolean res = false;
        try {
            URL url = new URL(path);
            try (InputStream stream = url.openStream()){
                int avl = stream.available();
                if (avl > 0)
                    res = true;
            }
            catch(IOException ex){
                log.config("The program can't find remote resource.\r\n " +
                            "Check its existence and network connection and try again.\r\n");
                res = false;
            }
        }
        catch (MalformedURLException ex) {
            log.config("\"" + path + "\" - this is malformed url.\r\nFix it and try again.");
            res = false;
        }

        return res;
    }

    @Override
    public void runResourceParsing() throws MalformedURLException, Exception{
        if (!isValid())
            throw new Exception("\"" + path + "\" is not a valid file");

        URL url = new URL(path);

        try (InputStream stream = url.openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {

            String s = null;
            boolean isFirst = false;
            while ((s = br.readLine()) != null && !finish) {
                if (!isFirst) {
                    s = s.substring(1);
                    isFirst = true;
                }

                TextParser.getStringStatistic(s, TextResource.getDict());
            }
        }
        catch (Exception ex) {
            throw ex;
        }
    }
}

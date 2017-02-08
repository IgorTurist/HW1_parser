package resources;

import resources.TextResource;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
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
                System.out.println("The program can't find remote resource.\r\n " +
                            "Check its existence and network connection and try again.\r\n");
                res = false;
            }
        }
        catch (MalformedURLException ex) {
            System.out.println("\"" + path + "\" - this is malformed url.\r\nFix it and try again.");
            res = false;
        }

        return res;
    }

    @Override
    public void runResourceParsing(){
//this is a stub. Don't forget to fill it!!!
    }
}

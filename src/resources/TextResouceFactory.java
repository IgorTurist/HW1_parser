package resources;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by igor on 08.02.2017.
 */
public class TextResouceFactory {
    public static TextResource createTextResource(String path){
        try {
            File f = new File(path);
            if (f.isFile())
                return new FileResource(path);

            URL url = new URL(path);
            InputStream is = url.openStream();
            int ave = is.available();
            if(is.available() > 0)
                return new UrlResource(path);
            else
                throw new Exception("Unavailable url: \"" + path + "\"");
        }
        catch(NullPointerException ex){
            System.out.println("\"" + path + "\" Sorry, internal program error occurred\r\n" +
                    "Try to start the program again.");
        }
        catch(MalformedURLException ex){
            System.out.println("\"" + path + "\" - this is malformed url.\r\nFix it and try again.");
        }
        catch(Exception ex){
            System.out.println("Error occurred:" + ex.getMessage());
        }

        return null;
    }
}

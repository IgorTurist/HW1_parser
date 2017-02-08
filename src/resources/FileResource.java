package resources;

import resources.TextResource;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by igor on 07.02.2017.
 */
public class FileResource extends TextResource {
    public FileResource(){
    }

    public FileResource(String path){
        this.path = path;
    }

    @Override
    public boolean isValid() {
        File f = new File(path);
        System.out.println();
        return f.isFile();
    }

    @Override
    public void runResourceParsing(){
//this is a stub. Don't forget to fill it!!!
    }

}

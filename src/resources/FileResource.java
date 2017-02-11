package resources;

import parser.TextParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

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
    public boolean isValid() throws NullPointerException{
        File f = new File(path);
        return f.isFile();
    }

    @Override
    public void runResourceParsing() throws Exception {
        if (!isValid())
            throw new Exception("\"" + path + "\" is not a valid file");

        FileReader fr = new FileReader(path);

        try (BufferedReader br = new BufferedReader(fr)) {

            String s = null;
            boolean isFirst = false;
            while ((s = br.readLine()) != null && !finish) {
                if (!isFirst) {
                    s = s.substring(1);
                    isFirst = true;
                }

                TextParser.getStringStatistic(s, TextResource.getDict());
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}

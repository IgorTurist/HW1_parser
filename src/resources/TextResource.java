package resources;

import java.util.HashMap;

/**
 * Created by igor on 07.02.2017.
 */
public class TextResource {

    static {
        dict = new HashMap<String,Long>(TextResource.INIT_CAPACITY);
    }

    public boolean isValid(){
        if(path.isEmpty())
            return false;
        return true;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static HashMap<String, Long> getDict() {
        return dict;
    }

    protected void runResourceParsing() {
    }

    protected String path = "";
    protected static final int INIT_CAPACITY = 100;

    protected static HashMap<String,Long> dict;
}

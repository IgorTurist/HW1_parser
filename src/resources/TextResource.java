package resources;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by igor on 07.02.2017.
 */
public abstract class TextResource implements Runnable{

    static {
        dict = new HashMap<String,Long>(TextResource.INIT_CAPACITY);
    }

    @Override
    public void run() {
        try{
            runResourceParsing();
        }
        catch(Exception ex){
            finish = true;
            log.severe(ex.getMessage());
        }
        log.info("Thread (id=" + Thread.currentThread().getId() + ") has just been finished.");
    }

    public abstract boolean isValid();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static HashMap<String, Long> getDict() {
        return dict;
    }

    public abstract void runResourceParsing()throws Exception;

    public static void stopResourceParsing(){
        finish = true;
    }

    public static boolean isParsingSucceeded(){
        return !finish;
    }

    protected static volatile boolean finish = false;
    protected String path = "";
    protected static final int INIT_CAPACITY = 100;
    protected static volatile HashMap<String,Long> dict;
    protected static final Logger log = Logger.getLogger(TextResource.class.getName());
}

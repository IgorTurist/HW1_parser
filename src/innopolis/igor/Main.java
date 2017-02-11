package innopolis.igor;

import resources.*;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class Main {

    private static final Logger log =  Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Thread[] threads = new Thread[args.length];

        try {
            if (args.length == 0)
                throw new Exception("There are no any resources to process.\r\nRerun the program with not empty resources list.");

            for(int i = 0;i<args.length;i++){
                TextResource res = TextResourceFactory.createTextResource(args[i]);
                threads[i] = new Thread(res);
                threads[i].start();
            }

            closeThreads(threads);

            if(TextResource.isParsingSucceeded()) {
                HashMap<String, Long> dict = TextResource.getDict();
                System.out.println("\r\nFinal report:");
                for (Map.Entry<String, Long> entry : dict.entrySet()) {
                    System.out.println("\t" + entry.getKey() + ": " + entry.getValue());
                }
            }
        }
        catch(Exception ex){
            TextResource.stopResourceParsing();
            closeThreads(threads);
            log.severe(ex.getMessage());
        }

    }
    private static void closeThreads(Thread[] threads){
        for(Thread thread: threads){
            try{
                if(thread != null && thread.isAlive()){
                    thread.join(1000);
                    if(thread.isAlive())
                        thread.interrupt();
                }
            }
            catch (InterruptedException ex){
                log.config("Thread closing error: " + ex.getMessage());
            }
        }
    }
}

package innopolis.igor;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import resources.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Main - сновной класс, содержащий точку входа в программу
 */
public class Main {

    public static final Logger logger = Logger.getLogger(Main.class);

    static {
        DOMConfigurator.configure("src/innopolis/igor/log4j.xml");
    }

    /**
     * Точка входа в программу
     *
     * @param args список путей к ресурсам, которые программа будет обрабатывать
     */
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
                logger.trace("\r\nFinal report:\r\n");
                for (Map.Entry<String, Long> entry : dict.entrySet()) {
                    logger.trace("\t" + entry.getKey() + ": " + entry.getValue()+"\r\n");
                }
            }
        }
        catch(Exception ex){
            TextResource.stopResourceParsing();
            closeThreads(threads);

            logger.error(ex.getMessage() + "\r\n");
        }
    }

    /**
     * Метод корректно завершает работу запущенных потоков
     *
     * @param threads массив потоков, которые необходимо завершить
     */
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
                logger.error("Thread closing error: " + ex.getMessage() + "\r\n");
            }
        }
    }
}

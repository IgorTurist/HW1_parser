package resources;

import innopolis.igor.Main;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.HashMap;

/**
 * Класс предназначен для подсчета количества разных слов в текстовом ресурсе
 */
public abstract class TextResource implements Runnable{

    protected static final Logger logger = Logger.getLogger(Main.class);

    static {
        DOMConfigurator.configure("src/resources/log4j.xml");
        dict = new HashMap<>(TextResource.INIT_CAPACITY);
    }

    /**
     * Метод позволяет запускать процесс обработки данных ресурса в отдельном потоке
     */
    @Override
    public void run() {
        try{
            finish = false;
            runResourceParsing();
        }
        catch(Exception ex){
            finish = true;
            dict.clear();

            logger.error(ex.getMessage() + "\r\n");
        }

        logger.trace("Thread (id=" + Thread.currentThread().getId() + ") is going to be finished since the moment.\r\n");
    }

    /**
     * Метод проверяет корректность ресурса, находящегося по адресу ресурса
     * Доступ к адресу ресурса можно получить через методы getPath() и setPath()
     *
     * @return true, если ресурс доступен для считывания по указанному адресу
     * false - в противном случае
     */
    public abstract boolean isValid();

    /**
     * Метод возвращает адрес ресурса
     *
     * @return адрес ресурса
     */
    public String getPath() {
        return path;
    }

    /**
     * Метод устанавливает адрес ресурса
     *
     * @param path адрес ресурса
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Метод возвращает статистические данные о количестве различных слов в ресурсе
     *
     * @return коллекция, содержащая информацию о количестве различных слов в ресурсе
     * в виде пар вида {"слово" - "количество в ресурсе"}
     */
    public static HashMap<String, Long> getDict() {
        return dict;
    }

    /**
     * Интерфейс метода запуска подсчета слов в ресурсе
     *
     * @throws Exception возникает в случае ошибок при обработке ресурса
     */
    public abstract void runResourceParsing()throws Exception;

    /**
     * Останавливает подсчет количества слов в ресурсе в случае работы в отдельном потоке
     */
    public static void stopResourceParsing(){
        finish = true;
    }

    /**
     * Метод возвращает информацию о безошибочности обработки ресурса
     *
     * @return если ресурс обработан без ошибок, метод возвращает true, в противном случае false
     */
    public static boolean isParsingSucceeded(){
        return !finish;
    }

    protected static volatile boolean finish = false;
    protected String path = "";
    private static final int INIT_CAPACITY = 100;
    private static volatile HashMap<String,Long> dict;
}

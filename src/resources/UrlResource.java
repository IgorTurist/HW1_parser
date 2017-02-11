package resources;


import parser.TextParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Класс предназначен для подсчета количества разных слов в текстовом ресурсе,
 * который расположен в удаленной файловой системе
 */
public class UrlResource extends TextResource {

    /**
     * Конструктор класса
     * @param path адрес ресурса в файловой системе
     */
    public UrlResource(String path){
        this.path = path;
    }

    /**
     * Метод проверяет корректность ресурса, находящегося по адресу, хранимому в "path"
     *
     * @return true, если ресурс доступен для считывания по указанному ресурсу
     */
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

    /**
     * Метод запускает подсчет слов в ресурсе
     *
     * @throws Exception возникает в случае ошибок при обработке ресурса
     */
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

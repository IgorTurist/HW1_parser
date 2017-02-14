package resources;

import parser.TextParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Класс предназначен для подсчета количества разных слов в текстовом ресурсе,
 * который расположен в файловой системе
 */
public class FileResource extends TextResource {

    /**
     * Конструктор класса
     * @param path адрес ресурса в файловой системе
     */
    public FileResource(String path){
        this.path = path;
    }

    /**
     * Метод проверяет корректность ресурса, находящегося по адресу, хранимому в "path"
     *
     * @return true, если ресурс доступен для считывания по указанному ресурсу
     */
    @Override
    public boolean isValid() throws NullPointerException{
        File f = new File(path);
        return f.isFile();
    }

    /**
     * Метод запускает подсчет слов в ресурсе
     *
     * @throws Exception возникает в случае ошибок при обработке ресурса
     */
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
        }
        catch (Exception ex) {
            synchronized (TextResource.getDict()) {
                TextResource.getDict().clear();
            }
            throw ex;
        }
    }
}

package resources;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Класс является представлением ресурса, содержащего тест
 */
public class TextResourceFactory {
    /**
     * Метод создает экземпляр одного из потомков класса TextResource.
     * Выбор осуществляется на основе анализа адреса ресурса.
     * Адрес должен быть корректен. Ресурс по адресу должен быть доступен для считывания.
     *
     * @param path адрес ресурса
     * @return один из наследников класса TextResource
     * @throws Exception выбрасывается если путь к ресурсу не корректен, либо ресурс не доступен.
     */
    public static TextResource createTextResource (String path)throws Exception {
        try {
            File f = new File(path);
            if (f.isFile())
                return new FileResource(path);

            URL url = new URL(path);
            InputStream is = url.openStream();
            if(is.available() > 0)
                return new UrlResource(path);
            else
                throw new Exception("Unavailable url: \"" + path + "\"\r\nFix it and try again.");
        }
        catch(NullPointerException ex){
            throw new Exception("There is an empty path to a resource.\r\nExclude empty paths from input data and try again");
        }
        catch(MalformedURLException ex){
            throw new Exception("\"" + path + "\" - this is malformed url or incorrect file system path.\r\nFix it and try again.");
        }
    }
}

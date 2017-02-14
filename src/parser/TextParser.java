package parser;

import innopolis.igor.Main;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.Map;

/**
 * Класс предназначен для сбора статистики о количестве различных слов в тексте
 */
public class TextParser {

    private static final Logger logger = Logger.getLogger(Main.class);

    static {
        DOMConfigurator.configure("src/parser/log4j.xml");
    }

    /**
     * Метод считет количество вхождений каждого слова в строку parsedStr
     *
     * @param parsedStr строка, в которой ведется подсчет слов
     * @param dict коллекция, в которую записывается результат в формате пар {"слово" - "количество в строке"}
     * @throws Exception выбрасывается в случае, если строка содержит недопустимые символы.
     * Допустимые символы: кирилические символы, цифры и знаки препинания(,.:…;!?()"'-—).
     */
    static public void getStringStatistic(String parsedStr, Map<String, Long> dict) throws Exception{
        String[] words = parsedStr.split("\\s");

        for(int i = 0;i<words.length;i++){

            if(words[i].length() == 0 || words[i].matches("\\s*-|—\\s*"))
                continue;

            words[i] = words[i].toLowerCase();

            if(hasIllegalChar(words[i]))
                throw new Exception("\""+words[i] + "\"" + " has illegal characters\r\nCorrect it and try again.");

            words[i] = removePunctuation(words[i]);
            synchronized (dict) {
                if (dict.containsKey(words[i])) {
                    dict.put(words[i], dict.get(words[i]) + 1);
                    logger.trace("Thread (id=" + Thread.currentThread().getId() +
                            ") increased value for \"" + words[i] + "\"\r\n");
                }
                else {
                    dict.put(words[i], new Long(1));
                    logger.trace("Thread (id=" + Thread.currentThread().getId() +
                            ") added new word \"" + words[i] + "\"\r\n");
                }
            }
        }
    }

    /**
     * Метод проверяет вхождение в строку недопустимых символов.
     * Допустимые символы: кирилические символы, цифры и знаки препинания(,.:…;!?()"'-—)
     *
     * @param str анализируемая строка
     * @return true, если строка содержит недопустимые символы, false - в противном случае.
     */
    private static boolean hasIllegalChar(String str){
        return !str.matches("^[()а-яё:;!?\\.…,--——\\\"\\'\\d]+$");
    }

    /**
     * Выделяет слово из строки, убирая знаки препинания
     *
     * @param str анализируемая строка
     * @return слово
     */
    private static String removePunctuation(String str){
        String res = str;
        
        while(res.matches("^.+[),\\.…:;!?\\\"\\']$"))
            res = res.substring(0,res.length()-1);

        if(res.matches("^[\\\"\\'(].*$"))
            res = res.substring(1);

        if(res.matches("\\s*-|—\\s*"))
            res = "-";

        return res;
    }
}

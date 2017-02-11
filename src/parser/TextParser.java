package parser;

import java.util.Map;

/**
 * Created by igor on 08.02.2017.
 */
public class TextParser {
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
                    System.out.println("Thread (id=" + Thread.currentThread().getId() + ") increased value for \"" + words[i] + "\"");
                }
                else {
                    dict.put(words[i], new Long(1));
                    System.out.println("Thread (id=" + Thread.currentThread().getId() + ") added new word \"" + words[i] + "\"");
                }
            }
        }
    }

    private static boolean hasIllegalChar(String str){
        return !str.matches("^[()а-яё:;!?\\.…,--——\\\"\\'\\d]+$");
    }

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

package innopolis.igor;

import resources.*;

public class Main {

    private static String[] paths = {/*"./test_data/normal_file.txt",
                                    "./test_data/ghost_file.txt",
                                    "./test_data",
                                    "file:///C:/Users/igor/IdeaProjects/HW1_parser/test_data/normal_file.txt",
                                    "http://joomla.ru/robots.txt",
                                    "https://github.com/binnocl/innoshop.me/blob/master/README.md",*/
                                    "http://github.com/ddddddddrrrrrrrrrrrrrrrrrrrrr.txt"};

    public static void main(String[] args) {
        try {

//            if (args.length == 0)
//                throw new Exception("There are no any resources to process.\r\nRerun the program with not empty resources list.");

            for(String path: paths){
                TextResource res = TextResouceFactory.createTextResource(path);
                if(res != null) {
                    System.out.println(path + " " + res.getClass());
                }
                else System.out.println("Null Pointer");
                System.out.println("------------------------------");
            }
        }
        catch(Exception ex){
            System.out.println("Error occurred:" + ex.getMessage());
//Don't forget to free the resources
        }
    }
}

package innopolis.igor;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 0)
                throw new Exception("There are no any resources to process.\r\nRerun the program with not empty resources list.");

            TextResource resource;
            for(int i = 0;i<args.length;i++) {
                TextResource.ResourceType type = TextResource.getResourceType(args[i]);
                if(type == TextResource.ResourceType.FILE){
                    resource = new innopolis.igor.FileResource(args[i]);
                }
                else if(type == TextResource.ResourceType.URL){
                    resource = new innopolis.igor.UrlResource(args[i]);
                }
                else{
                    throw new Exception("\"" + args[0] + "\"" +" has incorrect type.\r\nCheck input data and try again.");
                }

                if(!resource.isValid())
                    throw new Exception("\"" + args[0] + "\"" + " can't be opened.\r\nCheck it's  accessibility and try again");


            }
        }
        catch(Exception ex){
            System.out.println("Error occurred:" + ex.getMessage());
//Don't forget to free the resources
        }
    }
}

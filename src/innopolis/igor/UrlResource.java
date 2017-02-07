package innopolis.igor;

/**
 * Created by igor on 07.02.2017.
 */
public class UrlResource extends TextResource {
    public UrlResource(){
        super();
    }

    public UrlResource(String path){
        this.path = path;
    }

    @Override
    public boolean isValid(){
        boolean res = false;
//this is a stub. Don't forget to fill it!!!
        return res;
    }

    @Override
    public void runResourceParsing(){
//this is a stub. Don't forget to fill it!!!
    }
}

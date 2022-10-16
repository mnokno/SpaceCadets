import java.util.HashMap;
import java.util.Map;

public class Variables {

    private final Map<String, Integer> integerVariables;
    private final Variables parentVariables;

    public Variables(Variables parentVariables){
        this.parentVariables = parentVariables;
        this.integerVariables = new HashMap<String, Integer>();
    }

    public int getInt(String name){
        //TODO
        return 0;
    }

    public void setInt(String name){
        // TODO
    }

    public void changeIntBy(String name, int amount){
        // TODO
    }

    public String generateLog(){
        // TODO
        return null;
    }
}

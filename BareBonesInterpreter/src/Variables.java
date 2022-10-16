import java.util.HashMap;
import java.util.Map;

public final class Variables {

    private final Map<String, Integer> integerVariables = new HashMap<String, Integer>();

    public int getInt(String name){
        // If the variables haven't been defined yet, we tread this request as a definition statement
        if (!integerVariables.containsKey(name)){
            integerVariables.put(name, 0);
        }
        return integerVariables.get(name);
    }

    public void setInt(String name, int value){
        if (integerVariables.containsKey(name)){
            integerVariables.replace(name, value);
        }
        else{
            integerVariables.put(name, value);
        }
    }

    public void changeIntBy(String name, int amount){
        if (integerVariables.containsKey(name)){
            integerVariables.replace(name, integerVariables.get(name) + amount);
        }
        else{
            // this is because if the variable does not exist yet, we initialize it with 0 then add amount
            integerVariables.put(name, amount);
        }
    }

    public String generateLog(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: integerVariables.keySet()) {
            stringBuilder.append(key).append(" : ").append(integerVariables.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }
}

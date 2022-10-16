import java.util.HashMap;
import java.util.Map;

public final class Variables {

    private static final Map<String, Integer> integerVariables = new HashMap<String, Integer>();

    public static int getInt(String name){
        // checks if we need to find the value or the name is the value
        if (isInt(name)){
            return Integer.parseInt(name);
        }
        // if the variables haven't been defined yet, we tread this request as a definition statement
        if (!integerVariables.containsKey(name)){
            integerVariables.put(name, 0);
        }
        return integerVariables.get(name);
    }

    public static void setInt(String name, int value) throws Exception {
        validateVariableName(name);
        if (integerVariables.containsKey(name)){
            integerVariables.replace(name, value);
        }
        else{
            integerVariables.put(name, value);
        }
    }

    public static void changeIntBy(String name, int amount) throws Exception {
        validateVariableName(name);
        if (integerVariables.containsKey(name)){
            integerVariables.replace(name, integerVariables.get(name) + amount);
        }
        else{
            // this is because if the variable does not exist yet, we initialize it with 0 then add amount
            integerVariables.put(name, amount);
        }
    }

    public static String generateLog(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: integerVariables.keySet()) {
            stringBuilder.append(key).append(" : ").append(integerVariables.get(key)).append("\n");
        }
        return stringBuilder.toString();
    }

    private static boolean isInt(String name){
        try {
            Integer.parseInt(name);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private static void validateVariableName(String name) throws Exception {
        if (isInt(name)){
            throw new Exception("Variable names cant be a number!");
        }
    }
}

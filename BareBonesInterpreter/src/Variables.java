import java.util.HashMap;
import java.util.Map;

/**
 * Variables is a static class that is used to store all variable
 * this version of the interpreter only supports integer values and
 * all variables are stored in a global scope, hence this class is static.
 * <p><p></p>
 * Scope variables can be implemented by caning this class fromm final to non-finale
 * and adding reference to a Variables instance below this one (creating a linked list)
 * then when fetching values you check the current Variables if it does not contain the variable
 * you go dawn one scope until you find the variable or fail in the global scope, in case of failed you
 * define the variable in the Variables where the initial call has made (top of the recursive stack).
 * In addition, Scope will have to store reference to its own variables.
 */
public final class Variables {

    private static final Map<String, Integer> integerVariables = new HashMap<String, Integer>();

    /**
     * Gets value corresponding to the given name if the vale has not been.
     * initialed it will be initialized to 0, and 0 will be returned.
     * @param name Name of the variable for witch the value will be fetched.
     * @return Returns value corresponding the passed name.
     */
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

    /**
     * Sets the given variable to the passed value, if the variable has been
     * initialized before it will overwrite its value.
     * @param name The name of the variable that the value will be set to (operand).
     * @param value The value that the variable will be set to.
     * @throws Exception throws an exception if the name is invalid.
     */
    public static void setInt(String name, int value) throws Exception {
        validateVariableName(name);
        if (integerVariables.containsKey(name)){
            integerVariables.replace(name, value);
        }
        else{
            integerVariables.put(name, value);
        }
    }

    /**
     * Increases the given variable by the passed amount, pass negative
     * amount for subtraction and position for addition. If the variable has not
     * yet been initialized it will be initialized to the passed amount since the
     * default value is 0.
     * @param name The name of the variable to be changed (operand).
     * @param amount The amount that the variable will be changed by, can be position or negative.
     * @throws Exception throws an exception if the name is invalid.
     */
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

    /**
     * Generates a log contain values of all current variables.
     * @return return the generated log.
     */
    public static String generateLog(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String key: integerVariables.keySet()) {
            stringBuilder.append(key).append(" : ").append(integerVariables.get(key)).append("\n");
        }
        return stringBuilder.toString().trim();
    }

    /**
     * Check weather or not the passed string is an integer.
     * @param name String to be checked.
     * @return return true if the passed string is an integer, false otherwise.
     */
    private static boolean isInt(String name){
        try {
            Integer.parseInt(name);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Throws an exception if the variable name is invalid (variable names cant be numbers).
     * @param name name to be validated.
     * @throws Exception thrown when the given name is invalid.
     */
    private static void validateVariableName(String name) throws Exception {
        if (isInt(name)){
            throw new Exception("Variable names cant be a number!");
        }
    }
}

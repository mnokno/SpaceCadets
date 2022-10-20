import java.util.HashMap;
import java.util.Map;

/**
 * Function can be used to define function with no parameters (in this version of the language).
 * Once a function is defined it can be used anywhere from the code to execute its body.
 */
public class Function extends Command {

    private static final Map<String, Function> definedFunctions = new HashMap<String, Function>();
    private final Scope functionBody;

    /**
     * Creates a new function with the given body and name.
     * It can be later called from anywhere from the program using
     * its name to execute its body.
     * @param functionName Name used to reference this function
     * @param functionBody Body associated with the given function name
     */
    public Function(String functionName, Scope functionBody){
        this.functionBody = functionBody;
        definedFunctions.put(functionName, this);
    }

    /**
     * Executables body of this function, used defined function do not support function
     * variable due to the fast the there are no local scopes.
     * @throws Exception Throws an exception if the interpreters encounter invalid code.
     */
    @Override
    public void execute() throws Exception {
        functionBody.execute();
    }

    /**
     * Gets and returns reference to function with given name
     * (function have to been defined before they can be accessed)
     * @param name Name of the function to be accessed;
     * @return Returns function associated with the given name.
     * @throws Exception Throws an exception if given function name has never been defined.
     */
    public static Function getFunction(String name) throws Exception {
        if (definedFunctions.containsKey(name)){
            return definedFunctions.get(name);
        }
        else{
            throw new Exception("Function named: " + name + " has never been defined");
        }

    }
}

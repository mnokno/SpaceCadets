/**
 * Executable is a wrapper class is used to start execution of the code.
 */
public class Executable {

    private final Scope mainScope;

    /**
     * Creates a new Executable instance that can be used to execute the program
     * and generate a variable log to view result of execution.
     * @param mainScope The scope that the program will start from.
     */
    public Executable(Scope mainScope){
        this.mainScope = mainScope;
    }

    /**
     * Executes the code.
     * @throws Exception Throws an exception if the interpreters encounter invalid code.
     */
    public void execute() throws Exception {
        mainScope.execute();
    }

    /**
     * Generates a log contain values of all variables from the global scope
     * (there is only global scope in this version of the interpreter).
     * @return returns the generated log.
     */
    public String getLog(){
        return Variables.generateLog();
    }
}
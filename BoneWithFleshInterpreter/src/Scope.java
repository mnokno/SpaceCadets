/**
 * Scope contain a sequence of command to be executed,
 * it may be the case that there are many nested scopes.
 * In this version there is only one global variable scope,
 * hence we don't need to a Variables instance in this class.
 */
public class Scope extends Callable {

    private final Callable[] callables;

    /**
     * Creates a new scope object with the passed command, this version of the
     * interpreter does not support scope variable hence no Variables parameter.
     * @param callables List of callables for this scope in chronology order.
     */
    public Scope(Callable[] callables){
        this.callables = callables;
    }

    /**
     * Executes the contents of this scope.
     * @throws Exception throws an exception if the interpreter encounter an issue in this scope.
     */
    @Override
    public void execute() throws Exception {
        for (Callable callable : callables) {
            callable.execute();
        }
    }
}

/**
 * Scope contain a sequence of command to be executed,
 * it may be the case that there are many nested scopes.
 * In this version there is only one global variable scope,
 * hence we don't need to a Variables instance in this class.
 */
public class Scope extends Command {

    private final Command[] commands;

    /**
     * Creates a new scope object with the passed command, this version of the
     * interpreter does not support scope variable hence no Variables parameter.
     * @param commands List of commands for this scope in chronology order.
     */
    public Scope(Command[] commands){
        this.commands = commands;
    }

    /**
     * Executes the contents of this scope.
     * @throws Exception throws an exception if the interpreter encounter an issue in this scope.
     */
    @Override
    public void execute() throws Exception {
        for (Command command: commands) {
            command.execute();
        }
    }
}

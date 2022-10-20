/**
 * Base class used to group object that can execute their respected code.
 */
public abstract class Callable {
    /**
     * Executes the command
     * @throws Exception Throws exception th interpreter encounters problems during execution of the command.
     */
    public abstract void execute() throws Exception;
}

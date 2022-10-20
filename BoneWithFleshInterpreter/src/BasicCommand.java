/**
 * BasicCommand instance can be used to execute BasicAction's
 * on specified operand.
 * <p>
 * NOTE: the operand has to be a variable (direct addressing)
 */
public class BasicCommand extends Callable {

    private final BasicAction basicAction;
    private final String operand;

    /**
     * Creates a new BasicCommand that can be used to execute the passed
     * BasicAction on the given operand.
     * <p>
     * NOTE: the operand has to be a variable (direct addressing)
     * @param basicAction Action that will be performed on the operand.
     * @param operand Operand that the action will be performed on.
     */
    public BasicCommand(BasicAction basicAction, String operand){
        this.basicAction = basicAction;
        this.operand = operand;
    }

    /**
     * Executes basic the basic command, variables are fetched from the global scope
     * (this version fo the interpreter only supports the global scope).
     * @throws Exception Thrown an execution if the interpolator encounter any problems in the code.
     */
    @Override
    public void execute() throws Exception {
        switch (basicAction){
            case clear -> Variables.setInt(operand, 0);
            case incr -> Variables.changeIntBy(operand, 1);
            case decr -> Variables.changeIntBy(operand, -1);
        }
    }
}

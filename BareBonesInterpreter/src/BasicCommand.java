public class BasicCommand extends Command {

    private final BasicAction basicAction;
    private final String operand;

    public BasicCommand(BasicAction basicAction, String operand){
        this.basicAction = basicAction;
        this.operand = operand;
    }

    @Override
    public void execute() {
        switch (basicAction){
            case clear -> Variables.setInt(operand, 0);
            case incr -> Variables.changeIntBy(operand, 1);
            case decr -> Variables.changeIntBy(operand, -1);
        }
    }
}

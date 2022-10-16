public class BasicCommand extends Command {

    private final BasicAction basicAction;
    private final String operand;
    private final Scope commandScope;

    public BasicCommand(BasicAction basicAction, String operand, Scope commandScope){
        this.basicAction = basicAction;
        this.operand = operand;
        this.commandScope = commandScope;
    }

    @Override
    public void execute() {
        // TODO
    }
}

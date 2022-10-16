public class WhileLoop extends Command {

    private final Condition condition;
    private final Scope loopScope;
    private final Scope conditionScope;

    public WhileLoop(Condition condition, Scope loopScope, Scope conditionScope){
        this.condition = condition;
        this.loopScope = loopScope;
        this.conditionScope = conditionScope;
    }

    @Override
    public void execute() {
        //TODO
    }
}

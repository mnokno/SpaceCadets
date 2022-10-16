public class WhileLoop extends Command {

    private final Condition condition;
    private final Scope loopScope;

    public WhileLoop(Condition condition, Scope loopScope){
        this.condition = condition;
        this.loopScope = loopScope;
    }

    @Override
    public void execute() {
        while (condition.evaluate()){
            loopScope.execute();
        }
    }
}

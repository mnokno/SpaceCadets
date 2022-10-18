/**
 * WhileLoop is a control structure that will execute its scope as long as its
 * comparisonCondition evaluates to true. The comparisonCondition is evaluated before starting each new iteration.
 */
public class WhileLoop extends Command {

    private final Condition condition;
    private final Scope loopScope;

    /**
     * WhileLoop is a control structure that will execute its scope as long as its.
     * comparisonCondition evaluates to true. The comparisonCondition is evaluated before starting each new iteration.
     * @param condition Condition that will be evaluated before each new iteration.
     * @param loopScope Scope that will be executed during each iteration.
     */
    public WhileLoop(Condition condition, Scope loopScope){
        this.condition = condition;
        this.loopScope = loopScope;
    }

    /**
     * Execute the while statement returning when the loops comparisonCondition evaluated to false.
     * @throws Exception throws an exception if something brakes in the loops scope.
     */
    @Override
    public void execute() throws Exception {
        while (condition.evaluate()){
            loopScope.execute();
        }
    }
}

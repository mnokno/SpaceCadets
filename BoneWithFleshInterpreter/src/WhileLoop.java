/**
 * WhileLoop is a control structure that will execute its scope as long as its
 * comparisonCondition evaluates to true. The comparisonCondition is evaluated before starting each new iteration.
 */
public class WhileLoop extends Command {

    private final ComparisonCondition comparisonCondition;
    private final Scope loopScope;

    /**
     * WhileLoop is a control structure that will execute its scope as long as its.
     * comparisonCondition evaluates to true. The comparisonCondition is evaluated before starting each new iteration.
     * @param comparisonCondition ComparisonCondition that will be evaluated before each new iteration.
     * @param loopScope Scope that will be executed during each iteration.
     */
    public WhileLoop(ComparisonCondition comparisonCondition, Scope loopScope){
        this.comparisonCondition = comparisonCondition;
        this.loopScope = loopScope;
    }

    /**
     * Execute the while statement returning when the loops comparisonCondition evaluated to false.
     * @throws Exception throws an exception if something brakes in the loops scope.
     */
    @Override
    public void execute() throws Exception {
        while (comparisonCondition.evaluate()){
            loopScope.execute();
        }
    }
}

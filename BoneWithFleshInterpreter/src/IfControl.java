/**
 * IfControl is a control structure that executes its scope if its
 * condition evaluates to true. IfControl can be chained to another
 * IfControl that is evaluated if the current one fails creating an if-else control.
 * IfControl with static true condition can be used to creat an else control black.
 */
public class IfControl extends Callable {

    private final Condition condition;
    private final Scope scope;
    private IfControl failCaseElseStatement;

    /**
     * Creates a new IfControl that will can be used to execute given scope when
     * the passed condition evaluates to true.
     * @param condition Condition that will be used to decide weather or not to execute ifs scope.
     * @param scope Scope that will be executed if the condition evaluates to true.
     */
    public IfControl(Condition condition, Scope scope){
        this.condition = condition;
        this.scope = scope;
        this.failCaseElseStatement = null;
    }

    /**
     * Chains this IfControl to an else or else-if.
     * @param elseCase The else case that will be executed if if-condition fails.
     */
    public void setElseCase(IfControl elseCase){
        this.failCaseElseStatement = elseCase;
    }

    /**
     * Executes scope of this "if" statement if its condition evaluated to true.
     * In case the condition evaluated to false it executes its chained else statement (if it has one).
     * @throws Exception Throws an exception if the interpreters encounter invalid code.
     */
    @Override
    public void execute() throws Exception {
        if (condition.evaluate()){
            scope.execute();
        }
        else if (failCaseElseStatement != null){
            failCaseElseStatement.execute();
        }
    }
}

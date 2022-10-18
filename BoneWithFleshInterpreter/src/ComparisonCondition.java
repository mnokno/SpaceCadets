/**
 * ComparisonCondition is used to preform comparison for control structures.
 * <p>
 * NOTE: The current version of the interpreter only support integers
 * hence the ComparisonCondition can only be used directly withing the control structure.
 */
public class ComparisonCondition {

    private final String rightOperand;
    private final String leftOperand;
    private final ComparisonOperator comparisonOperator;

    /**
     * Creates a condition that will use the specified operator to on operands to
     * make decision for control structures.
     * @param leftOperand Value on the left of the equation (comparison)
     * @param rightOperand Value on the right of the equation (comparison)
     * @param comparisonOperator Operation that will be used to compare the operands
     */
    public ComparisonCondition(String leftOperand, String rightOperand, ComparisonOperator comparisonOperator){
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.comparisonOperator = comparisonOperator;
    }

    /**
     * Compares the rightOperand against leftOperand used the specified (at initialization) ComparisonOperator
     * @return returns result of this comparison
     */
    public boolean evaluate(){
        return switch (comparisonOperator){
            case equal -> Variables.getInt(leftOperand) == Variables.getInt(rightOperand);
            case notEqual -> Variables.getInt(leftOperand) != Variables.getInt(rightOperand);
            case greater -> Variables.getInt(leftOperand) > Variables.getInt(rightOperand);
            case greaterOrEqual -> Variables.getInt(leftOperand) >= Variables.getInt(rightOperand);
            case less -> Variables.getInt(leftOperand) < Variables.getInt(rightOperand);
            case lessOrEqual -> Variables.getInt(leftOperand) <= Variables.getInt(rightOperand);
        };
    }
}

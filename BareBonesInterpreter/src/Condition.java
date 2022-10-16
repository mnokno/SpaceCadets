public class Condition {

    private final String rightOperand;
    private final String leftOperand;
    private final ComparisonOperator comparisonOperator;

    public Condition(String rightOperand, String leftOperand, ComparisonOperator comparisonOperator){
        this.rightOperand = rightOperand;
        this.leftOperand = leftOperand;
        this.comparisonOperator = comparisonOperator;
    }

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

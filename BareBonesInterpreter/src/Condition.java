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
        // TODO
        return false;
    }
}

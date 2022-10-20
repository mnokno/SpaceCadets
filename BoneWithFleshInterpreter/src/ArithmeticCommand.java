/**
 * ArithmeticCommand cam be used to preform arithmetic operation on operands
 */
public class ArithmeticCommand extends Callable {

    private final ArithmeticOperator arithmeticOperator;
    private final String operandSave;
    private final String operandLeft;
    private final String operandRight;

    /**
     * Creates a new ArithmeticCommand that can perform specified arithmetic operation on given operands.
     * @param arithmeticOperator operation to be performed on the variable.
     * @param operandSave name of the variable where the vale will be saved.
     * @param operandLeft operand on the left side of the equation.
     * @param operandRight operand on the right side of the equation.
     */
    public ArithmeticCommand(ArithmeticOperator arithmeticOperator, String operandSave, String operandLeft, String operandRight){
        this.arithmeticOperator = arithmeticOperator;
        this.operandSave = operandSave;
        this.operandLeft = operandLeft;
        this.operandRight = operandRight;
    }

    /**
     * Performs specified arithmetic operation on operandLeft and operandRight, saving teh result in operandSave
     * @throws Exception Thrown an execution if the interpolator encounter any problems in the code.
     */
    @Override
    public void execute() throws Exception {
        switch (arithmeticOperator){
            case add -> Variables.setInt(operandSave, Variables.getInt(operandLeft) + Variables.getInt(operandRight));
            case sub -> Variables.setInt(operandSave, Variables.getInt(operandLeft) - Variables.getInt(operandRight));
            case mul -> Variables.setInt(operandSave, Variables.getInt(operandLeft) * Variables.getInt(operandRight));
            case div -> Variables.setInt(operandSave, Variables.getInt(operandLeft) / Variables.getInt(operandRight));
            case mod -> Variables.setInt(operandSave, Variables.getInt(operandLeft) % Variables.getInt(operandRight));
        }
    }
}

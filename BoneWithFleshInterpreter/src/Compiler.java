import java.util.ArrayList;
import java.util.Arrays;

/**
 * Compile is a static class that can be used to compile code
 * from a file to an Executable object that can be used to
 * execute the program.
 * <p>
 * NOTE: The Compiler will simply crash if the syntax is invalid
 */
public final class Compiler {

    private static final String[] arithmeticOperators = new String[] {"+", "-", "*", "/", "%"};
    private static int currentLine;
    private static String[] lines;

    /**
     * @param lines Array of the lines of code in chronological order.
     * @return Returns an Executable object that can can be used to execute teh passed program.
     * @throws Exception Throws an exception is the completer encounter any invalid syntax.
     */
    public static Executable compile(String[] lines) throws Exception {
        Compiler.lines = lines;
        flattenLines();
        Compiler.currentLine = 0;
        return new Executable(compileScope());
    }

    /**
     * Used be "compile" method to handle nested scope
     * @return Returns the complied scope.
     * @throws Exception Throws an exception if encounters invalid syntax.
     */
    private static Scope compileScope() throws Exception {
        ArrayList<Command> scopeCommands = new ArrayList<Command>();

        while (currentLine < lines.length && !lines[currentLine].equals("end")){
            String[] parts = lines[currentLine].split(" ");
            currentLine++;
            if (parts.length == 5 && Arrays.asList(arithmeticOperators).contains(parts[3])){
                scopeCommands.add(new ArithmeticCommand(convertToArithmeticOperator(parts[3]),
                        parts[0], parts[2], parts[4]));
            }
            else{
                switch (parts[0]) {
                    case "clear" -> scopeCommands.add(new BasicCommand(BasicAction.clear, parts[1]));
                    case "incr" -> scopeCommands.add(new BasicCommand(BasicAction.incr, parts[1]));
                    case "decr" -> scopeCommands.add(new BasicCommand(BasicAction.decr, parts[1]));
                    case "while" -> scopeCommands.add(
                            new WhileLoop(
                                    new ComparisonCondition(parts[1], parts[3], convertToComparisonOperator(parts[2])),
                                    compileScope()
                            ));
                }
            }
        }
        currentLine++;

        return new Scope(scopeCommands.toArray(new Command[0]));
    }

    /**
     * Flatten the array of line, removing all from beginning of lines
     * and replaces any duplicated spaces with single spaces.
     */
    private static void flattenLines(){
        for (int i = 0; i < lines.length; i++){
            while (lines[i].contains("  ")){
                lines[i] = lines[i].replace("  ", " ");
            }
            if (lines[i].charAt(0) == ' '){
                lines[i] = lines[i].substring(1);
            }
        }
    }

    /**
     * Converts the passed String to ComparisonOperator.
     * @param operatorAsString String to be parsed to ComparisonOperator.
     * @return Return ComparisonOperator represented by the passed string.
     * @throws Exception Throws an exception if the passed string was not a valid ComparisonOperator.
     */
    private static ComparisonOperator convertToComparisonOperator(String operatorAsString) throws Exception {
        return switch (operatorAsString){
            case "not", "!=" -> ComparisonOperator.notEqual;
            case "equal", "==" -> ComparisonOperator.equal;
            case "greater", ">" -> ComparisonOperator.greater;
            case "greaterOrEqual", ">=" -> ComparisonOperator.greaterOrEqual;
            case "less", "<" -> ComparisonOperator.less;
            case "lessOrEqual", "<=" -> ComparisonOperator.lessOrEqual;
            default -> throw new Exception(operatorAsString + " is not a valid comparison operation!");
        };
    }

    /**
     * Converts the passed String to ArithmeticOperator.
     * @param operatorAsString String to be parsed to ArithmeticOperator.
     * @return Return ArithmeticOperator represented by the passed string.
     * @throws Exception Throws an exception if the passed string was not a valid ArithmeticOperator.
     */
    private static ArithmeticOperator convertToArithmeticOperator(String operatorAsString) throws Exception {
        return switch (operatorAsString){
            case "+" -> ArithmeticOperator.add;
            case "-" -> ArithmeticOperator.sub;
            case "*" -> ArithmeticOperator.mul;
            case "/" -> ArithmeticOperator.div;
            case "%" -> ArithmeticOperator.mod;
            default -> throw new Exception(operatorAsString + " is not a valid arithmetic operation!");
        };
    }
}

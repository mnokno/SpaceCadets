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
    private static final String[] buildInFunction = new String[] {"log", "input"};
    private static int currentLine;
    private static String[] lines;
    private static IfControl ifControl;

    /**
     * @param code String conditioning code to be complied.
     * @return Returns an Executable object that can can be used to execute teh passed program.
     * @throws Exception Throws an exception is the completer encounter any invalid syntax.
     */
    public static Executable compile(String code) throws Exception {
        Compiler.lines = code.replaceAll("\n", "").split(";");
        flattenLines();
        removeNonCodeLines();
        Compiler.currentLine = 0;
        Compiler.ifControl = null;
        compileFunctions();
        removeFunctionsFromCode();
        Compiler.currentLine = 0;

        return new Executable(compileScope());
    }

    /**
     * Compiles all function found if the code, they can be later referred by colling their names.
     * @throws Exception Throws an exception if encounters invalid syntax.
     */
    private static void compileFunctions() throws Exception {
        while (currentLine < lines.length){
            while (currentLine < lines.length && !lines[currentLine].split(" ")[0].equals("function")){
                currentLine++;
            }
            if (currentLine < lines.length){
                String functionName = lines[currentLine].split(" ")[1];
                currentLine++;
                new Function(functionName, compileScope());
            }
        }
    }

    /**
     * Removes lines that are definition or bodies of a function
     */
    private static void removeFunctionsFromCode(){
        currentLine = 0;
        ArrayList<String> newLines = new ArrayList<String>();
        while (currentLine < lines.length){
            while (currentLine < lines.length && !lines[currentLine].split(" ")[0].equals("function")){
                newLines.add(lines[currentLine]);
                currentLine++;
            }
            while (currentLine < lines.length && !lines[currentLine].split(" ")[0].equals("end")){
                currentLine++;
            }
            currentLine++;
        }
        lines = newLines.toArray(new String[0]);
    }

    /**
     * Used be "compile" method to handle nested scope
     * @return Returns the complied scope.
     * @throws Exception Throws an exception if encounters invalid syntax.
     */
    private static Scope compileScope() throws Exception {
        ArrayList<Callable> scopeCallables = new ArrayList<Callable>();

        while (currentLine < lines.length &&
                !lines[currentLine].equals("end")){

            String[] parts = lines[currentLine].split(" ");
            currentLine++;

            if (parts.length == 5 && Arrays.asList(arithmeticOperators).contains(parts[3])){
                scopeCallables.add(new ArithmeticCommand(convertToArithmeticOperator(parts[3]),
                        parts[0], parts[2], parts[4]));
            }
            else if (Arrays.asList(buildInFunction).contains(parts[0])){
                switch (parts[0]){
                    case "log" -> scopeCallables.add(new Log(parts[1]));
                    case "input" -> scopeCallables.add(new Input(parts[1]));
                }
            }
            else if (parts.length == 1){
                scopeCallables.add(Function.getFunction(parts[0]));
            }
            else{
                switch (parts[0]) {
                    case "clear" -> scopeCallables.add(new BasicCommand(BasicAction.clear, parts[1]));
                    case "incr" -> scopeCallables.add(new BasicCommand(BasicAction.incr, parts[1]));
                    case "decr" -> scopeCallables.add(new BasicCommand(BasicAction.decr, parts[1]));
                    case "while" -> scopeCallables.add(
                            new WhileLoop(
                                    new ComparisonCondition(parts[1], parts[3], convertToComparisonOperator(parts[2])),
                                    compileScope()
                            ));
                    case "if" -> {
                        Compiler.ifControl = null;
                        IfControl current = new IfControl(
                                new ComparisonCondition(parts[1], parts[3], convertToComparisonOperator(parts[2])),
                                compileScope());
                        scopeCallables.add(current);
                        if (ifControl != null) {
                            current.setElseCase(ifControl);
                        }
                    }
                    case "elseif" -> {
                        Compiler.ifControl = null;
                        IfControl current = new IfControl(
                                new ComparisonCondition(parts[1], parts[3], convertToComparisonOperator(parts[2])),
                                compileScope());
                        if (ifControl != null) {
                            current.setElseCase(ifControl);
                        }
                        ifControl = current;
                        currentLine--;
                    }
                    case "else" -> {
                        Compiler.ifControl = null;
                        ifControl = new IfControl(
                                new StaticCondition(true),
                                compileScope());
                        currentLine--;
                    }
                }
            }
        }
        currentLine++;

        return new Scope(scopeCallables.toArray(new Callable[0]));
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
     * Removes non code lines, comment line, empty lines, lines with only spaces.
     */
    private static void removeNonCodeLines(){
        ArrayList<String> newLines = new ArrayList<String>();
        for (String line: lines) {
            if (!line.equals(" ") && !line.isEmpty()){
                if (line.toCharArray()[0] != '#'){
                    newLines.add(line);
                }
            }
        }
        lines = newLines.toArray(new String[0]);
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

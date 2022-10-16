import java.awt.desktop.SystemEventListener;
import java.util.ArrayList;

public final class Compiler {

    private static int currentLine;
    private static String[] lines;

    public static Executable compile(String[] lines) throws Exception {
        Compiler.lines = lines;
        flattenLines();
        Compiler.currentLine = 0;
        return new Executable(compileScope());
    }

    private static Scope compileScope() throws Exception {
        ArrayList<Command> scopeCommands = new ArrayList<Command>();

        while (currentLine < lines.length && !lines[currentLine].equals("end")){
            String[] parts = lines[currentLine].split(" ");
            currentLine++;
            switch (parts[0]) {
                case "clear" -> scopeCommands.add(new BasicCommand(BasicAction.clear, parts[1]));
                case "incr" -> scopeCommands.add(new BasicCommand(BasicAction.incr, parts[1]));
                case "decr" -> scopeCommands.add(new BasicCommand(BasicAction.decr, parts[1]));
                case "while" -> scopeCommands.add(
                        new WhileLoop(
                                new Condition(parts[1], parts[3], convertToComparisonOperator(parts[2])),
                                compileScope()
                        ));
            }
        }
        currentLine++;

        return new Scope(scopeCommands.toArray(new Command[0]));
    }

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
}

import java.util.ArrayList;

public final class Compiler {

    private static int currentLine;
    private static String[] lines;

    public static Executable compile(String[] lines){
        //TODO


        Compiler.lines = lines;
        flattenLines();
        Compiler.currentLine = 0;

        //for (String line : Compiler.lines){
        //    System.out.println(line);
        //}
        new Executable(compileScope());
        return null;//
    }

    private static Scope compileScope(){
        ArrayList<Command> scopeCommands = new ArrayList<Command>();

        while (currentLine < lines.length && !lines[currentLine].equals("end")){
            String[] parts = lines[currentLine].split(" ");
            switch (parts[0]){
                case "clear":
                    scopeCommands.add(new BasicCommand(BasicAction.clear, parts[1]));
                    break;
                case "incr":
                    scopeCommands.add(new BasicCommand(BasicAction.incr, parts[1]));
                    break;
                case "decr":
                    scopeCommands.add(new BasicCommand(BasicAction.decr, parts[1]));
                    break;
                case "while":

            }
            currentLine++;
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
}

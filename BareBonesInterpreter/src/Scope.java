public class Scope extends Command {

    private final Command[] commands;
    public final Variables variables;

    public Scope(Command[] commands, Variables variables){
        this.commands = commands;
        this.variables = variables;
    }

    @Override
    public void execute() {
        for (Command command: commands) {
            command.execute();
        }
    }
}

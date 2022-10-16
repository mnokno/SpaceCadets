public class Scope extends Command {

    private final Command[] commands;

    public Scope(Command[] commands){
        this.commands = commands;
    }

    @Override
    public void execute() {
        for (Command command: commands) {
            command.execute();
        }
    }
}

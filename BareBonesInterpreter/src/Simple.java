public class Simple extends Command {

    private final String simpleCommand;
    private final Scope commandScope;

    public Simple(String simpleCommand, Scope commandScope){
        this.simpleCommand = simpleCommand;
        this.commandScope = commandScope;
    }

    @Override
    public void execute() {
        //TODO
    }
}

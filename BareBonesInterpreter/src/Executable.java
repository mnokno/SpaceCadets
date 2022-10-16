public class Executable {

    private final Scope mainScope;

    public Executable(Scope mainScope){
        this.mainScope = mainScope;
    }

    public void execute() throws Exception {
        mainScope.execute();
    }

    public String getLog(){
        return Variables.generateLog();
    }
}
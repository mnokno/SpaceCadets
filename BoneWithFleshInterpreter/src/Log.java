/**
 * Log class is a build in function that can be used to log a message to the console.
 */
public class Log extends Callable{

    private final String message;

    /**
     * Creates a new Log class that can be used to log a message to the console.
     * @param message Message that will be longed to the console can be a varibale or a string
     */
    public Log(String message){
        this.message = message;
    }

    /**
     * Logs the contents of this message to the console.
     * @throws Exception Throws an exception if the interpreters encounter invalid code.
     */
    @Override
    public void execute() throws Exception {
        if (message.contains("\"")){
            System.out.println(message.replaceAll("\"", ""));
        }
        else{
            System.out.println(Variables.getInt(message));
        }
    }
}

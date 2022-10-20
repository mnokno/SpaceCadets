import java.util.Scanner;

/**
 * Input is a build in function that can be used to read a value from the console to a specified varibale.
 */
public class Input extends Callable {

    private final String operand;

    /**
     * Creates a new Input callable that can be used to read a value from the
     * console to a specified variable.
     * @param operand Name of the variable where the result of the read will be saved.
     */
    public Input(String operand){
        this.operand = operand;
    }

    /**
     * Reads value from a console to a specified varibale.
     * @throws Exception Throws an exception if the interpreters encounter invalid code.
     */
    @Override
    public void execute() throws Exception {
        Scanner sc = new Scanner(System.in);
        String readValue = sc.nextLine();
        try{
            int res = Integer.parseInt(readValue);
            Variables.setInt(operand, res);
        } catch (Exception e){
            Variables.setInt(operand, 0);
        }
    }
}

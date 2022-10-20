import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {

        // reads code from file
        String fileName = "case7.bb";
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("data/" + fileName));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        // creates executable
        Executable executable = Compiler.compile(stringBuilder.toString());

        // executes the code
        executable.execute();
    }
}
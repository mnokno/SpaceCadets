import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {
    private static final String URL_BASE = "https://www.ecs.soton.ac.uk/people/";

    public static void main(String[] args) throws IOException {
        // reads email id, used to construct a full url
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter email id: ");
        String emailID = sc.nextLine();

        // constructs the full url, used to make URL object
        String urlString = URL_BASE + emailID;
        System.out.println(urlString);
        // creates the URL object, used to make the actual request
        URL url = new URL(urlString);

        // reads data from the website corresponding to the url
        URLConnection urlConnection = url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String readLine;
        String lineWithName = "";
        while ((readLine = bufferedReader.readLine()) != null)
        {
            // we only care about the line that contain a name
            if (readLine.contains("\"@type\": \"Person\"")){
                // we know that the next line will contain the name
                readLine = bufferedReader.readLine();
                readLine = readLine.replaceAll("\"", "").replaceAll(",", "");
                lineWithName = readLine;
                break;
            }

        }
        bufferedReader.close();

        // checks if a name was found, if yes then extracts the name
        if (!lineWithName.equals("")){
            System.out.println("Name:" + lineWithName.split(":")[1]);
        }
        else{
            System.out.println("The given email id does not line to a valid page! The page was most likely moved.");
        }
    }
}
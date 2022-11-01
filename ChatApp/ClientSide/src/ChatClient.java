import java.net.*;
import java.io.*;
import java.util.*;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isActive;
    private String username;
    private final List<String> localMessages = Collections.synchronizedList(new ArrayList<String>());

    public void setup() throws IOException {
        // creates scanner to read inout from console
        Scanner sc = new Scanner(System.in);

        // get username from the user
        System.out.println("Enter username:");
        username = sc.nextLine();

        // connects to the serve
        startConnection("127.0.0.1", 9999);
        (new ChatSync(this, localMessages)).start();

        // logs a message that this user joined the chat
        sendMessageVoid(username + " has joined the chat!");

        // main loop listing for user input
        String message;
        do{
            message = username + ": " + sc.nextLine();
            synchronized (localMessages){
                localMessages.add(message);
            }
            sendMessageVoid(message);
        }
        while (!Objects.equals(message, username + ": " + "."));

        // disconnects from the server
        stopConnection();
    }

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        isActive = true;
    }

    public String sendMessageAwaitResponse(String msg) throws IOException {
        out.println(msg);
        return in.readLine();
    }

    public void sendMessageVoid(String msg){
        out.println(msg);
    }

    public void stopConnection() throws IOException {
        isActive = false;
        in.close();
        out.close();
        clientSocket.close();
        System.out.println("CLOSED CONNECTION!");
    }

    public String getMessagesFromServer() throws IOException {
        return sendMessageAwaitResponse("@@@@@@messages@@@@@");
    }

    public boolean getIsActive(){
        return isActive;
    }
}

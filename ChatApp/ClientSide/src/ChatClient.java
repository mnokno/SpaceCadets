import java.net.*;
import java.io.*;
import java.util.*;

public class ChatClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isActive;
    private String username;
    private List<String> localMessages = Collections.synchronizedList(new ArrayList<String>());
    private final Object lock = new Object();

    public void setup() throws IOException {
        // creates scanner to read inout from console
        Scanner sc = new Scanner(System.in);

        // get username from the user
        System.out.println("Enter username:");
        username = sc.nextLine();

        // creates a client
        ChatClient chatClient = new ChatClient();
        // connects to the serve
        chatClient.startConnection("127.0.0.1", 9999);
        String message;
        do{
            message = username + ": " + sc.nextLine();
            synchronized (localMessages){
                System.out.println(localMessages.size());
                localMessages.add(message);
                System.out.println(localMessages.size());
            }
            chatClient.sendMessageVoid(message);
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
        (new ChatSync(this, localMessages)).start();
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
    }

    public String getMessagesFromServer() throws IOException {
        return sendMessageAwaitResponse("@@@@@@messages@@@@@");
    }

    public boolean getIsActive(){
        return isActive;
    }
}

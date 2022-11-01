import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ChatServer {
    private ServerSocket serverSocket;
    private final ArrayList<String> messages;

    public ChatServer(){
        this.messages = new ArrayList<String>();
    }

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            new ChatServerHandler(serverSocket.accept(), this).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("SERVER SIDE");
        ChatServer chatServer = new ChatServer();
        chatServer.start(9999);
    }

    public ArrayList<String> getMessages(){
        return messages;
    }

    public void addMessage(String message){
        messages.add(message);
    }
}

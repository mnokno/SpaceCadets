import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServerHandler extends Thread{

    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final ChatServer chatServer;


    public ChatServerHandler(Socket socket, ChatServer chatServer) {
        this.clientSocket = socket;
        this.chatServer = chatServer;
    }

    @Override
    public void run() {
        try{
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("@@@@@@messages@@@@@")){
                    //System.out.println("Server received request for messages");
                    out.println(String.join("¬", chatServer.getMessages()));
                }
                else{
                    System.out.println("Server received message " + inputLine);
                    chatServer.addMessage(inputLine);
                    if (".".equals(inputLine)) {
                        break;
                    }
                }
            }

            in.close();
            out.close();
            clientSocket.close();
            System.out.println("USER SESSION TERMINATED BY THE USER!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

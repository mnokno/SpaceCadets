// sourced from https://www.baeldung.com/a-guide-to-java-sockets

import java.net.*;
import java.io.*;

public class EchoMultiServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true){
            new EchoClientHandler(serverSocket.accept()).start();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }

    public static void main(String[] args) throws IOException {
        System.out.println("SERVER SIDE");
        EchoMultiServer echoMultiServer = new EchoMultiServer();
        echoMultiServer.start(5555);
    }
}

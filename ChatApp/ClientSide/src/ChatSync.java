import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatSync extends Thread {

    private final ChatClient chatClient;
    private final List<String> localMessages;

    public ChatSync(ChatClient chatClient, List<String> localMessages){
        this.chatClient = chatClient;
        this.localMessages = localMessages;
    }

    @Override
    public void run(){
        try {
            while (chatClient.getIsActive()){
                String messagesString = chatClient.getMessagesFromServer();
                if (!Objects.equals(messagesString, "")){
                    synchronized (localMessages){
                        String[] globalMessages = chatClient.getMessagesFromServer().split("¬");
                        if (globalMessages.length > localMessages.size()){
                            int sizeDiff = globalMessages.length - localMessages.size();
                            int localPos = localMessages.size();
                            for (int i = 0; i < sizeDiff; i++){
                                String newMessage = globalMessages[localPos + i];
                                System.out.println(newMessage);
                                localMessages.add(newMessage);
                            }
                            System.out.println(localMessages.size() + "T");
                        }
                    }
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}

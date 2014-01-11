package client;

public class GraphicalChatClientHandler extends ChatClientHandler {

    @Override
    public void print(String msg){
        if(GraphicalChatClient.connected){
            GraphicalChatClient.cf.textArea.append(msg + "\n");
        }
    }
    
}

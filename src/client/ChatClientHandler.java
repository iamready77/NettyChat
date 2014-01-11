package client;

import api.Handler;
import api.User;

public class ChatClientHandler extends Handler{

    @Override
    public void serverChatMessage(String user, String msg){
        print("<" + user + "> " + msg);
    }
    
    @Override
    public void userConnected(User user){
        print("Connected to server");
    }
    
    @Override
    public void userDisconnected(User user){
        print("Lost connection to server");
        System.exit(1);
    }
    
    public void print(String msg){
        System.out.println(msg);
    }
    
}

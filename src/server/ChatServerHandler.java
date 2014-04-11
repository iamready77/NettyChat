package server;

import java.util.ArrayList;
import java.util.List;

import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.group.DefaultChannelGroup;
import api.Handler;
import api.User;
import api.UserGroup;

public class ChatServerHandler extends Handler{

    static final List<User> users = new ArrayList<User>();
    
    @Override
    public void userConnected(User user){
        users.add(user);
        broadcastChatMessage("Server", user.getAddress() + " joined");
    }
    
    @Override
    public void userDisconnected(User user){
        if(users.contains(user)){
            String address = user.getAddress();
            users.remove(user);
            user.disconnect();
            broadcastChatMessage("Server", address + " left");
        }else{
            System.out.println("User not in users");
        }
    }
    
    @Override
    public void clientChatMessage(User user, String msg){
        broadcastChatMessage(user.getAddress(), msg);
    }
    
    
    public void broadcastChatMessage(String user, String msg){
        for(User u : users){
            u.writeChat(user, msg);
        }
    }
}

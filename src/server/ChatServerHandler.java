package server;

import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.channel.group.DefaultChannelGroup;
import api.Handler;
import api.User;
import api.UserGroup;

public class ChatServerHandler extends Handler{

    static final UserGroup users = new UserGroup(new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
    
    @Override
    public void userConnected(User user){
        users.add(user);
        broadcastChatMessage("Server", user.getAddress() + " joined");
    }
    
    @Override
    public void userDisconnected(User user){
        if(users.contains(user)){
           users.remove(user);
            broadcastChatMessage("Server", user.getAddress() + " left");
        }else{
            System.out.println("User not in users");
        }
    }
    
    @Override
    public void clientChatMessage(User user, String msg){
        broadcastChatMessage(user.getAddress(), msg);
    }
    
    
    public void broadcastChatMessage(String user, String msg){
        users.writeChatMessage(user, msg);
    }
}

package server;

import java.util.ArrayList;
import java.util.List;

import api.Handler;
import api.User;

public class ChatServerHandler extends Handler{

    static final List<User> users = new ArrayList<User>();
    
    @Override
    public void userConnected(User user){
        user.writeChat("Server", "Please enter a username:");
    }
    
    @Override
    public void userDisconnected(User user){
        if(users.contains(user)){
            users.remove(user);
            user.disconnect();
            broadcastMessage(user.getUsername() + " left");
        }else{
            System.out.println("User not in users");
        }
    }
    
    @Override
    public void clientChatMessage(User user, String msg){
        if(user.getUsername()==null){
            if(msg.contains(" ")){
                user.writeServerMessage("Usernames can't contain spaces!");
            }else if(msg == null || msg.length()==0 || msg.length()>11){
                user.writeServerMessage("Usernames must be less than 11 characters long!");
            }else{
                user.setUsername(msg);
                users.add(user);
                broadcastMessage(user.getUsername() + " joined");
            }
        }else{
            broadcastChatMessage(user, msg);
        }
    }
    
    
    public void broadcastChatMessage(User user, String msg){
        for(User u : users){
            u.writeChat(user, msg);
        }
    }
    
    public void broadcastMessage(String msg){
        for(User u : users){
            u.writeServerMessage(msg);
        }
    }
}

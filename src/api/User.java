package api;

import java.util.ArrayList;
import java.util.List;

import api.message.Message;
import api.message.ServerChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class User {

    private static final List<User> users;
    
    private final Channel channel;
    private String username = null;
    static{
        users = new ArrayList<User>();
    }
    
    public static User getInstance(ChannelHandlerContext ctx){
        return User.getInstance(ctx.channel());
    }
    
    public static User getInstance(Channel channel){
        User u = getUser(channel.remoteAddress().toString());
        if(u==null){
            u = new User(channel);
            users.add(u);
            return u;
        }else{
            return u;
        }
    }
    
    private User(Channel channel){
        this.channel = channel;
    }
    
    public Channel channel(){
        return channel;
    }
    
    public void writeMessagd(Message m){
        channel.writeAndFlush(m);
    }
    
    public void writeChat(User user, String msg){
        String name;
        if(user.getUsername()==null){
            name = user.getAddress().toString();
        }else{
            name = user.getUsername();
        }
        writeChat(name, msg);
    }
    
    public void writeChat(String user, String msg){
        channel.writeAndFlush(new ServerChatMessage(user, msg));
    }
    
    public void writeServerMessage(String msg){
        writeChat("Server", msg);
    }
    
    public String getAddress(){
        return channel.remoteAddress().toString();
    }
    
    public void disconnect(){
        users.remove(this);
        channel.close();
    }
    
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public static boolean userExists(String address){
        if(getUser(address)!=null){
            return true;
        }else{
            return false;
        }
    }
    
    public static User getUser(String address){
        for(int i = 0;i<users.size();i++){
            if(users.get(i).getAddress().equalsIgnoreCase(address)){
                return users.get(i);
            }
        }
        return null;
    }
    
    public static User getUser(Channel channel){
        return getUser(channel.remoteAddress().toString());
    }
    
    public static boolean usernameTaken(String username){
        for(int i = 0;i<users.size();i++){
            if(users.get(i).getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }
    
}

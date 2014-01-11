package api;

import api.message.Message;
import api.message.ServerChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class User {

    private final Channel channel;
    
    public User(ChannelHandlerContext ctx){
        this.channel= ctx.channel();
    }
    
    public User(Channel channel){
        this.channel = channel;
    }
    
    public Channel channel(){
        return channel;
    }
    
    public void writeMessagd(Message m){
        channel.writeAndFlush(m);
    }
    
    public void writeChat(String user, String msg){
        channel.writeAndFlush(new ServerChatMessage(user, msg));
    }
    
    public String getAddress(){
        return channel.remoteAddress().toString();
    }
    
}

package api;

import api.message.Message;
import api.message.ClientChatMessage;
import api.message.ServerChatMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Handler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg){
        if(msg instanceof Message){
            Message m = (Message) msg;
            if(m instanceof ClientChatMessage){
                ClientChatMessage n = (ClientChatMessage) m;
                clientChatMessage(User.getInstance(ctx), n.getMsg());
            }else if(m instanceof ServerChatMessage){
                ServerChatMessage n = (ServerChatMessage) m;
                serverChatMessage(n.getUser(), n.getMsg());
            }
        }else{
            System.err.println("Message received not instance of message!");
        }
    }
    public void clientChatMessage(User user, String msg){}
    public void serverChatMessage(String user, String msg){}
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        userConnected(User.getInstance(ctx));
    }
    public void userConnected(User user){}
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        userDisconnected(User.getInstance(ctx));
    }
    public void userDisconnected(User user){}
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    
}

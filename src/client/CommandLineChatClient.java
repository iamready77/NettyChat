package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import api.ObjectChannelInitializer;
import api.message.ClientChatMessage;

public class CommandLineChatClient {

    public static void main(String[] args) throws Exception{
        
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ObjectChannelInitializer(ChatClientHandler.class));
            ChannelFuture ch = b.connect(host, port).sync();
            if(ch.isSuccess()){
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                for(;;){
                    String line = in.readLine();
                    if(line == null){
                        break;
                    }
                    ch.channel().writeAndFlush(new ClientChatMessage(line));
                }
            }else{
                System.out.println("Couldn't connect to server");
            }
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    
}

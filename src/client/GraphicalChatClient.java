package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import api.ObjectChannelInitializer;
import api.message.ClientChatMessage;

public class GraphicalChatClient implements ActionListener{
    
    public static String host;
    public static int port;
    public static ChatFrame cf;
    private static Channel channel;
    public static boolean connected;
    public static GraphicalChatClient gc;

    public static void main(String[] args) throws InterruptedException{
        if(args.length == 2){
            host = args[0];
            port = Integer.parseInt(args[1]);
        }else if(args.length == 1){
            host = args[0];
            port = 8443;
        }else{
            host = "70.94.38.149";
            port = 8443;
        }
        cf = new ChatFrame();
        gc = new GraphicalChatClient();
        gc.connect();
    }
    
    public void connect() throws InterruptedException{
        final EventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup);
        b.channel(NioSocketChannel.class);
        b.option(ChannelOption.SO_KEEPALIVE, true);
        b.handler(new ObjectChannelInitializer(GraphicalChatClientHandler.class));
        final ChannelFuture ch = b.connect(host, port);
        ch.addListener(new ChannelFutureListener(){
            @Override
            public void operationComplete(ChannelFuture f) throws Exception {
                if(f.isSuccess()){
                    cf.textArea.setText(null);
                    cf.entry.addActionListener(gc);
                    channel = ch.channel();
                    connected = true;
                }else{
                    cf.textArea.setText("Couldn't connect to the server");
                    connected = false;
                    workerGroup.shutdownGracefully();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(connected){
            String text = cf.entry.getText();
            if(!text.equalsIgnoreCase("") && text != null){
                channel.writeAndFlush(new ClientChatMessage(text));
            }
            cf.entry.setText(null);
        }
        
    }

}

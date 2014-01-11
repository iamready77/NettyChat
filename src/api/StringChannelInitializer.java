package api;

import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class StringChannelInitializer extends ChannelInitializer<SocketChannel>{

    private Class<? extends ChannelInboundHandlerAdapter> handler;
    
    public StringChannelInitializer(Class<? extends ChannelInboundHandlerAdapter> handler){
        super();
        this.handler = handler;
    }
    
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        ch.pipeline().addLast("decoder", new StringDecoder());
        ch.pipeline().addLast("encoder", new StringEncoder());
        ch.pipeline().addLast(handler.newInstance());
    }

}

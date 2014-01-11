package api;

import api.message.Message;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ObjectChannelInitializer extends ChannelInitializer<SocketChannel>{

    private Class<? extends Handler> handler;
    
    public ObjectChannelInitializer(Class<? extends Handler> handler){
        super();
        this.handler = handler;
    }
    
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("encoder", new ObjectEncoder());
        ch.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers.softCachingResolver(Message.class.getClassLoader())));
        ch.pipeline().addLast(handler.newInstance());
    }

}

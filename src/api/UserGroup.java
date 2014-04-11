package api;

import api.message.Message;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

public class UserGroup{

    private ChannelGroup group;
    
    public UserGroup(ChannelGroup group) {
        this.group = group;
    }
    
    public boolean contains(User user){
        if(group.contains(user.channel())){
            return true;
        }else{
            return false;
        }
    }
    
    public void add(User user){
        group.add(user.channel());
    }
    
    public void remove(User user){
        group.remove(user.channel());
    }
    
    public void write(Message msg){
        group.write(msg);
    }
    
    public void writeChatMessage(String user, String msg){
        for(Channel c: group){
            User u = User.getInstance(c);
            u.writeChat(user, msg);
        }
    }

}

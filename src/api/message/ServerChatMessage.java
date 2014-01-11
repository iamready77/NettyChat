package api.message;

public class ServerChatMessage extends Message{

    private static final long serialVersionUID = 1L;
    private final String user;
    private final String msg;

    public ServerChatMessage(String user, String msg) {
        super();
        this.user = user;
        this.msg = msg;
    }
    
    public String getUser(){
        return user;
    }
    
    public String getMsg(){
        return msg;
    }

}

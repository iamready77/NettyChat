package api.message;

public class ClientChatMessage extends Message{

    private static final long serialVersionUID = 1L;
    private final String msg;
    
    public ClientChatMessage(String msg) {
        super();
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }
    
}

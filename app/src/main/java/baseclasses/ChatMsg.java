package baseclasses;

/**
 * Created by Guston on 05/03/18.
 */

public class ChatMsg {
    public static final int TYPE_RECEVIED = 0;
    public  static final int TYPE_SEND = 1;
    private  String content;
    private int type;

    public ChatMsg(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}

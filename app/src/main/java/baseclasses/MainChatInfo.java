package baseclasses;

/**
 * Created by Guston on 05/03/18.
 */

public class MainChatInfo {

    private String Friend_name;
    private int Friend_Img;

    public MainChatInfo(String friend_name, int friend_Img) {
        Friend_name = friend_name;
        Friend_Img = friend_Img;
    }


    public String getFriend_name() {
        return Friend_name;
    }

    public int getFriend_Img() {
        return Friend_Img;
    }
}

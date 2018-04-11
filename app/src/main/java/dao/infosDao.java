package dao;

import java.util.ArrayList;
import java.util.List;

import baseclasses.MainChatInfo;

/**
 * Created by Guston on 06/03/18.
 */

public class infosDao {

    public static List<MainChatInfo> infoList = new ArrayList<MainChatInfo>();

    public static  List<MainChatInfo> getTheList()
    {



        if (infoList == null){

            infoList = new ArrayList<MainChatInfo>();
            return infoList;
        }

        return infoList;

    }





}

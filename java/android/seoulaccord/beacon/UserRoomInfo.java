package android.seoulaccord.beacon;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kyi42 on 2017-05-25.
 */

public class UserRoomInfo implements Serializable{
    final int USER_MAX_ROOM = 10;
    String check_in;
    String check_out;
    ArrayList<String> room_num_arr;
    int user_room_cnt;
    UserRoomInfo(String check_in, String check_out, int user_room_cnt)
    {
        room_num_arr =  new ArrayList<String>();
        this.check_in = check_in;
        this.check_out = check_out;
        this.user_room_cnt = user_room_cnt;
    }

    public String getCheck_in() {
        return check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public ArrayList<String> getRoom_num_arr() {
        return room_num_arr;
    }

    public int getUser_room_num() {
        return user_room_cnt;
    }
}

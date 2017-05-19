package android.seoulaccord.beacon;

/**
 * Created by seowo on 2017-05-19.
 */

//DB와 통신하여 정보를 받아온 클래스라고 가정
public class TempDB {

    String user_id;
    String user_password;
    RoomBeacon room;

    boolean take_room;
    boolean check_in;
    int engage_days;

    String engage_date;
    String return_date;
}

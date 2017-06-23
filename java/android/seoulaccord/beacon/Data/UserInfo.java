package android.seoulaccord.beacon.Data;

/**
 * Created by seowok on 2017-05-19.
 */

public class UserInfo {
    public static String user_id;
    public static String room_number;
    public static RoomBeacon room = null;
    public static boolean door_lock = false;

    public static String check_in = null;
    public static String check_out = null;


    public static void setUserRoom(String room_number, String room_uuid, int major, int minor){
        room = new RoomBeacon(room_number, room_uuid, major, minor);
    }
}

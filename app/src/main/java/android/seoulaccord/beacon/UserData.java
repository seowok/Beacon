package android.seoulaccord.beacon;

/**
 * Created by seowok on 2017-05-10.
 */

public class UserData {
    private String user_ID;
    private String user_PW;
    private String user_room_code;
    private boolean user_in_region;

    UserData(String user_ID, String user_PW, String user_room_code){
        this.user_ID = user_ID;
        this.user_PW = user_PW;
        this.user_room_code = user_room_code;
        this.user_in_region = false;
    }

    public void setUserInRegion(boolean user_in_region){
        this.user_in_region = user_in_region;
    }

    public String[] getUserIdentity(){
        String[] user_data = new String[2];
        user_data[0] = user_ID;
        user_data[1] = user_PW;
    return user_data;
    }

    public String getUserRoomNumber(){
        return user_room_code;
    }

    public boolean getUserInRegion(){
        return user_in_region;
    }
}

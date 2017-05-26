package android.seoulaccord.beacon;

import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by seowok on 2017-05-19.
 */

public class UserData {
    private String user_id;
    private String user_password;
    private RoomBeacon room = null;

    private boolean take_room = false;
    private boolean check_in = false;
    private int engage_days = 0;

    private SimpleDateFormat yyyy_MM_dd_format;
    private String engage_date = null;
    private String return_date = null;

    //DB로부터 user data를 받아 생성
    /*public UserData(TempDB user_data) {
        user_id = user_data.user_id;
        user_password = user_data.user_password;

        if(user_data.take_room == true){
            room = user_data.room;
            take_room = true;
            check_in = false;
            engage_days = user_data.engage_days;
            engage_date = user_data.engage_date;
            return_date = user_data.return_date;
        }
    }*/

    /**test용
    * 생성자
    * */
    public UserData(){
        user_id = "test";
        user_password = "test";

        room = new RoomBeacon("777", UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0")
                , 40001
                , 10622);
        take_room = true;
        check_in = false;
    }

    //이 함수를 호출할 때 parameter를 Button, spinner등을 이용하여 exception이 발생하지 않게 한다
    //User가 DB를 업데이트 할 때 동시에 이 함수를 호출하여 UserData도 업데이트
    //방을 빌릴때 호출
    public boolean engageRoom(RoomBeacon room, int engage_days){
        if(take_room == false) {
            this.room = room;
            this.engage_days = engage_days;
            int take_time_millis = engage_days * (1000 * 60 * 60 * 24);

            yyyy_MM_dd_format = new SimpleDateFormat("yyyy-MM-dd");
            engage_date = yyyy_MM_dd_format.format(new Date(System.currentTimeMillis()));
            return_date = yyyy_MM_dd_format.format(new Date(System.currentTimeMillis() + take_time_millis));
        }
        else{
            return false;
        }
        return true;
    }

    public boolean isTakeRoom(){
        return take_room;
    }

    public RoomBeacon getRoomBeacon(){
        return room;
    }
}

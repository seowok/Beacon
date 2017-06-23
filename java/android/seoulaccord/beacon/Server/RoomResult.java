package android.seoulaccord.beacon.Server;

import java.util.UUID;

/**
 * Created by seowo on 2017-06-10.
 */

public class RoomResult {
    public RoomData result;
    public String message;

    public class RoomData{
        public String room_number;
        public String room_uuid;
        public int major;
       public int minor;
    }
}

package android.seoulaccord.beacon;

import java.util.UUID;

/**
 * Created by seowok on 2017-05-19.
 */

public class RoomBeacon {
    public final String room_number;
    private final UUID room_uuid;
    private final int major;
    private final int minor;

    public RoomBeacon(String room_number, UUID room_uuid, int major, int minor) {
        this.room_number = room_number;
        this.room_uuid = room_uuid;
        this.major = major;
        this.minor = minor;
    }

    public UUID getUUID() {
        return room_uuid;
    }

    public int getMajor(){
        return major;
    }

    public int getMinor(){
        return minor;
    }
}

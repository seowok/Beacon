package android.seoulaccord.beacon.Data;

import java.util.UUID;

/**
 * Created by seowok on 2017-05-19.
 */

/**
 * 각 호실의 비콘 정보를 담는 클래스
 */
public class RoomBeacon {
    public String room_number;
    private UUID room_uuid;
    private int major;
    private int minor;

    //Database로 부터 얻어온 정보로 객체 생성
    public RoomBeacon(String room_number, String room_uuid, int major, int minor) {
        this.room_number = room_number;
        this.room_uuid = UUID.fromString(room_uuid);
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

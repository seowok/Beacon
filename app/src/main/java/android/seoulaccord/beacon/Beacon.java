package android.seoulaccord.beacon;

import java.util.UUID;

/**
 * Created by seowok on 2017-05-10.
 */

public final class Beacon {
    private final String mAddress;
    UUID uuid;
    int major;
    int minor;
    int rssi;
    float accuracy;
    float previousAccuracy;

    public enum Proximity {
        UNKNOWN,
        IMMEDIATE,
        NEAR,
        FAR;

        Proximity() {
        }
    }

    Beacon(String address){
        this.mAddress = address;
        this.accuracy = -1.0F;
        this.previousAccuracy = -1.0F;
    }

    public String getDeviceAddress() {
        return this.mAddress;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public int getMajor() {
        return this.major;
    }

    public int getMinor() {
        return this.minor;
    }

    public int getRssi() {
        return this.rssi;
    }

    public Proximity getProximity() {
        return this.calculateProximity(this.accuracy);
    }

    public Proximity getPreviousProximity() {
        return this.calculateProximity(this.previousAccuracy);
    }

    public float getAccuracy() {
        return this.accuracy;
    }

    public String toString() {
        return this.uuid + " major: " + this.major + " minor: " + this.minor + " | proximity: " + this.getProximity() + " accuracy: " + this.accuracy + "m";
    }

    private Proximity calculateProximity(float accuracy) {
        return accuracy == -1.0F?Proximity.UNKNOWN:((double)accuracy <= 0.26D?Proximity.IMMEDIATE:((double)accuracy <= 2.0D?Proximity.NEAR:Proximity.FAR));
    }
}

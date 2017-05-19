package android.seoulaccord.beacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

/**
 * Created by seowok on 2017-05-18.
 */

public class BeaconConnection extends Application {

    private BeaconManager beacon_manager;
    private UserData user;
    private RoomBeacon room;

    @Override
    public void onCreate() {
        super.onCreate();
        //DB로부터 사용자 정보 받아옴
        //user = new UserData(temp_db);

        if(user.isTakeRoom()) {
            room = user.getRoomBeacon();
            beacon_manager = new BeaconManager(getApplicationContext());

            beacon_manager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                @Override
                public void onEnteredRegion(Region region, List<Beacon> list) {
                    showNotification(room.room_number, "Check In");
                }

                @Override
                public void onExitedRegion(Region region) {
                    showNotification(room.room_number, "Check Out");
                }
            });

            beacon_manager.connect(new BeaconManager.ServiceReadyCallback() {
                //E2C56DB5-DFFB-48D2-B060-D0F5A71096E0, 40001, 10622
                @Override
                public void onServiceReady() {
                    beacon_manager.startMonitoring(new Region(room.room_number,
                            room.getUUID(),
                            room.getMajor(), room.getMinor()));

                }
            });
        }
    }

    public void showNotification(String title, String message){
        Intent notify_intent = new Intent(this, MainActivity.class);
        notify_intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending_intent = PendingIntent.getActivities(this, 0,
                                        new Intent[] {notify_intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pending_intent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notification_manager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification_manager.notify(1, notification);
    }

    /*private boolean isAlreadyRunActivity(){
        final String package_name = "android.seoulaccord.beacon";
        ActivityManager activity_manager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> process_list = activity_manager.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo process_info : process_list){
            if(process_info.processName.equals(package_name)) {
                if (process_info.importance == process_info.IMPORTANCE_FOREGROUND)
                    return true;
            }
        }
        return false;
    }*/
}

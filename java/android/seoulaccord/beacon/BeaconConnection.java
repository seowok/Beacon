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
import java.util.UUID;

/**
 * Created by seowok on 2017-05-18.
 */

public class BeaconConnection extends Application {

    private BeaconManager beacon_manager;

    @Override
    public void onCreate() {
        super.onCreate();

        beacon_manager = new BeaconManager(getApplicationContext());

        beacon_manager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification("514", "Check In");
            }

            @Override
            public void onExitedRegion(Region region) {
                showNotification("514", "Check Out");
            }
        });

        beacon_manager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beacon_manager.startMonitoring(new Region("514",
                                                UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"),
                                                40001, 10622));

            }
        });
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
}

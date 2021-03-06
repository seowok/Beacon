package android.seoulaccord.beacon.Beacon;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.seoulaccord.beacon.Data.RoomBeacon;
import android.seoulaccord.beacon.R;
import android.seoulaccord.beacon.SmartKeyActivity;
import android.seoulaccord.beacon.Data.UserInfo;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;
import java.util.UUID;

/**
 * Created by seowok on 2017-05-18.
 */

/**
 * 비콘의 스캔, 연결
 * 연결, 연결 해제시 알림
 */
public class BeaconConnection extends Application {

    private BeaconManager beacon_manager;
    private RoomBeacon room;
    public static boolean is_check_in = false;

    @Override
    public void onCreate() {
        super.onCreate();

        //user가 빌린 방 비콘 스캔
            room = UserInfo.room;    //user 데이터로부터 방의 비콘 정보 받아옴
            beacon_manager = new BeaconManager(getApplicationContext());

            //비콘 연결을 서비스로 동작하게 함
            beacon_manager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    beacon_manager.startMonitoring(new Region(room.room_number,
                            room.getUUID(),
                            room.getMajor(), room.getMinor()));
                }

                /**
                 * 임시 테스트용
                 */
                /*@Override
                public void onServiceReady() {
                    beacon_manager.startMonitoring(new Region("test"
                            , UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0")
                            , 40001
                            , 10622));
                }*/
            });

            //비콘 범위에 들어오고 나갈 때 알림을 뜨게 함
            beacon_manager.setMonitoringListener(new BeaconManager.MonitoringListener() {
                @Override
                public void onEnteredRegion(Region region, List<Beacon> list) {
                    if(!is_check_in) {
                        showNotification(room.room_number, "Check In");
                        is_check_in = true;
                        SmartKeyActivity.beacon_check.setImageResource(R.drawable.bar_in);
                        SmartKeyActivity.beacon_check.invalidate();

                        //방 안에 있을 때 가능한 서비스
                        RoomServiceFragment.roomservice_btn.setClickable(true);
                        RoomServiceFragment.light_btn.setClickable(true);
                        RoomServiceFragment.morning_call_btn.setClickable(true);

                        RoomServiceFragment.roomservice_btn.invalidate();
                        RoomServiceFragment.light_btn.invalidate();
                        RoomServiceFragment.morning_call_btn.invalidate();

                        //밖에 있을 때 가능한 서비스
                        RoomServiceFragment.cleaning_btn.setClickable(false);
                        RoomServiceFragment.replacement_btn.setClickable(false);

                        RoomServiceFragment.cleaning_btn.invalidate();
                        RoomServiceFragment.replacement_btn.invalidate();
                    }
                }

                @Override
                public void onExitedRegion(Region region) {
                    if(is_check_in) {
                        showNotification(room.room_number, "Check Out");
                        is_check_in = false;
                        SmartKeyActivity.beacon_check.setImageResource(R.drawable.bar_out);
                        SmartKeyActivity.beacon_check.invalidate();

                        //방 안에 있을 때 가능한 서비스
                        RoomServiceFragment.roomservice_btn.setClickable(false);
                        RoomServiceFragment.light_btn.setClickable(false);
                        RoomServiceFragment.morning_call_btn.setClickable(false);

                        RoomServiceFragment.roomservice_btn.invalidate();
                        RoomServiceFragment.light_btn.invalidate();
                        RoomServiceFragment.morning_call_btn.invalidate();

                        //밖에 있을 때 가능한 서비스
                        RoomServiceFragment.cleaning_btn.setClickable(true);
                        RoomServiceFragment.replacement_btn.setClickable(true);

                        RoomServiceFragment.cleaning_btn.invalidate();
                        RoomServiceFragment.replacement_btn.invalidate();
                    }
                }
            });
    }

    //알림을 뜨게 함
    public void showNotification(String title, String message) {
        Intent notify_intent = new Intent(this, SmartKeyActivity.class);
        notify_intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending_intent = PendingIntent.getActivities(this, 0,
                new Intent[]{notify_intent}, PendingIntent.FLAG_UPDATE_CURRENT);
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

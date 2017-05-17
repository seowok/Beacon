package android.seoulaccord.beacon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import java.util.List;

/**
 * Created by seowok on 2017-05-10.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BeaconConnection extends MainActivity{
    public static final int REQUEST_ENABLE_BLUETOOTH = 1;
    private BeaconManager beacon_manager;
    private Beacon user_beacon;

    public BeaconConnection(BeaconManager beacon_manager, Beacon user_beacon) {
        this.beacon_manager = beacon_manager;
        this.user_beacon = user_beacon;
    }

    //Connect 하기 전 연결을 준비
    public void ready(){
        if(!ensureBleExists())
            finish();

        if(!isBleEnabled())
            enableBle();
    }

    //사용자가 예약한 방의 비콘과 연결
    public void connect(){
        //application을 종료해도 service가 계속 실행된다
        beacon_manager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beacon_manager.startMonitoring(new Region("user_room_code", user_beacon.getUuid(), user_beacon.major, user_beacon.minor));
            }
        });

        //android 단말이 beacon의 송신범위에 들어가거나, 나왔을 때 작동
        beacon_manager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<com.estimote.sdk.Beacon> list) {
                showNotification(user_beacon.getMinor(), "Check In");
            }

            @Override
            public void onExitedRegion(Region region) {
                showNotification(user_beacon.getMinor(), "Check In");
            }
        });
    }

    //장치가 블루투스를 지원하면 true 리턴, 지원하지 않으면 토스트발생, false 리턴
    private boolean ensureBleExists(){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 장치입니다", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    //Checks whether the Bluetooth adapter is enabled.
    private boolean isBleEnabled(){
        final BluetoothManager bluetooth_manager = (BluetoothManager)getSystemService(BLUETOOTH_SERVICE);
        final BluetoothAdapter bluetooth_adapter = bluetooth_manager.getAdapter();
        return (bluetooth_adapter != null && bluetooth_adapter.isEnabled());
    }

    //Tries to start Bluetooth adapter
    private void enableBle(){
        final Intent enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enable_intent, REQUEST_ENABLE_BLUETOOTH);
    }

    //연결이 연결되거나 끊어졌을 때 알림
    private void showNotification(int room_number, String message){
        Intent notify_intent = new Intent(getApplicationContext(), MainActivity.class);
        notify_intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending_intent = PendingIntent.getActivities(getApplicationContext(), 0, new Intent[] {notify_intent}
                                                                    , PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(getApplicationContext())
                                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                                    .setContentTitle(message)
                                    .setAutoCancel(true)
                                    .setContentIntent(pending_intent)
                                    .build();
        notification.defaults |= Notification.DEFAULT_SOUND;

        NotificationManager notification_manager = (NotificationManager)getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notification_manager.notify("ROOM", room_number, notification);
    }
}

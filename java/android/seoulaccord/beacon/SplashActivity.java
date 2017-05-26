package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

public class SplashActivity extends Activity {
    private BeaconManager beaconManager;
    private Region region;
    private boolean isConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(intent);
                finish();
            }
        }, 2000);

//        beaconManager = new BeaconManager(this);
//        isConnected = false;
//        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
//            @Override
//            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
//                if (!list.isEmpty()) {
//                    Beacon nearestBeacon = list.get(0);
//                    Log.d("Beacon", "Nearest places: " + nearestBeacon.getRssi());
//                    if( !isConnected && nearestBeacon.getRssi() > -70) {
//                        isConnected = true;
////                        AlertDialog.Builder dialog = new AlertDialog.Builder(SplashActivity.this);
////                        dialog.setTitle("알림") .setMessage("비콘이 연결되었습니다.")
////                                .setPositiveButton("확인", new DialogInterface.OnClickListener()
////                                {
////                                    @Override
////                                    public void onClick(DialogInterface dialog, int which) { }
////                                }) .create().show();
//                    }
//                    else
//                    {
//                        Toast.makeText(SplashActivity.this, "연결이 끊어졌습니다.", Toast.LENGTH_SHORT).show();
//                        isConnected = false;
//                    }
//                }
//            }
//        });
//        region = new Region("ranged region", UUID.fromString("E2C56DB5-DFFB-48D2-B060-D0F5A71096E0"),40001,10610);
    }
    @Override
    protected void onResume() {
        super.onResume();
//        SystemRequirementsChecker.checkWithDefaultDialogs(this);
//        beaconManager.connect(new BeaconManager.ServiceReadyCallback(){
//            @Override
//            public void onServiceReady() {
//                beaconManager.startRanging(region);
//            }
//        });
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
}

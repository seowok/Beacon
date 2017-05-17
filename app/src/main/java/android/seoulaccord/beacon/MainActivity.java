package android.seoulaccord.beacon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.estimote.sdk.BeaconManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BeaconManager bc_mng = new BeaconManager(getApplicationContext());

        BeaconConnection bc_con = new BeaconConnection(bc_mng);
    }
}

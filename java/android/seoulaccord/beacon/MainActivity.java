package android.seoulaccord.beacon;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsHelper;

public class MainActivity extends AppCompatActivity {
    SystemRequirementsHelper requirements_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();

        requirements_helper = new SystemRequirementsHelper();

        if(!requirements_helper.isBluetoothLeAvailable(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 장치입니다", Toast.LENGTH_LONG).show();
            finish();
        }

        if(!requirements_helper.isBluetoothEnabled(getApplicationContext())){
            Intent enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enable_intent);
        }
    }
}

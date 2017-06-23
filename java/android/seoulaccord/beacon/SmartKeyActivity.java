package android.seoulaccord.beacon;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.seoulaccord.beacon.Beacon.BeaconConnection;
import android.seoulaccord.beacon.Beacon.RoomServiceFragment;
import android.seoulaccord.beacon.Beacon.SmartKeyFragment;
import android.seoulaccord.beacon.Data.UserRoomInfo;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsHelper;

public class SmartKeyActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    SystemRequirementsHelper requirements_helper;
    final int REQUEST_BLE = 77;

    public static FloatingActionButton beacon_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_key);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        beacon_check = (FloatingActionButton) findViewById(R.id.beacon_check);
        beacon_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!BeaconConnection.is_check_in)
                Snackbar.make(view, "방과 거리가 멀어 서비스가 제한됩니다", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        requirements_helper = new SystemRequirementsHelper();

        //블루투스를 지원하는 장치인지 확인하고 지원하지 않으면 activity 종료
        if(!requirements_helper.isBluetoothLeAvailable(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "블루투스를 지원하지 않는 장치입니다", Toast.LENGTH_LONG).show();
            finish();
        }

        //블루투스 사용을 위해 권한 획득 요청
        if(!requirements_helper.isBluetoothEnabled(getApplicationContext())){
            Intent enable_intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enable_intent, REQUEST_BLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //블루투스 사용 권한 요청에 승인하지 않으면 Toast 메세지를 띄우고 activity 종료
        if(requestCode == REQUEST_BLE){
            if(resultCode != Activity.RESULT_OK){
                Toast.makeText(getApplicationContext(), "블루투스가 필요한 서비스 입니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_smart_key, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            Fragment fragment;
            Bundle bundle = new Bundle();

            switch (position){
                case 0 :
                    bundle.putBoolean("Beacon", BeaconConnection.is_check_in);
                    bundle.putBoolean("SmartKey", UserRoomInfo.lock);
                    fragment = new SmartKeyFragment();
                    fragment.setArguments(bundle);
                    return fragment;
                case 1 :
                    bundle.putBoolean("Beacon", BeaconConnection.is_check_in);
                    bundle.putBoolean("RoomService", UserRoomInfo.room_service);
                    bundle.putBoolean("LightOnOff", UserRoomInfo.light);
                    bundle.putBoolean("MorningCall", UserRoomInfo.morning_call);
                    bundle.putBoolean("Lobby", UserRoomInfo.lobby);
                    bundle.putBoolean("Cleaning", UserRoomInfo.cleaning);
                    bundle.putBoolean("Replacement", UserRoomInfo.replacement);
                    fragment = new RoomServiceFragment();
                    fragment.setArguments(bundle);
                    return fragment;
                default: return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}

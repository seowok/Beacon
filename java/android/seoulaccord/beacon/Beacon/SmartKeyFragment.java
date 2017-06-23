package android.seoulaccord.beacon.Beacon;

import android.os.Bundle;
import android.seoulaccord.beacon.Data.UserInfo;
import android.seoulaccord.beacon.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.cloud.internal.User;

/**
 * Created by seowo on 2017-06-22.
 */

public class SmartKeyFragment extends Fragment {
    ImageView smart_key;
    ImageButton smart_key_btn;
    TextView room_num;
    TextView user_name;
    TextView check_in, check_out;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root_view = inflater.inflate(R.layout.fragment_smart_key, container, false);
        smart_key = (ImageView) root_view.findViewById(R.id.smart_key);
        smart_key_btn = (ImageButton)root_view.findViewById(R.id.smart_key_btn);
        room_num = (TextView)root_view.findViewById(R.id.room_num);
        user_name = (TextView)root_view.findViewById(R.id.user_name);
        check_in = (TextView)root_view.findViewById(R.id.check_in_date);
        check_out = (TextView)root_view.findViewById(R.id.check_out_btn);

        if(getArguments() != null){
            if(getArguments().getBoolean("Beacon")){
                smart_key.setImageResource(R.drawable.card_background);
                if(UserInfo.door_lock) smart_key_btn.setImageResource(R.drawable.lock_open);
                else smart_key_btn.setImageResource(R.drawable.lock_close);
            }
            else{
                smart_key.setImageResource(R.drawable.card_notactivated);
                if(UserInfo.door_lock) smart_key_btn.setImageResource(R.drawable.lock_open_tap);
                else smart_key_btn.setImageResource(R.drawable.lock_close_tap);
            }
        }

        smart_key_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfo.door_lock = !UserInfo.door_lock;
                root_view.invalidate();
            }
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

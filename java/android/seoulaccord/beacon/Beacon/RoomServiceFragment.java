package android.seoulaccord.beacon.Beacon;

import android.os.Bundle;
import android.seoulaccord.beacon.Data.UserRoomInfo;
import android.seoulaccord.beacon.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by seowo on 2017-06-22.
 */

public class RoomServiceFragment extends Fragment {
    public static Button roomservice_btn;
    public static Button light_btn;
    public static Button morning_call_btn;
    public static Button lobby_btn;
    public static Button cleaning_btn;
    public static Button replacement_btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root_view = inflater.inflate(R.layout.fragment_room_service, container, false);
        if(getArguments() != null){
            roomservice_btn = (Button)root_view.findViewById(R.id.frag_roomservice_btn);
            light_btn = (Button)root_view.findViewById(R.id.frag_light_btn);
            morning_call_btn = (Button)root_view.findViewById(R.id.frag_morningcall_btn);
            lobby_btn = (Button)root_view.findViewById(R.id.frag_lobby_btn);
            cleaning_btn = (Button)root_view.findViewById(R.id.frag_cleaning_btn);
            replacement_btn = (Button)root_view.findViewById(R.id.frag_replacement_btn);

            roomservice_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    roomservice_btn.setBackgroundResource(R.drawable.roomservice_yellow);
                    UserRoomInfo.room_service = true;
                }
            });
            light_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!UserRoomInfo.light) {
                        light_btn.setBackgroundResource(R.drawable.lightonoff_yellow);
                        UserRoomInfo.light = true;
                    }
                }
            });
            morning_call_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!UserRoomInfo.morning_call) {
                        morning_call_btn.setBackgroundResource(R.drawable.morningcall_yellow);
                        UserRoomInfo.morning_call = true;
                    }
                }
            });
            lobby_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!UserRoomInfo.lobby) {
                        lobby_btn.setBackgroundResource(R.drawable.lobby_yellow);
                        UserRoomInfo.lobby = true;
                    }
                }
            });
            cleaning_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!UserRoomInfo.cleaning) {
                        cleaning_btn.setBackgroundResource(R.drawable.cleaning_yellow);
                        UserRoomInfo.cleaning = true;
                    }
                }
            });
            replacement_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!UserRoomInfo.replacement) {
                        replacement_btn.setBackgroundResource(R.drawable.replacement_yellow);
                        UserRoomInfo.replacement = true;
                    }
                }
            });

        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

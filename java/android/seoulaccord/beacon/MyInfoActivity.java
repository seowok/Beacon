package android.seoulaccord.beacon;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyInfoActivity extends Activity {

    boolean room_service_request = false;
    Button reserve_btn;
    Button room_service_btn;
    Button open_room_btn;
    Button logout_btn;

    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        reserve_btn = (Button)findViewById(R.id.reserve_btn);
        room_service_btn = (Button)findViewById(R.id.service_btn);
        open_room_btn = (Button)findViewById(R.id.open_btn);
        logout_btn = (Button)findViewById(R.id.logout_btn);

        SharedPreferences sf = getSharedPreferences("reserve_data", MODE_PRIVATE);
        if(!sf.getString("hotel", "").equals("")) {
            reserve_btn.setEnabled(false);
            Toast.makeText(getApplicationContext(), "자동 예약 로그인", Toast.LENGTH_SHORT).show();
        }

        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, ReserveInfoActivity.class);
                startActivity(intent);
            }
        });
        room_service_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "룸서비스 요청", Toast.LENGTH_SHORT).show();
                room_service_request = true;
            }
        });
        open_room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, SmartKeyActivity.class);
                startActivity(intent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Intent intent = getIntent();
            if((UserRoomInfo)intent.getSerializableExtra("room_info") != null) {
                UserRoomInfo roominfo = (UserRoomInfo) intent.getSerializableExtra("room_info");
                for(int i = 0; i < roominfo.getUser_room_num(); i ++)
                    Toast.makeText(getApplicationContext(), "확인" + roominfo.getRoom_num_arr().get(i), Toast.LENGTH_SHORT).show();
            }
            /*if (!(intent.getExtras().get("check_in").equals("YYYY_MM_DD")))
            {
                reserve_btn.setText("예약완료");
                sf = getSharedPreferences("reserve_data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sf.edit();
                editor.commit(); // 파일에 최종 반영함
            }*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                logout();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void logout()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyInfoActivity.this);
        alertDialogBuilder.setMessage("로그아웃 하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sf;
                                sf = getSharedPreferences("reserve_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sf.edit();
                                editor1.clear();
                                editor1.commit();

                                sf = getSharedPreferences("login_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor2 = sf.edit();
                                editor2.clear();
                                editor2.commit();
                                Intent intent = new Intent(MyInfoActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//액티비티 스택 비우기
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

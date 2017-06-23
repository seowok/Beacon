package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.seoulaccord.beacon.Data.UserRoomInfo;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyInfoActivity extends Activity {

    boolean room_service_request = false;
    Button reserve_btn;
    Button info_btn;
    Button smartkey_btn;
    Button account_btn;
    UserRoomInfo roomInfo;

    SharedPreferences sf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        reserve_btn = (Button)findViewById(R.id.reserve_btn);
        info_btn = (Button)findViewById(R.id.info_btn);
        smartkey_btn = (Button)findViewById(R.id.smartkey_btn);
        account_btn = (Button)findViewById(R.id.account_btn);

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
        info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        smartkey_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            Intent intent = getIntent();
            //!!서버 통신 필요
            //roomInfo로 서버에 이전에 예약된 방 정보들을 불러와야 한다.
            if((UserRoomInfo)intent.getSerializableExtra("room_info") != null) {
                roomInfo = (UserRoomInfo) intent.getSerializableExtra("room_info");
            }
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
                finish();
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
                                /*sf = getSharedPreferences("reserve_data", MODE_PRIVATE);
                                SharedPreferences.Editor editor1 = sf.edit();
                                editor1.clear();
                                editor1.commit();*/

                                //로그아웃을 통해 로그인에 필요한 아이디, 비밀번호 데이터 삭제
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

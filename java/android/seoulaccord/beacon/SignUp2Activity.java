package android.seoulaccord.beacon;

import android.content.Intent;
import android.os.Bundle;
import android.seoulaccord.beacon.Data.UserRoomInfo;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp2Activity extends AppCompatActivity {
    String name;
    String birth;
    String reserve_code;
    //
    String server_name;
    String server_birth;
    String server_reserve_code;
    //
    UserRoomInfo roominfo;

    EditText name_et;
    EditText birth_et;
    EditText reservecode_et;

    Button skip_btn;
    Button reserveconfirm_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        name_et = (EditText)findViewById(R.id.signup2_id_et);
        birth_et = (EditText)findViewById(R.id.signup2_birth_et);
        reservecode_et = (EditText)findViewById(R.id.signup2_reservecode_et);

        skip_btn = (Button)findViewById(R.id.signup2_skip_btn);
        reserveconfirm_btn = (Button)findViewById(R.id.signup2_reserveconfirm_btn);

        reserveconfirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = name_et.getText().toString();
                birth = birth_et.getText().toString();
                reserve_code = reservecode_et.getText().toString();
                //일치하는 예약코드 확인 시에 방 번호 추가 userRoomInfo로 방 번호 등록
                //if문안에서 userroominfo 변수 체크인 체크아웃 방 번호 받아와야 한다. 서버통해서
                if(reserve_code.equals(server_reserve_code) && name.equals(server_name) && birth.equals(server_birth))
                {
                    Intent intent = new Intent(SignUp2Activity.this, SignUpActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("birth", birth);
                    SignUp2Activity.this.startActivity(intent);
                }
                //방 번호 일치하지 않을 시에 오류 메시지 전송
                {
                    Toast.makeText(getApplicationContext(), "정보 불일치", Toast.LENGTH_SHORT).show();
                }
            }
        });
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp2Activity.this, SignUpActivity.class);
                SignUp2Activity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        name_et.setText("");
        birth_et.setText("");
        reservecode_et.setText("");
        roominfo = null;
    }
}

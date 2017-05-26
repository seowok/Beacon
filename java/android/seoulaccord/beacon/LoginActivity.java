package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

    SharedPreferences sf;
    String id, pw;
    EditText edit_id;
    EditText edit_pw;
    Button sign_up_btn;
    Button login_btn;
    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_id=(EditText)findViewById(R.id.id_edit_text);
        edit_pw=(EditText)findViewById(R.id.pw_edit_text);
        sign_up_btn=(Button)findViewById(R.id.sign_up_btn);
        login_btn=(Button)findViewById(R.id.login_btn);
        layout = (LinearLayout)findViewById(R.id.login_layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit_id.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(edit_pw.getWindowToken(), 0);
            }
        });


        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(intent);
                finish();
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_id.getText().toString().equals("neosopt"))
                {
                    if(edit_pw.getText().toString().equals("abc"))
                    {
                        Toast.makeText(getApplicationContext(), "로그인 완료", Toast.LENGTH_SHORT).show();
                        sf = getSharedPreferences("login_data", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sf.edit();
                        editor.putString("id", edit_id.getText().toString()); // 입력
                        editor.putString("pw", edit_pw.getText().toString()); // 입력
                        editor.commit(); // 파일에 최종 반영함

                        edit_id.setText("");
                        edit_pw.setText("");
                        loginSuccess();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
            }
        });
        if(edit_id.getText().toString() .equals("") && edit_pw.getText().toString().equals(""))
        {
            SharedPreferences sf = getSharedPreferences("login_data", MODE_PRIVATE);
            String id = sf.getString("id", ""); // 키값으로 꺼냄
            String pw = sf.getString("pw", "");
            if(id.equals("neosopt") && pw.equals("abc"))//아이디 비밀번호 확인
            {
                Toast.makeText(getApplicationContext(), "자동 로그인", Toast.LENGTH_SHORT).show();
                loginSuccess();
            }
        }
    }

    void loginSuccess()
    {
        Intent intent = new Intent(LoginActivity.this, MyInfoActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}

package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.seoulaccord.beacon.Data.UserInfo;
import android.seoulaccord.beacon.Network.NetworkService;
import android.seoulaccord.beacon.Server.ServerController;
import android.seoulaccord.beacon.Server.UserResult;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {

    EditText edit_id;
    EditText edit_pw;
    Button sign_up_btn;
    Button login_btn;
    LinearLayout layout;

    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ////////////////////////서비스 객체 초기화////////////////////////
        service = ServerController.getInstance().getNetworkService();

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
                //server로 부터 데이터 받음
                    Call<UserResult> userResultCall = service.getUserResult(edit_id.getText().toString(), edit_pw.getText().toString());
                    userResultCall.enqueue(new Callback<UserResult>() {
                        @Override
                        public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                            if (response.isSuccessful()) {
                                if (response.body().message.equals("success")) {
                                    SharedPreferences sf = getSharedPreferences("login_data", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sf.edit();
                                    editor.putString("id", edit_id.getText().toString()); // 입력
                                    editor.putString("pw", edit_pw.getText().toString()); // 입력
                                    editor.putBoolean("loginSuccess", true);
                                    editor.commit(); // 파일에 최종 반영함

                                    UserInfo.user_id = edit_id.getText().toString();
                                    UserInfo.room_number = response.body().room_num;

                                    edit_id.setText("");
                                    edit_pw.setText("");
                                    loginSuccess();
                                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Login Fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
        });

        SharedPreferences sf = getSharedPreferences("login_data", MODE_PRIVATE);
        if(sf.getBoolean("loginSuccess", false)) {
            Toast.makeText(getApplicationContext(), "자동 로그인", Toast.LENGTH_SHORT).show();
            loginSuccess();
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

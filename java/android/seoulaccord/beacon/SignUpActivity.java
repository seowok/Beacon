package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.seoulaccord.beacon.Network.NetworkService;
import android.seoulaccord.beacon.Server.ServerController;
import android.seoulaccord.beacon.Server.UserData;
import android.seoulaccord.beacon.Server.UserResult;
import android.text.Editable;
import android.text.TextWatcher;
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

public class SignUpActivity extends Activity {
    InputMethodManager imm;

    EditText id_et, pw_et, name_et, birth_et, phone_et;
    Button id_check, submit, reset;
    LinearLayout root_view;

    boolean id_check_OK = false;

    private String id;
    private String password;
    private String name;

    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ////////////////////////서비스 객체 초기화////////////////////////
        service = ServerController.getInstance().getNetworkService();

        root_view = (LinearLayout)findViewById(R.id.signup_root_view);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        id_et = (EditText)findViewById(R.id.signup_id_et);
        pw_et = (EditText)findViewById(R.id.signup_password_et);
        name_et = (EditText)findViewById(R.id.signup_name_et);
        id_check = (Button)findViewById(R.id.id_check_btn);
        submit = (Button)findViewById(R.id.signup_submit_btn);
        reset = (Button)findViewById(R.id.signup_reset_btn);



        //hide virtual keyboard
        root_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        //check id if id input is already existing or not
        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<UserResult> userResultCall = service.getIdCheck(id_et.getText().toString());
                userResultCall.enqueue(new Callback<UserResult>() {
                    @Override
                    public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                        if(response.isSuccessful()){
                            if(response.body().message.equals("notExist")){
                                id = id_et.getText().toString();
                                id_check_OK = true;
                                Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.body().message.equals("Exist")){
                                id_check_OK = false;
                                Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                                id_et.setText("");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UserResult> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "ID Check Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        //reset BOOLEAN id_check_OK if id input is changed
        id_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                id_check_OK = false;
            }
        });


        //check which info is not filled
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_check_OK == false){
                    Toast.makeText(getApplicationContext(), "아이디 중복확인이 필요합니다", Toast.LENGTH_SHORT).show();
                }
                else if((id = id_et.getText().toString()) == ""){
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if((password = pw_et.getText().toString()) == ""){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else if((name = name_et.getText().toString()) == ""){
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    UserData new_user = new UserData();
                    new_user.user_id = id;
                    new_user.user_password = password;

                    Call<UserResult> resisterUser = service.resisterUser(new_user);
                    resisterUser.enqueue(new Callback<UserResult>() {
                        @Override
                        public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                            if(response.isSuccessful()){
                                if(response.body().message.equals("resister")){
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResult> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Resister Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        //reset input data
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = "";
                password = "";
                name = "";
                id_et.setText("");
                pw_et.setText("");
                name_et.setText("");

                Toast.makeText(getApplicationContext(), "입력 초기화", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}

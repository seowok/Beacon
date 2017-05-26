package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SignUpActivity extends Activity {
    InputMethodManager imm;

    EditText id_et, pw_et, name_et, birth_et, phone_et;
    Button id_check, submit, reset;
    LinearLayout root_view;
    UserInformation userInformation;

    private String id, password;
    private String name;
    private String birth;

    boolean id_check_OK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        root_view = (LinearLayout)findViewById(R.id.signup_root_view);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        id_et = (EditText)findViewById(R.id.signup_id_et);
        pw_et = (EditText)findViewById(R.id.signup_password_et);
        name_et = (EditText)findViewById(R.id.signup_name_et);
        birth_et = (EditText)findViewById(R.id.signup_birth_et);
        phone_et = (EditText)findViewById(R.id.signup_num_et) ;

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
                String id_input_text = id_et.getText().toString();
                if(UserInformation.checkUserData(id_input_text, "") == UserInformation.ID_NOT_FOUND){
                    id = id_input_text;
                    id_check_OK = true;
                    Toast.makeText(getApplicationContext(), "사용 가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                    id_et.setText("");
                }
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
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
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

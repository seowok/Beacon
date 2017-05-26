package android.seoulaccord.beacon;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ReserveInfoActivity extends Activity {
    final int FLOOR_MAX_ROOM = 10;
    LinearLayout layout;
    Button check_in_btn;
    Button check_out_btn;
    EditText room_cnt_et;
    Button next_btn;
    DatePickerDialog dialog;
    int check_in_year;
    int check_in_month;
    int check_in_day;
    int check_out_year;
    int check_out_month;
    int check_out_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_info);
        check_in_btn = (Button)findViewById(R.id.check_in_btn);
        check_out_btn = (Button)findViewById(R.id.check_out_btn);
        room_cnt_et = (EditText)findViewById(R.id.room_et);
        next_btn = (Button)findViewById(R.id.next_btn);
        layout = (LinearLayout)findViewById(R.id.reserve_info_layout);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(room_cnt_et.getWindowToken(), 0);
            }
        });

        check_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new DatePickerDialog(ReserveInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        check_in_btn.setText(year + "-" + "0" + (month + 1)+ "-" + dayOfMonth);
                        check_in_year = year;
                        check_in_month = month;
                        check_in_day = dayOfMonth;
                    }
                }, 2017, 0, 1);
                dialog.show();
            }
        });
        check_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new DatePickerDialog(ReserveInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        check_out_btn.setText(year + "-" +(month + 1)+ "-" + dayOfMonth);
                        check_out_year = year;
                        check_out_month = month;
                        check_out_day = dayOfMonth;
                    }
                }, 2017, 0, 1);
                dialog.show();
            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int error_check = 0;
                String check_in = check_in_btn.getText().toString();
                String check_out = check_out_btn.getText().toString();
                if(check_in.equals("YYYY-MM-DD") || check_out.equals("YYYY-MM-DD"))
                {
                    Toast.makeText(getApplicationContext(), "입력 날짜를 확인하세요.", Toast.LENGTH_SHORT).show();
                    error_check = 1;
                }
                if(!(check_in.equals("YYYY-MM-DD") && check_out.equals("YYYY-MM-DD")))
                {
                    if(check_in_year > check_out_year) {
                        error_check = 1;
                        Toast.makeText(getApplicationContext(), "입력 연도를 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(check_in_month > check_out_month) {
                        error_check = 1;
                        Toast.makeText(getApplicationContext(), "입력 달을 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else if(check_in_day >= check_out_day) {
                        error_check = 1;
                        Toast.makeText(getApplicationContext(), "입력 일을 확인하세요.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        error_check = 0;
                }
                if(!(Integer.parseInt(room_cnt_et.getText().toString()) >= 1 &&
                        Integer.parseInt(room_cnt_et.getText().toString()) <= FLOOR_MAX_ROOM))
                {
                    Toast.makeText(getApplicationContext(), "객실 수를 확인하세요.", Toast.LENGTH_SHORT).show();
                    error_check = 1;
                }
                if(error_check == 0)
                {
                    Intent intent = new Intent(ReserveInfoActivity.this, ReserveRoomActivity.class);
                    intent.putExtra("check_in", check_in_btn.getText().toString());
                    intent.putExtra("check_out", check_out_btn.getText().toString());
                    intent.putExtra("room_cnt", room_cnt_et.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(ReserveInfoActivity.this, MyInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

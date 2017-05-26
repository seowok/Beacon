package android.seoulaccord.beacon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ReserveRoomActivity extends Activity {
    final int MAX_FLOOR = 6;
    final int MIN_FLOOR = 1;
    final int MAX_ROOM_NUM = 10;
    UserRoomInfo roominfo;
    Button reserve_next_btn;
    Button[] room_btn_arr;
    Button up_btn;
    Button down_btn;
    Button now_floor_btn;
    TextView floor_tv;

    int max_room_cnt;
    int room_cnt = 0;
    int[][] user_room_check;//사용자 룸 체크
    int[][] real_room_check;//예약불가, 예약가능
    int now_floor = 1;
    Intent intent;
    View.OnClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_room);

        reserve_next_btn = (Button)findViewById(R.id.reserve_next_btn);
        room_btn_arr = new Button[MAX_ROOM_NUM + 1];
        room_btn_arr[1] = (Button) findViewById(R.id.room1);
        room_btn_arr[2] = (Button) findViewById(R.id.room2);
        room_btn_arr[3] = (Button) findViewById(R.id.room3);
        room_btn_arr[4] = (Button) findViewById(R.id.room4);
        room_btn_arr[5] = (Button) findViewById(R.id.room5);
        room_btn_arr[6] = (Button) findViewById(R.id.room6);
        room_btn_arr[7] = (Button) findViewById(R.id.room7);
        room_btn_arr[8] = (Button) findViewById(R.id.room8);
        room_btn_arr[9] = (Button) findViewById(R.id.room9);
        room_btn_arr[10] = (Button) findViewById(R.id.room10);
        up_btn = (Button) findViewById(R.id.floor_up_btn);
        down_btn = (Button) findViewById(R.id.floor_down_btn);
        now_floor_btn = (Button) findViewById(R.id.now_floor_btn);
        floor_tv = (TextView)findViewById(R.id.floor_tv);

        user_room_check = new int[MAX_FLOOR + 1][MAX_ROOM_NUM + 1];
        real_room_check = new int[MAX_FLOOR + 1][MAX_ROOM_NUM + 1];

        intent = getIntent();
        final String max_room_str = intent.getExtras().getString("room_cnt");
        max_room_cnt = Integer.parseInt(max_room_str);
        String room_check_in = intent.getExtras().getString("check_in");
        String room_check_out = intent.getExtras().getString("check_out");
        roominfo = new UserRoomInfo(room_check_in, room_check_out, max_room_cnt);

        for (int i = 1; i <= MAX_FLOOR; i++) {
            for (int j = 1; j <= MAX_ROOM_NUM; j++)
                user_room_check[i][j] = 0;
        }
        //TEST
        real_room_check[1][3] = 1;
        real_room_check[2][2] = 1;
        real_room_check[4][5] = 1;
        real_room_check[4][7] = 1;
        real_room_check[5][9] = 1;
        real_room_check[5][8] = 1;
        real_room_check[6][1] = 1;
        real_room_check[6][10] = 1;
        //TEST
        for (int i = 1; i <= MAX_ROOM_NUM; i++) {
            final int room_index = i;
            if (real_room_check[now_floor][i] == 1) {
                room_btn_arr[i].setBackgroundColor(Color.BLACK);
                user_room_check[now_floor][i] = -1;
            } else {
                user_room_check[now_floor][i] = 0;
                room_btn_arr[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (user_room_check[now_floor][room_index] == 0) {
                            if (room_cnt == max_room_cnt)
                                Toast.makeText(getApplicationContext(), "기존의 방을 클릭하여 취소하세요.", Toast.LENGTH_SHORT).show();
                            else {
                                room_btn_arr[room_index].setBackgroundColor(Color.BLUE);
                                room_btn_arr[room_index].setTextColor(Color.WHITE);
                                user_room_check[now_floor][room_index] = 1;
                                room_cnt++;
                                roominfo.getRoom_num_arr().add(room_btn_arr[room_index].getText().toString());
                            }
                        } else {
                            room_btn_arr[room_index].setBackgroundColor(Color.WHITE);
                            room_btn_arr[room_index].setTextColor(Color.BLACK);
                            user_room_check[now_floor][room_index] = 0;
                            room_cnt--;
                            for(int i = 0; i < roominfo.getRoom_num_arr().size(); i++)
                            {
                                if(roominfo.getRoom_num_arr().get(i) == room_btn_arr[room_index].getText().toString())
                                    roominfo.getRoom_num_arr().remove(i);
                            }
                        }
                    }
                });
            }
        }
        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now_floor + 1 <= MAX_FLOOR) {
                    now_floor++;
                    floor_tv.setText(now_floor + "층");
                    now_floor_btn.setText("" + now_floor + "층");
                    for (int i = 1; i <= MAX_ROOM_NUM; i++) {
                        final int room_index = i;
                        if (real_room_check[now_floor][i] == 1) {
                            room_btn_arr[room_index].setBackgroundColor(Color.BLACK);
                            user_room_check[now_floor][room_index] = -1;
                        }
                        else {
                            if(room_index < 10)
                                room_btn_arr[room_index].setText(now_floor + "0" + room_index);
                            else
                                room_btn_arr[room_index].setText(now_floor + "" + room_index);
                            if(user_room_check[now_floor][room_index]== 0) {
                                room_btn_arr[room_index].setBackgroundColor(Color.WHITE);
                                room_btn_arr[room_index].setTextColor(Color.BLACK);
                            }
                            if(user_room_check[now_floor][room_index]== 1) {
                                room_btn_arr[room_index].setBackgroundColor(Color.BLUE);
                                room_btn_arr[room_index].setTextColor(Color.WHITE);
                            }
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "이 호텔의 최대 층은 " + MAX_FLOOR + "층 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        down_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (now_floor - 1 >= MIN_FLOOR) {
                    now_floor--;
                    floor_tv.setText(now_floor + "층");
                    now_floor_btn.setText("" + now_floor + "층");
                    for (int i = 1; i <= MAX_ROOM_NUM; i++) {
                        final int room_index = i;
                        if (real_room_check[now_floor][i] == 1) {
                            room_btn_arr[room_index].setBackgroundColor(Color.BLACK);
                            user_room_check[now_floor][room_index] = -1;
                        }
                        else {
                            if(room_index < 10)
                                room_btn_arr[room_index].setText(now_floor + "0" + room_index);
                            else
                                room_btn_arr[room_index].setText(now_floor + "" + room_index);
                            if(user_room_check[now_floor][room_index]== 0) {
                                room_btn_arr[room_index].setBackgroundColor(Color.WHITE);
                                room_btn_arr[room_index].setTextColor(Color.BLACK);
                            }
                            if(user_room_check[now_floor][room_index]== 1) {
                                room_btn_arr[room_index].setBackgroundColor(Color.BLUE);
                                room_btn_arr[room_index].setTextColor(Color.WHITE);
                            }
                        }
                    }
                } else
                    Toast.makeText(getApplicationContext(), "이 호텔의 최하 층은 " + MIN_FLOOR + "층 입니다.", Toast.LENGTH_SHORT).show();
            }
        });
        reserve_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room_cnt == max_room_cnt)
                {
                    Intent intent2 = new Intent(ReserveRoomActivity.this, MyInfoActivity.class);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent2.putExtra("room_info", roominfo);
                    startActivity(intent2);
                }
                else
                    Toast.makeText(getApplicationContext(), "선택하신 방의 개수가 다릅니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

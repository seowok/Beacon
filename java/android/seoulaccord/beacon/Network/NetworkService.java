package android.seoulaccord.beacon.Network;

import android.seoulaccord.beacon.Server.RoomResult;
import android.seoulaccord.beacon.Server.UserData;
import android.seoulaccord.beacon.Server.UserResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by seowo on 2017-06-10.
 */

public interface NetworkService {

    //로그인을 성공하면 "success" message
    @GET("/user/{id}")
    Call<UserResult> getUserResult(@Path("id") String id, String password);

    //기존에 없는 id이면 "notExist" message
    //기존에 있는 id이면 "Exist" message
    @GET("/user/{id}")
    Call<UserResult> getIdCheck(@Path("id") String id);

    //방 정보 받아오기 성공하면 "ok" message
    @GET("/room/{room_num}")
    Call<RoomResult> getRoomInfo(@Path("room_id") String room_num);

    //회원가입 성공하면 "resister" message
    @POST("/user/")
    Call<UserResult> resisterUser(@Body UserData user_data);
}

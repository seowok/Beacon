package android.seoulaccord.beacon.Server;

import android.app.Application;
import android.seoulaccord.beacon.Network.NetworkService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by seowo on 2017-06-10.
 */

public class ServerController extends Application{

    private static ServerController instance;

    private static String baseUrl = "http://165.194.35.156:3000/checkuser";

    private NetworkService networkService;

    public static ServerController getInstance(){
        return instance;
    }

    public NetworkService getNetworkService(){ return networkService; }

    @Override
    public void onCreate() {
        super.onCreate();

        ServerController.instance = this;
        buildService();
    }

    public void buildService(){
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        networkService = retrofit.create(NetworkService.class);
    }
}

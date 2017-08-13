package au.com.lionslogistics.lionstv.service;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import au.com.lionslogistics.lionstv.model.Channel;
import au.com.lionslogistics.lionstv.rest.LionsTvApiClient;
import au.com.lionslogistics.lionstv.rest.LionsTvApiInterface;
import au.com.lionslogistics.lionstv.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex-daphne on 30/07/2017.
 * All rights reserved
 */

public class ChannelService {
    private static ChannelService instance=null;
    private MainActivity parentActivity=null;
    private ChannelService(MainActivity mainActivity){
        this.parentActivity=mainActivity;
    }

    public static ChannelService getInstance(MainActivity mainActivity){
        if (instance==null){
            instance=new ChannelService(mainActivity);
        }
        return instance;
    }

    public void getChannelsByCategoryId(int categoryId){
        LionsTvApiInterface restApi = LionsTvApiClient.getInstance().create(LionsTvApiInterface.class);
        Call<Channel[]> call = restApi.getChannelsByCategoryId(UserService.getInstance().getToken(),categoryId);
        call.enqueue(new Callback<Channel[]>() {
            @Override
            public void onResponse(@NonNull Call<Channel[]> call, @NonNull Response<Channel[]> response) {
                if (response.body()==null){
                    parentActivity.onConnectionError("101");
                    return;
                }
                List<Channel> data= Arrays.asList(response.body());
                parentActivity.onChannelListReceived(data);
            }

            @Override
            public void onFailure(@NonNull Call<Channel[]> call, @NonNull Throwable t) {
                parentActivity.onConnectionError("103 - Network Error");
            }
        });
    }
}

package au.com.lionslogistics.lionstv.service;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import au.com.lionslogistics.lionstv.model.AuthPostRequest;
import au.com.lionslogistics.lionstv.rest.LionsTvApiClient;
import au.com.lionslogistics.lionstv.rest.LionsTvApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public class UserService {
    private static final String TAG="UserService";
    private static UserService instance=null;
    private String token;
    private AuthenticationService parentActivity;
    private UserService(){

    }

    public static UserService getInstance(){
        if (instance==null){
            instance=new UserService();
        }
        return instance;
    }

    public UserService bindActivity(Activity activity){
        parentActivity= (AuthenticationService) activity;
        return instance;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }

    public void authenticate(String email, final String password){
        AuthPostRequest postRequest=new AuthPostRequest();
        postRequest.setUsername(email);
        postRequest.setPassword(password);

        LionsTvApiInterface restApi = LionsTvApiClient.getInstance().create(LionsTvApiInterface.class);
        Call<String> call=restApi.authenticateUser(postRequest);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code()!=200 || response.body()==null){
                    Log.e(TAG,"Code: "+response.code());
                    parentActivity.onAuthenticationFailed();
                }
                Log.e(TAG,response.message());
                String result= response.body();
                if (result!=null){
                    token="Basic "+result;
                    parentActivity.onAuthenticationSuccessful();
                } else {
                    parentActivity.onAuthenticationFailed();
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
                parentActivity.onNetworkError();
            }
        });
    }


}

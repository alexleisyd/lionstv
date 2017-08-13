package au.com.lionslogistics.lionstv.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public class LionsTvApiClient {
    private static Retrofit instance=null;
    private static final String BASE_URL="http://lionstv-env.qbb4ktqwiy.ap-southeast-2.elasticbeanstalk.com/";
    public static Retrofit getInstance(){
        if (instance==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            instance=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }
}

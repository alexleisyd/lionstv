package au.com.lionslogistics.lionstv.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public class LionsTvApiClient {
    private static Retrofit instance=null;
    private static final String BASE_URL="http://192.168.1.150:8080/lionsTv_rest/";
    public static Retrofit getInstance(){
        if (instance==null){
            instance=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }
}

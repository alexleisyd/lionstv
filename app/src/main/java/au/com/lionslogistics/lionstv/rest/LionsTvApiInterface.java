package au.com.lionslogistics.lionstv.rest;

import au.com.lionslogistics.lionstv.model.Category;
import au.com.lionslogistics.lionstv.model.Channel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public interface LionsTvApiInterface {
    @GET("category/get")
    Call<Category[]> getAllCategories(@Header("Authorization") String token);

    @GET("channel/get")
    Call<Channel[]> getChannelsByCategoryId(@Header("Authorization") String token, @Query("category") int category);
}

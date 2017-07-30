package au.com.lionslogistics.lionstv.service;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import au.com.lionslogistics.lionstv.model.Category;
import au.com.lionslogistics.lionstv.rest.LionsTvApiClient;
import au.com.lionslogistics.lionstv.rest.LionsTvApiInterface;
import au.com.lionslogistics.lionstv.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex-daphne on 29/07/2017.
 * All rights reserved
 */

public class CategoryService {
    private static final String TAG="CategoryService";
    private static CategoryService instance=null;
    private MainActivity parentActivity=null;
    private CategoryService(MainActivity mainActivity){
        this.parentActivity=mainActivity;
    }

    public static CategoryService getInstance(MainActivity mainActivity) {

        if (instance==null){
            instance=new CategoryService(mainActivity);
        }
        return instance;
    }

    public void getAllCategories(){
        LionsTvApiInterface restApi = LionsTvApiClient.getInstance().create(LionsTvApiInterface.class);
        Call<Category[]> call = restApi.getAllCategories(UserService.getInstance().getToken());
        call.enqueue(new Callback<Category[]>() {
            @Override
            public void onResponse(@NonNull Call<Category[]> call, @NonNull Response<Category[]> response) {
                if (response.body()==null){
                    Log.e(TAG,"Code: "+response.code());
                    Log.e(TAG,"Message: "+response.toString());
                    parentActivity.onConnectionError("101");
                    return;
                }
                List<Category> data= Arrays.asList(response.body());
                parentActivity.onCategoryListReceived(data);
            }

            @Override
            public void onFailure(@NonNull Call<Category[]> call, @NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
                parentActivity.onConnectionError("102");
            }
        });
    }
}

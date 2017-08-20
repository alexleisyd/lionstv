package au.com.lionslogistics.lionstv.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Category;
import au.com.lionslogistics.lionstv.model.Channel;
import au.com.lionslogistics.lionstv.presenter.CardPresenter;
import au.com.lionslogistics.lionstv.rest.LionsTvApiClient;
import au.com.lionslogistics.lionstv.rest.LionsTvApiInterface;
import au.com.lionslogistics.lionstv.service.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Alex Lei on 14/8/17.
 * All Rights Reserved.
 */

public class MainFragment extends BrowseFragment implements OnItemViewClickedListener,OnItemViewSelectedListener{
    private static final String TAG="MainFragment";
    private static final int BACKGROUND_UPDATE_DELAY = 300;
    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mCategoryRowAdapter;
    private Map<Integer,ArrayObjectAdapter> mChannelAdapter;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private Timer mBackgroundTimer;
    private Uri mBackgroundURI;
    private BackgroundManager mBackgroundManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mChannelAdapter=new HashMap<>();
        //Load category data from webservice
        loadCategoryData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Initiate background manager
        initBackgroundManager();
        //Setup UI elements on BrowseFragment
        setupUIElements();
        //Setup listeners
        setupEventListeners();

        mCategoryRowAdapter=new ArrayObjectAdapter(new ListRowPresenter());
        setAdapter(mCategoryRowAdapter);
    }

    @Override
    public void onDestroy() {
        if (mBackgroundTimer!=null){
            mBackgroundTimer.cancel();
            mBackgroundTimer=null;
        }
        mBackgroundManager=null;
        super.onDestroy();

    }

    @Override
    public void onStop() {
        mBackgroundManager.release();
        super.onStop();
    }

    private void loadCategoryData(){
        LionsTvApiInterface restApi = LionsTvApiClient.getInstance().create(LionsTvApiInterface.class);
        Call<Category[]> call = restApi.getAllCategories(UserService.getInstance().getToken());
        call.enqueue(new Callback<Category[]>() {
            @Override
            public void onResponse(@NonNull Call<Category[]> call, @NonNull Response<Category[]> response) {
                if (response.body()==null){
                    Log.e(TAG,"Code: "+response.code());
                    Log.e(TAG,"Message: "+response.toString());
                    showDialog(getString(R.string.label_error),getString(R.string.error_server_error)+getString(R.string.label_error_code)+response.code());
                } else {
                    List<Category> data= Arrays.asList(response.body());
                    onCategoryListReceived(data);
                }

            }

            @Override
            public void onFailure(@NonNull Call<Category[]> call, @NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
                showDialog(getString(R.string.label_error),getString(R.string.error_connection_error)+getString(R.string.label_error_code)+404);
            }
        });
    }

    private void onCategoryListReceived(List<Category> data){
        mCategoryRowAdapter.clear();
        for (Category category:data){
            HeaderItem headerItem=new HeaderItem(category.getName());
            ArrayObjectAdapter existingAdaptor=mChannelAdapter.get(category.getId());
            if (existingAdaptor==null) {
                ArrayObjectAdapter adapter=new ArrayObjectAdapter(new CardPresenter());
                ListRow listRow=new ListRow(headerItem,adapter);
                mCategoryRowAdapter.add(listRow);
                mChannelAdapter.put(category.getId(),adapter);
                loadChannelData(category.getId());
            } else {
                ListRow listRow=new ListRow(headerItem,existingAdaptor);
                mCategoryRowAdapter.add(listRow);
            }

        }
    }

    private void loadChannelData(final int categoryId){
        LionsTvApiInterface restApi = LionsTvApiClient.getInstance().create(LionsTvApiInterface.class);
        Call<Channel[]> call = restApi.getChannelsByCategoryId(UserService.getInstance().getToken(),categoryId);
        call.enqueue(new Callback<Channel[]>() {
            @Override
            public void onResponse(@NonNull Call<Channel[]> call, @NonNull Response<Channel[]> response) {
                if (response.body()==null){
                    Log.e(TAG,"Code: "+response.code());
                    Log.e(TAG,"Message: "+response.toString());
                    showDialog(getString(R.string.label_error),getString(R.string.error_server_error)+getString(R.string.label_error_code)+response.code());
                } else {
                    List<Channel> data= Arrays.asList(response.body());
                    onChannelDataReceived(categoryId,data);
                }

            }
            @Override
            public void onFailure(@NonNull Call<Channel[]> call, @NonNull Throwable t) {
                Log.e(TAG,t.getMessage());
                showDialog(getString(R.string.label_error),getString(R.string.error_connection_error)+getString(R.string.label_error_code)+404);
            }
        });
    }

    private void onChannelDataReceived(int categoryId, List<Channel> data){
        ArrayObjectAdapter adapter=mChannelAdapter.get(categoryId);
        adapter.clear();
        adapter.addAll(0,data);
    }

    private void initBackgroundManager(){
        mBackgroundManager=BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground=getResources().getDrawable(R.mipmap.banner,null);
        mMetrics=new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements(){
        setTitle(getString(R.string.app_title));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);
        setBrandColor(ContextCompat.getColor(getActivity(),R.color.colorFastlane));
        setSearchAffordanceColor(ContextCompat.getColor(getActivity(),R.color.colorAccent));
    }

    private void setupEventListeners(){
        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
        setOnItemViewClickedListener(this);
        setOnItemViewSelectedListener(this);
    }

    private void updateBackground(String uri) {
        int width = mMetrics.widthPixels;
        int height = mMetrics.heightPixels;
        Glide.with(this)
                .load(uri)
                .asBitmap()
                .centerCrop()
                .error(mDefaultBackground)
                .into(new SimpleTarget<Bitmap>(width, height) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap>
                            glideAnimation) {
                        mBackgroundManager.setBitmap(resource);
                    }
                });
        mBackgroundTimer.cancel();
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY);
    }

    private void showDialog(String title, String content){
        AlertDialog dialog=new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_Leanback))
                .setTitle(title)
                .setMessage(content)
                .create();
        dialog.show();
    }

    @Override
    public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
        Channel channel= (Channel) item;
        Intent intent=new Intent(getActivity(),DetailsActivity.class);
        intent.putExtra(DetailsActivity.CHANNEL,channel);
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                getActivity(),
                ((ImageCardView) itemViewHolder.view).getMainImageView(),
                DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
        getActivity().startActivity(intent,bundle);
    }

    @Override
    public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {

    }

    private class UpdateBackgroundTask extends TimerTask {

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mBackgroundURI != null) {
                        updateBackground(mBackgroundURI.toString());
                    }
                }
            });
        }
    }
}

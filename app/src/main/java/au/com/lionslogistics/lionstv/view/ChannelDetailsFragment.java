package au.com.lionslogistics.lionstv.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.DetailsFragment;
import android.support.v17.leanback.widget.Action;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ClassPresenterSelector;
import android.support.v17.leanback.widget.DetailsOverviewRow;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewRowPresenter;
import android.support.v17.leanback.widget.FullWidthDetailsOverviewSharedElementHelper;
import android.support.v17.leanback.widget.OnActionClickedListener;
import android.support.v17.leanback.widget.SparseArrayObjectAdapter;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Channel;
import au.com.lionslogistics.lionstv.model.Source;
import au.com.lionslogistics.lionstv.presenter.DetailsDescriptionPresenter;

/**
 * Created by alex-daphne on 18/08/2017.
 * All rights reserved
 */

public class ChannelDetailsFragment extends DetailsFragment {
    private static final String TAG="ChannelDetailsFragment";
    private ArrayObjectAdapter mRowsAdapter;
    private Channel mSelectedChannel;
    private BackgroundManager mBackgroundManager;
    private Drawable mDefaultBackground;
    private DisplayMetrics mMetrics;
    private FullWidthDetailsOverviewSharedElementHelper mHelper;
    private ClassPresenterSelector mPresenterSelector;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBackgroundManager();

        // Get Channel Object from Intent
        mSelectedChannel=getActivity().getIntent().getParcelableExtra(DetailsActivity.CHANNEL);

        if (mSelectedChannel!=null){
            setupAdapter();
            setupDetailsOverviewRow();
        }
    }

    @Override
    public void onStop() {
        mBackgroundManager.release();
        super.onStop();
    }

    private void initBackgroundManager(){
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
        mDefaultBackground = getResources().getDrawable(R.drawable.ic_photo, null);
        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupAdapter(){
        FullWidthDetailsOverviewRowPresenter detailsPresenter=new FullWidthDetailsOverviewRowPresenter(new DetailsDescriptionPresenter());
        detailsPresenter.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.cardview_dark_background));
        detailsPresenter.setInitialState(FullWidthDetailsOverviewRowPresenter.STATE_HALF);

        //Transitioning the Thumbnail Photo
        mHelper=new FullWidthDetailsOverviewSharedElementHelper();
        mHelper.setSharedElementEnterTransition(getActivity(),DetailsActivity.SHARED_ELEMENT_NAME);
        detailsPresenter.setListener(mHelper);
        detailsPresenter.setParticipatingEntranceTransition(false);
        prepareEntranceTransition();

        //On Click Listener
        detailsPresenter.setOnActionClickedListener(new OnActionClickedListener() {
            @Override
            public void onActionClicked(Action action) {
                Source source=mSelectedChannel.getSources()[(int) action.getId()];
                Intent intent=new Intent(getActivity(),PlayerActivity.class);
                intent.setAction(PlayerActivity.ACTION_VIEW);
                intent.setData(Uri.parse(source.getUrl()));
                getActivity().startActivity(intent);
            }
        });
        mPresenterSelector=new ClassPresenterSelector();
        mPresenterSelector.addClassPresenter(DetailsOverviewRow.class,detailsPresenter);
        mRowsAdapter=new ArrayObjectAdapter(mPresenterSelector);
        setAdapter(mRowsAdapter);

    }

    private void setupDetailsOverviewRow(){
        final DetailsOverviewRow row=new DetailsOverviewRow(mSelectedChannel);
        Glide.with(this)
                .load(mSelectedChannel.getThumbnailUrl())
                .asBitmap()
                .dontAnimate()
                .error(R.mipmap.television19)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        row.setImageBitmap(getActivity(),resource);
                        startEntranceTransition();
                    }
                });
        SparseArrayObjectAdapter adapter=new SparseArrayObjectAdapter();
        Source[] sources=mSelectedChannel.getSources();
        for (int i=0;i<sources.length;i++){
            adapter.set(i,new Action(i,sources[i].getName(),sources[i].getName()));
        }
        row.setActionsAdapter(adapter);
        mRowsAdapter.add(row);
    }
}

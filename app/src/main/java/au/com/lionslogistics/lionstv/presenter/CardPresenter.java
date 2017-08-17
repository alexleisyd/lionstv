package au.com.lionslogistics.lionstv.presenter;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Channel;

/**
 * Created by Alex Lei on 17/8/17.
 * All rights reserved.
 */

public class CardPresenter extends Presenter {
    private int mSelectedBackgroundColor = -1;
    private int mDefaultBackgroundColor = -1;
    private Drawable mDefaultCardImage;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mDefaultBackgroundColor= ContextCompat.getColor(parent.getContext(), R.color.cardview_dark_background);
        mSelectedBackgroundColor=ContextCompat.getColor(parent.getContext(),android.R.color.darker_gray);
        mDefaultCardImage=parent.getResources().getDrawable(R.drawable.ic_photo,null);
        ImageCardView cardView=new ImageCardView(parent.getContext()){
            @Override
            public void setSelected(boolean selected) {
                updateCardBackgroundColor(this,selected);
                super.setSelected(selected);
            }
        };

        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        updateCardBackgroundColor(cardView,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Channel channel= (Channel) item;
        ImageCardView cardView= (ImageCardView) viewHolder.view;
        cardView.setTitleText(channel.getTitle());
        cardView.setContentText(channel.getTitle());
        if (channel.getThumbnailUrl()!=null){
            Resources res=cardView.getResources();
            int width = res.getDimensionPixelSize(R.dimen.card_width);
            int height = res.getDimensionPixelSize(R.dimen.card_height);
            cardView.setMainImageDimensions(width, height);
            cardView.setMainImageScaleType(ImageView.ScaleType.FIT_CENTER);
            Glide.with(cardView.getContext()).load(channel.getThumbnailUrl()).override(width,height).fitCenter().error(mDefaultCardImage).into(cardView.getMainImageView());
        }

    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }

    private void updateCardBackgroundColor(ImageCardView view, boolean selected){
        int color=selected?mSelectedBackgroundColor:mDefaultBackgroundColor;
        view.setBackgroundColor(color);
        view.findViewById(R.id.info_field).setBackgroundColor(color);
    }
}

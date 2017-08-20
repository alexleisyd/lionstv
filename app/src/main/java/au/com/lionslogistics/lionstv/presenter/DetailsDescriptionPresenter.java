package au.com.lionslogistics.lionstv.presenter;

import android.support.v17.leanback.widget.AbstractDetailsDescriptionPresenter;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Channel;

/**
 * Created by alex-daphne on 18/08/2017.
 * All rights reserved
 */

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {
    @Override
    protected void onBindDescription(ViewHolder vh, Object item) {
        Channel channel= (Channel) item;
        vh.getTitle().setText(channel.getTitle());
        vh.getSubtitle().setText(channel.getTitle());
        vh.getBody().setText(R.string.dummy_body);
    }
}

package au.com.lionslogistics.lionstv.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import au.com.lionslogistics.lionstv.R;
import au.com.lionslogistics.lionstv.model.Channel;
import au.com.lionslogistics.lionstv.view.PlayerActivity;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ThumbnailViewHolder>{
    private Context mContext;
    private List<Channel> channels;
    private Channel selectedItem;

    public ThumbnailAdapter(Context mContext, List<Channel> channels) {
        this.mContext = mContext;
        this.channels = channels;
    }

    @Override
    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        return new ThumbnailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        final Channel channel=channels.get(position);
        holder.thumbnail.setImageURI(channel.getThumbnailUrl());
        holder.title.setText(channel.getTitle());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,PlayerActivity.class);
                intent.setData(Uri.parse(channel.getSource()));
                intent.setAction(PlayerActivity.ACTION_VIEW);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }


    class ThumbnailViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parent;
        SimpleDraweeView thumbnail;
        TextView title;
        public ThumbnailViewHolder(View itemView) {
            super(itemView);
            parent= (LinearLayout) itemView.findViewById(R.id.parentPanel);
            title= (TextView) itemView.findViewById(R.id.title);
            thumbnail= (SimpleDraweeView) itemView.findViewById(R.id.thumbnail);
            GenericDraweeHierarchy hierarchy= GenericDraweeHierarchyBuilder.newInstance(mContext.getResources()).setActualImageScaleType(ScalingUtils.ScaleType.CENTER_INSIDE).setPlaceholderImage(R.drawable.ic_photo).build();
            thumbnail.setHierarchy(hierarchy);
        }
    }

}

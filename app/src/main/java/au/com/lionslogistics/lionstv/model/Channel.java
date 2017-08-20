package au.com.lionslogistics.lionstv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class Channel implements Parcelable{
    private String thumbnailUrl;
    private String title;
    private String subTitle;
    private String description;
    private Source[] sources;

    public Channel(String thumbnailUrl, String title,String subTitle,String description, Source[] sources) {
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.subTitle=subTitle;
        this.description=description;
        this.sources = sources;
    }

    private Channel(Parcel in){
        thumbnailUrl=in.readString();
        title=in.readString();
        subTitle=in.readString();
        description=in.readString();
        sources= (Source[]) in.createTypedArray(Source.CREATOR);
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Source[] getSources() {
        return sources;
    }

    public void setSources(Source[] source) {
        this.sources = source;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeString(this.description);
        dest.writeTypedArray(this.sources,0);
    }

    public static final Parcelable.Creator CREATOR=new Parcelable.Creator(){

        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };

}

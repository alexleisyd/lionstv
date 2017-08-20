package au.com.lionslogistics.lionstv.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class Source implements Parcelable{
    private String name;
    private String type;
    private String url;

    public Source(String name,String type, String url) {
        this.name = name;
        this.type=type;
        this.url = url;
    }

    public Source(Parcel in){
        this.name=in.readString();
        this.type=in.readString();
        this.url=in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(url);
    }

    public static final Parcelable.Creator CREATOR=new Parcelable.Creator(){

        @Override
        public Source createFromParcel(Parcel source) {
            return new Source(source);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };
}

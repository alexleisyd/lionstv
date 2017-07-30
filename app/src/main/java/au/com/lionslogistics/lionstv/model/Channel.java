package au.com.lionslogistics.lionstv.model;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class Channel {
    private String thumbnailUrl;
    private String title;
    private Source[] sources;

    public Channel(String thumbnailUrl, String title, Source[] sources) {
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.sources = sources;
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
}

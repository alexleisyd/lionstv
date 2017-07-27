package au.com.lionslogistics.lionstv.model;

/**
 * Created by alex-daphne on 24/07/2017.
 * All rights reserved
 */

public class Channel {
    private String thumbnailUrl;
    private String title;
    private String source;

    public Channel(String thumbnailUrl, String title, String source) {
        this.thumbnailUrl = thumbnailUrl;
        this.title = title;
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

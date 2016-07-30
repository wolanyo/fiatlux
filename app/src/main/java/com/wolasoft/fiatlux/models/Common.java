package com.wolasoft.fiatlux.models;

/**
 * Created by kkoudo on 14/04/2016.
 */
public abstract class Common {
    protected int id ;
    protected String title ;
    protected String excerpt ;
    protected String image;
    protected String mediaType;
    protected String mediaURL;

    public Common(){

    }

    public Common(int id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Common(int id, String title, String excerpt, String image) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.image = image;
    }

    public Common(int id, String title, String excerpt, String image, String mediaType, String mediaURL) {
        this.id = id;
        this.title = title;
        this.excerpt = excerpt;
        this.image = image;
        this.mediaType = mediaType;
        this.mediaURL = mediaURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }
}

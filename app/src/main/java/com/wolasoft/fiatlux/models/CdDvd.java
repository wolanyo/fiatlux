package com.wolasoft.fiatlux.models;

/**
 * Created by osiris on 25/05/16.
 */
public class CdDvd extends Document {
    private String duration;
    private String discType;

    protected String mediaURL;

    public CdDvd() {
    }

    public CdDvd(int id, String title, String author, String imageURL, float price) {
        super(id, title, author, imageURL, price);
    }

    public CdDvd(int id, String title, String author, String reference, float price, String imageURL, String excerpt, String duration, String discType, String mediaURL) {
        super(id, title, author, reference, price, imageURL, excerpt);
        this.duration = duration;
        this.discType = discType;
        this.mediaURL = mediaURL;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDiscType() {
        return discType;
    }

    public void setDiscType(String discType) {
        this.discType = discType;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public void setMediaURL(String mediaURL) {
        this.mediaURL = mediaURL;
    }
}

package com.wolasoft.fiatlux.models;

/**
 * Created by kkoudo on 14/04/2016.
 */
public class Chapter extends Common{
    private String content ;
    private int position ;

    public Chapter() {
    }

    public Chapter(int id, String title, String imageURL) {
        super(id, title, imageURL);
    }

    public Chapter(int id, String title, String excerpt, String imageURL) {
        super(id, title, excerpt, imageURL);
    }

    public Chapter(int id, String title, String imageURL, String content, int position) {
        super(id, title, imageURL);
        this.content = content;
        this.position = position;
    }

    public Chapter(int id, String title, String excerpt, String imageURL, String mediaType, String mediaURL, String content, int position) {
        super(id, title, excerpt, imageURL, mediaType, mediaURL);
        this.content = content;
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

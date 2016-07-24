package com.wolasoft.fiatlux.models;

import com.wolasoft.fiatlux.config.Utils;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class Post extends Common {

    private String postDate ;
    private String postTime ;
    private String content ;

    public Post(){
    }

    public Post(int id, String title, String imageURL) {
        super(id, title, imageURL);
    }

    public Post(int id, String title, String excerpt, String imageURL) {
        super(id, title, excerpt, imageURL);
    }

    public Post(int id, String title, String imageURL, String postDate, String postTime, String content) {
        super(id, title, imageURL);
        this.postDate = postDate;
        this.postTime = postTime;
        this.content = content;
    }

    public Post(int id, String title, String excerpt, String imageURL, String mediaType, String mediaURL, String postDate, String postTime, String content) {
        super(id, title, excerpt, imageURL, mediaType, mediaURL);
        this.postDate = postDate;
        this.postTime = postTime;
        this.content = content;
    }

    public String getPostDate() {
        return Utils.reverseDate(postDate);
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostTime() {
        return postTime.substring(0,5);
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

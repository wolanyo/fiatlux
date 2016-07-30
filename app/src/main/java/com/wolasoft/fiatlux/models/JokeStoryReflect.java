package com.wolasoft.fiatlux.models;

/**
 * Created by osiris on 15/05/16.
 */
public class JokeStoryReflect extends Common {

    private String content;

    public JokeStoryReflect() {
    }

    public JokeStoryReflect(int id, String title, String imageURL, String content) {
        super(id, title, imageURL);
        this.content = content;
    }

    public JokeStoryReflect(int id, String title, String excerpt, String imageURL, String content) {
        super(id, title, excerpt, imageURL);
        this.content = content;
    }

    public JokeStoryReflect(int id, String title, String excerpt, String imageURL, String mediaType, String mediaURL, String content) {
        super(id, title, excerpt, imageURL, mediaType, mediaURL);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

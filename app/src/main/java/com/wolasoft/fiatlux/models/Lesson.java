package com.wolasoft.fiatlux.models;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class Lesson extends Common {
    private int position ;
    private String contentFile;

    public Lesson() {
    }

    public Lesson(int id, String title, String imageURL) {
        super(id, title, imageURL);
    }

    public Lesson(int id, String title, String excerpt, String imageURL) {
        super(id, title, excerpt, imageURL);
    }

    public Lesson(int id, String title, String excerpt, String imageURL, String mediaURL, int position) {
        super(id, title, excerpt, imageURL);
        this.mediaURL = mediaURL;
        this.position = position;
    }

    public Lesson(int id, String title, String excerpt, String imageURL, String mediaType, String mediaURL, String mediaURL1, int position) {
        super(id, title, excerpt, imageURL, mediaType, mediaURL);
        mediaURL = mediaURL1;
        this.position = position;
    }


    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getContentFile() {
        return contentFile;
    }

    public void setContentFile(String contentFile) {
        this.contentFile = contentFile;
    }
}

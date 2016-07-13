package com.wolasoft.fiatlux.models;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class Help extends Common{
    private String author;
    private String publicationDate;

    public Help() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}

package com.wolasoft.fiatlux.models;

/**
 * Created by kkoudo on 05/03/2016.
 */
public abstract class Document {
    protected int id;
    protected String title ;
    protected String author ;
    protected String reference ;
    protected float price ;
    protected String image ;
    protected String excerpt;

    public Document(){

    }

    public Document(int id, String title, String author, String image, float price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.image = image;
        this.price = price;
    }

    public Document(int id, String title, String author, String reference, float price, String image, String excerpt){
        this.id = id ;
        this.title = title ;
        this.author = author ;
        this.reference = reference ;
        this.price = price ;
        this.image = image ;
        this.excerpt = excerpt;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
}

package com.wolasoft.fiatlux.models;

/**
 * Created by kkoudo on 05/03/2016.
 */
public class Book extends Document {

    private int numberOfPage ;
    private String excerptFile;

    public Book(){

    }

    public Book(int id, String title, String author, String imageURL, float price) {
        super(id, title, author, imageURL, price);
    }

    public Book(int id, String title, String author, String reference, float price, String imageURL, String summary, int numberOfPage){
        super(id, title, author, reference, price, imageURL, summary);
        this.numberOfPage = numberOfPage ;
    }

    public int getNumberOfPage() {
        return numberOfPage;
    }

    public void setNumberOfPage(int numberOfPage) {
        this.numberOfPage = numberOfPage;
    }

    public String getExcerptFile() {
        return excerptFile;
    }

    public void setExcerptFile(String excerptFile) {
        this.excerptFile = excerptFile;
    }
}

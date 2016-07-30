package com.wolasoft.fiatlux.models;

import com.wolasoft.fiatlux.config.Utils;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class Publicity extends Common {
    private String publicationDate;

    public Publicity() {
    }

    public String getPublicationDate() {
        return Utils.reverseDate(publicationDate);
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}

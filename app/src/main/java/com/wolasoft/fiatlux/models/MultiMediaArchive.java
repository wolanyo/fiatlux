package com.wolasoft.fiatlux.models;

import com.wolasoft.fiatlux.config.Utils;

/**
 * Created by osiris on 24/05/16.
 */
public class MultiMediaArchive extends Common {
    private String publishDate = "";

    public MultiMediaArchive() {
    }

    public String getPublishDate() {
        return Utils.reverseDate(publishDate);
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}

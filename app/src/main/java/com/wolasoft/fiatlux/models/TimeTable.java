package com.wolasoft.fiatlux.models;

import com.wolasoft.fiatlux.config.Utils;

/**
 * Created by kkoudo on 10/07/2016.
 */
public class TimeTable extends Common {
    private String eventDate;
    private String startTime;
    private String endTime;
    private String address;

    public TimeTable() {
    }

    public String getEventDate() {
        return Utils.reverseDate(eventDate);
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStartTime() {
        return startTime.substring(0,5);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime.substring(0,5);
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

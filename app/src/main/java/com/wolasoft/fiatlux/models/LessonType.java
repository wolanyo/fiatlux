package com.wolasoft.fiatlux.models;

/**
 * Created by osiris on 14/05/16.
 */
public class LessonType {
    private int id;
    private String label;
    private String description;

    public LessonType() {
    }

    public LessonType(int id, String libelle, String description) {
        this.id = id;
        this.label = libelle;
        this.description = description;
    }

    public LessonType(String libelle, String description) {
        this.label = libelle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.sjh.matzip.entities.data;

public class PlaceCategoryEntity {
    private int index;

    private String text;

    public int getIndex() {
        return index;
    }

    public PlaceCategoryEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getText() {
        return text;
    }

    public PlaceCategoryEntity setText(String text) {
        this.text = text;
        return this;
    }
}

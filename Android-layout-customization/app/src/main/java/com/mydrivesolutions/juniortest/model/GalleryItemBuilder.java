package com.mydrivesolutions.juniortest.model;

public class GalleryItemBuilder {
    private String title;
    private String url;

    public GalleryItemBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public GalleryItemBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public GalleryItem build() {
        return new GalleryItem(title, url);
    }
}
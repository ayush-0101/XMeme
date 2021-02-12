package com.crio.xmeme.dtos;

public class MemePostDTO {
    private String id;
    private String name;
    private String url;
    private String caption;

    public MemePostDTO(String id, String name, String url, String caption) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}

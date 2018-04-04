package com.example.hshakilst.jessoretourism;

/**
 * Created by hshakilst on 5/11/2017.
 */

public class Places {

    private int image;
    private String name;
    private String description;

    public Places(int image, String name, String desc){
        this.setImage(image);
        this.setName(name);
        this.setDescription(desc);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

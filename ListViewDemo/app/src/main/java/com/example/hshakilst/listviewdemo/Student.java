package com.example.hshakilst.listviewdemo;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by hshakilst on 5/6/2017.
 */

public class Student {
    private int imageResId;
    private String name;
    private String email;

    public Student(int image, String name, String email){
        this.imageResId = image;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}

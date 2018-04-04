package com.example.hshakilst.firebaseuploader;

import java.io.Serializable;

/**
 * Created by hshakilst on 8/2/2017.
 */

public class User implements Serializable{
    private String name;
    private String address;
    private String mobile;

    public User(String name, String address, String mobile){
        this.setName(name);
        this.setAddress(address);
        this.setMobile(mobile);
    }

    public User(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

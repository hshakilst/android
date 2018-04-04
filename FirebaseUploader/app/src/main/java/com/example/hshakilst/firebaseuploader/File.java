package com.example.hshakilst.firebaseuploader;

import java.text.DecimalFormat;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hshakilst on 8/3/2017.
 */

public class File implements Parcelable{
    private String fileName;
    private String fileUrl;
    private long fileSize;

    public File(String fileName, String fileUrl, long fileSize){
        this.setFileName(fileName);
        this.setFileUrl(fileUrl);
        this.setFileSize(fileSize);
    }

    public File(){}

    public File(Parcel in){
        this.fileName = in.readString();
        this.fileUrl = in.readString();
        this.fileSize = in.readLong();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        public File[] newArray(int size) {
            return new File[size];
        }
    };

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.fileName);
        parcel.writeString(this.fileUrl);
        parcel.writeLong(this.fileSize);
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long size) {
        this.fileSize = size;
    }

    public String calSise(){
        String res = null;
        float size = this.fileSize;
        if(size <=  999){
            res = size + " B";
        }
        else if (size >= 1000 && fileSize <= 99999){
            res = new DecimalFormat("##.##").format(size/1000) + " KB";
        }
        else if (size > 99999){
            res = new DecimalFormat("##.##").format(size/1000000) + " MB";
        }
        return res;
    }
}

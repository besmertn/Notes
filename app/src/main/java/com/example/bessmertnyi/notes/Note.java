package com.example.bessmertnyi.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;


public class Note implements Parcelable{
    private int id;
    private byte[] image;
    private CharSequence mainText;
    private CharSequence shortText;
    private CharSequence dateTime;
    private int status;

    Note(byte[] image, CharSequence mainText, CharSequence dateTime, int status) {
        this.image = image;
        this.status = status;
        this.dateTime = dateTime;
        this.mainText = mainText;
        String[] lines = mainText.toString().split("\r\n|\r|\n");
        if(mainText.length() > 40 || lines.length > 1) {
            if(lines.length > 1) {
                this.shortText = lines[0] + "\r\n" + lines[1] + "...";
            } else {
                this.shortText = mainText.subSequence(0, 40) + "...";
            }

        } else {
            shortText = mainText;
        }
    }

    CharSequence getMainText() {
        return mainText;
    }

    void setMainText(CharSequence mainText) {
        this.mainText = mainText;
        String[] lines = mainText.toString().split("\r\n|\r|\n");
        if(mainText.length() > 40 || lines.length > 2) {
            if(lines.length > 2) {
                this.shortText = lines[0] + "\r\n" + lines[1] + "...";
            } else {
                this.shortText = mainText.subSequence(0, 40) + "...";
            }

        } else {
            shortText = mainText;
        }

    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    CharSequence getShortText() {
        return shortText;
    }

    CharSequence getDateTime() {
        return dateTime;
    }

    void setDateTime(CharSequence dateTime) {
        this.dateTime = dateTime;
    }

    private Note(Parcel in) {
        String[] data = new String[4];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.image = data[0].getBytes();
        this.dateTime = data[1];
        this.mainText = data[2];
        this.status = Integer.parseInt(data[3]);
        this.id = Integer.parseInt(data[4]);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{Arrays.toString(this.image),
                this.dateTime.toString(),
                this.mainText.toString(),
                Integer.toString(this.status),
                Integer.toString(this.id)});
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    int getStatus() {
        return status;
    }

    void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

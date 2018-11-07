package com.example.bessmertnyi.notes;

import android.os.Parcel;
import android.os.Parcelable;


public class Note implements Parcelable{
    private byte[] image;
    private CharSequence mainText;
    private CharSequence shortText;
    private CharSequence dateTime;

    public Note(byte[] image, CharSequence mainText, CharSequence dateTime) {
        this.image = image;
        this.dateTime = dateTime;
        this.mainText = mainText;
        String[] lines = mainText.toString().split("\r\n|\r|\n");
        if(mainText.length() > 40 || lines.length > 2) {
            if(lines.length > 2) {
                String shortStr = lines[0] + "\r\n" + lines[1] + "...";
                this.shortText = shortStr;
            } else {
                this.shortText = mainText.subSequence(0, 40) + "...";
            }

        } else {
            shortText = mainText;
        }

    }



    public CharSequence getMainText() {
        return mainText;
    }

    public void setMainText(CharSequence mainText) {
        this.mainText = mainText;
        String[] lines = mainText.toString().split("\r\n|\r|\n");
        if(mainText.length() > 40 || lines.length > 2) {
            if(lines.length > 2) {
                String shortStr = lines[0] + "\r\n" + lines[1] + "...";
                this.shortText = shortStr;
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

    public CharSequence getShortText() {
        return shortText;
    }

    public CharSequence getDateTime() {
        return dateTime;
    }

    public void setDateTime(CharSequence dateTime) {
        this.dateTime = dateTime;
    }

    public Note(Parcel in){
        String[] data = new String[4];

        in.readStringArray(data);
        // the order needs to be the same as in writeToParcel() method
        this.image = data[0].getBytes();
        this.dateTime = data[1];
        this.mainText = data[2];
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.image.toString(),
                this.dateTime.toString(),
                this.mainText.toString()});
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
}

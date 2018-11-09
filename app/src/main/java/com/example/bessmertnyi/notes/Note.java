package com.example.bessmertnyi.notes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Note implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private long id;
    private byte[] image;
    private String mainText;
    @Ignore
    private String shortText;
    private String dateTime;

    public Note(byte[] image, String mainText, String dateTime) {
        this.image = image;
        System.out.println(image);
        this.dateTime = dateTime;
        this.mainText = mainText;
        String[] lines = mainText.split("\r\n|\r|\n");
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


    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
        String[] lines = mainText.split("\r\n|\r|\n");
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

    public String getShortText() {
        return shortText;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
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
                this.dateTime,
                this.mainText});
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

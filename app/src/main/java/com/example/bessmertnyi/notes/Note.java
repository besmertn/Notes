package com.example.bessmertnyi.notes;

import android.graphics.drawable.Drawable;


public class Note {
    private Drawable image;
    private CharSequence mainText;
    private CharSequence shortText;
    private CharSequence dateTime;

    public Note(Drawable image, CharSequence mainText, CharSequence dateTime) {
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
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
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
}

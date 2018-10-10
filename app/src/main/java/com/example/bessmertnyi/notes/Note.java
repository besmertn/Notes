package com.example.bessmertnyi.notes;

import android.graphics.drawable.Drawable;

public class Note {
    private Drawable image;
    private CharSequence mainText;

    public Note(Drawable image, CharSequence mainText) {
        this.image = image;
        this.mainText = mainText;
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
}

package com.example.bessmertnyi.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notesManager";
    private static final String TABLE_NOTES = "notes";
    private static final String KEY_ID = "id";
    private static final String KEY_MAIN_TEXT = "main_text";
    private static final String KEY_STATUS = "status";
    private static final String KEY_DATETIME = "datetime";
    private static final String KEY_IMAGE = "image";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MAIN_TEXT + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void insertNote(Note note) {

    }

    @Override
    public void updateNote(Note note) {

    }

    @Override
    public void deleteNote(Note note) {

    }

    @Override
    public List<Note> getNotes() {
        return null;
    }
}

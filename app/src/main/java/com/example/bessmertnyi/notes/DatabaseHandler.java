package com.example.bessmertnyi.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
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

    DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MAIN_TEXT + " TEXT,"
                + KEY_STATUS + " INTEGER," + KEY_DATETIME + " TEXT," + KEY_IMAGE + " BLOB " + ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);

        onCreate(db);
    }

    @Override
    public void insertNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAIN_TEXT, note.getMainText().toString());
        values.put(KEY_STATUS, note.getStatus());
        values.put(KEY_DATETIME, note.getDateTime().toString());
        values.put(KEY_IMAGE, note.getImage());


        db.insert(TABLE_NOTES, null, values);
        db.close();
    }

    @Override
    public int updateNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MAIN_TEXT, note.getMainText().toString());
        values.put(KEY_STATUS, note.getStatus());
        values.put(KEY_DATETIME, note.getDateTime().toString());
        values.put(KEY_IMAGE, note.getImage());

        return db.update(TABLE_NOTES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    @Override
    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?", new String[]{String.valueOf(note.getId())});
        db.close();
    }

    @Override
    public List<Note> getNotes() {
        List<Note> noteList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setMainText(cursor.getString(1));
                note.setStatus(Integer.parseInt(cursor.getString(2)));
                note.setDateTime(cursor.getString(3));
                note.setImage(cursor.getBlob(4));
                noteList.add(note);
            } while (cursor.moveToNext());
        }

        return noteList;
    }
}

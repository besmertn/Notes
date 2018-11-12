package com.example.bessmertnyi.notes;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Note.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}

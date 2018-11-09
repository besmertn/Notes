package com.example.bessmertnyi.notes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note")
    Flowable<List<Note>> getAll();

    @Query("SELECT * FROM note WHERE id = :id")
    Single<Note> getById(long id);

    @Insert
    Single<Integer> insert(Note note);

    @Update
    Single<Integer> update(Note note);

    @Delete
    Single<Integer> delete(Note note);
}

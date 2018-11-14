package com.example.bessmertnyi.notes;

import java.util.List;

public interface IDatabaseHandler {
    void insertNote(Note note);

    int updateNote(Note note);

    void deleteNote(Note note);

    List<Note> getNotes();
}

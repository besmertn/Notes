package com.example.bessmertnyi.notes;

import java.util.List;

public interface IDatabaseHandler {
    void insertNote(Note note);

    void updateNote(Note note);

    void deleteNote(Note note);

    List<Note> getNotes();
}

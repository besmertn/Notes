package com.example.bessmertnyi.notes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int CREATE_NEW_NOTE = 1;
    private static final int EDIT_NOTE = 2;
    private int selectedNotePosition;
    private NoteAdapter notesAdapter;
    private Spinner spinner;
    /*private AppDatabase db;
    private NoteDao noteDao;*/

    @Override
    protected void onStart() {
        super.onStart();


    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView notesRecyclerView = findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(mLayoutManager);

        OnNoteClickListener listener = new OnNoteClickListener() {
            @Override
            public void onClick(View view, int position) {
                selectedNotePosition = position;
                Intent intent = new Intent(MainActivity.this,
                        NoteCreationActivity.class);
                intent.putExtra("mainText", notesAdapter.getNote(position).getMainText());
                intent.putExtra("dateTime", notesAdapter.getNote(position).getDateTime());
                intent.putExtra("status", notesAdapter.getNote(position).getStatus());
                startActivityForResult(intent, EDIT_NOTE);
            }
        };

        notesAdapter = new NoteAdapter(listener, this, App.getInstance().getDatabase().noteDao());
        notesRecyclerView.setAdapter(notesAdapter);

        spinner = findViewById(R.id.statusFilterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_filter_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);


        //loadNotes();
        App.getInstance().getDatabase().noteDao().getAll()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Note>>() {
                    @Override
                    public void accept(List<Note> notes) {
                        notesAdapter.setNotesList(notes);
                    }
                });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("notes", new ArrayList<>(notesAdapter.getNotes()));
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Collection<Note> notes = savedInstanceState.getParcelableArrayList("notes");
        notesAdapter.setNotesList(notes);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, NoteCreationActivity.class);
                startActivityForResult(intent, CREATE_NEW_NOTE);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == CREATE_NEW_NOTE) {
            if(data == null) {return;}
            String imagePath = data.getStringExtra("image");
            byte[] imageAsBytes = new byte[1];
            if (imagePath != null) {
                Bitmap bmp = BitmapFactory.decodeFile(imagePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
                imageAsBytes = stream.toByteArray();
            }

            Note note = new Note(imageAsBytes,
                    data.getStringExtra("mainText"),
                    data.getStringExtra("dateTime"),
                    data.getIntExtra("status", 0));
            notesAdapter.setItems(note);
            notesAdapter.filterNotes(spinner.getSelectedItemPosition() - 1);
        }
        if (resultCode == Activity.RESULT_OK && requestCode == EDIT_NOTE) {
            if(data == null) {return;}
            final Note selectedNote = notesAdapter.getNote(selectedNotePosition);
            selectedNote.setMainText(data.getStringExtra("mainText"));
            selectedNote.setDateTime(data.getStringExtra("dateTime"));
            selectedNote.setStatus(data.getIntExtra("status", 0));
            String imagePath = data.getStringExtra("image");
            if (imagePath != null) {
                Bitmap bmp = BitmapFactory.decodeFile(imagePath);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byte[] imageAsBytes = stream.toByteArray();
                selectedNote.setImage(imageAsBytes);
            }

            Callable<Void> clb = new Callable<Void>() {
                @Override
                public Void call() {
                    App.getInstance().getDatabase().noteDao().update(selectedNote);
                    return null;
                }
            };
            Completable.fromCallable(clb)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();

            notesAdapter.notifyDataSetChanged();
        }
        if (resultCode == Activity.RESULT_CANCELED && requestCode == EDIT_NOTE) {
            if(data == null) {return;}
            Note selectedNote = notesAdapter.getNote(selectedNotePosition);
            notesAdapter.deleteItem(selectedNote);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        notesAdapter.filterNotes(position - 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}



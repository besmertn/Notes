package com.example.bessmertnyi.notes;

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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int CREATE_NEW_NOTE = 1;
    private static final int EDIT_NOTE = 2;
    private int selectedNotePosition;
    private NoteAdapter notesAdapter;
    private Spinner spinner;
    private DatabaseHandler dbHandler;

    @Override
    protected void onDestroy() {
        dbHandler.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DatabaseHandler(this);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        RecyclerView notesRecyclerView = findViewById(R.id.my_recycler_view);


        // use a linear layout manager
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

        notesAdapter = new NoteAdapter(listener, this, dbHandler);
        notesRecyclerView.setAdapter(notesAdapter);

        spinner = findViewById(R.id.statusFilterSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_filter_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        notesAdapter.clearAll();

        notesAdapter.setItems(dbHandler.getNotes());
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
        //super.onActivityResult(requestCode, resultCode, data);
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
            Note selectedNote = notesAdapter.getNote(selectedNotePosition);
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

            dbHandler.updateNote(selectedNote);
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



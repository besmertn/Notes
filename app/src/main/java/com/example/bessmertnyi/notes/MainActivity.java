package com.example.bessmertnyi.notes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private RecyclerView notesRecyclerView;
    private NoteAdapter notesAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        notesRecyclerView = findViewById(R.id.my_recycler_view);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(mLayoutManager);

        OnNoteClickListener listener = new OnNoteClickListener() {
            @Override
            public void onClick(View view, int position) {
                CharSequence text = "Position !" + position;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        };

        notesAdapter = new NoteAdapter(listener);
        notesRecyclerView.setAdapter(notesAdapter);

        loadNotes();
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
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void loadNotes() {
        Collection<Note> notes = getNotes();
        notesAdapter.setItems(notes);
    }

    private Collection<Note> getNotes(){
        return Arrays.asList(
                new Note(this.getDrawable(android.R.drawable.ic_secure), "Note number1"),
                new Note(this.getDrawable(android.R.drawable.ic_secure), "Note number2"),
                new Note(this.getDrawable(android.R.drawable.ic_secure), "Note number3")
        );
    }

}



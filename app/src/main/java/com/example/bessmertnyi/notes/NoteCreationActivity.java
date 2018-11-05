package com.example.bessmertnyi.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class NoteCreationActivity extends AppCompatActivity {

    TextView currentDateTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        final ImageButton button = findViewById(R.id.backImageButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText mainEditText = findViewById(R.id.mainTextEditText);
                intent.putExtra("mainText", mainEditText.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        currentDateTimeEditText =  findViewById(R.id.currentDaeTimeTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        currentDateTimeEditText.setText(currentDateTimeString);
    }
}

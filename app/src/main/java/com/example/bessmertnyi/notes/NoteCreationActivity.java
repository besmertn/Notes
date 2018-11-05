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

    TextView currentDateTimeTextView;
    EditText mainTextEditText;
    private  String dateTime;
    private  String mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);

        dateTime = this.getIntent().getStringExtra("dateTime");
        mainText = this.getIntent().getStringExtra("mainText");

        final ImageButton button = findViewById(R.id.backImageButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText mainEditText = findViewById(R.id.mainTextEditText);
                intent.putExtra("mainText", mainEditText.getText().toString());
                if((dateTime != null && !mainText.equals(mainTextEditText.getText().toString())) || dateTime == null) {
                    intent.putExtra("dateTime", currentDateTimeTextView.getText().toString());
                } else {
                    intent.putExtra("dateTime", dateTime);
                }

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        currentDateTimeTextView =  findViewById(R.id.currentDaeTimeTextView);
        mainTextEditText = findViewById(R.id.mainTextEditText);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        if(dateTime == null) {
            currentDateTimeTextView.setText(currentDateTimeString);
        } else {
            currentDateTimeTextView.setText(dateTime);
            mainTextEditText.setText(mainText);
        }

    }
}

package com.example.bessmertnyi.notes;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class NoteCreationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    TextView currentDateTimeTextView;
    EditText mainTextEditText;
    private  String dateTime;
    private  String mainText;
    private byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_light);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = stream.toByteArray();

        dateTime = this.getIntent().getStringExtra("dateTime");
        mainText = this.getIntent().getStringExtra("mainText");

        final ImageButton backButton = findViewById(R.id.backImageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText mainEditText = findViewById(R.id.mainTextEditText);
                intent.putExtra("mainText", mainEditText.getText().toString());
                intent.putExtra("image", image);
                if((dateTime != null && !mainText.equals(mainTextEditText.getText().toString())) || dateTime == null) {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    intent.putExtra("dateTime", currentDateTimeString);
                    System.out.println("+++++++++");
                } else {
                    intent.putExtra("dateTime", dateTime);
                }
                if(mainEditText.getText().toString().equals("")) {
                    setResult(RESULT_CANCELED, intent);
                } else {
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        final ImageButton deleteButton = findViewById(R.id.deleteImageButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        final ImageButton selectImageButton = findViewById(R.id.selectImageImageButton);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data == null) {
                return;
            }
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                image = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

package com.example.bessmertnyi.notes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;

public class NoteCreationActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    TextView currentDateTimeTextView;
    EditText mainTextEditText;
    private  String dateTime;
    private  String mainText;
    private Boolean imageSelectFlag = false;
    private Uri imgUri;

    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_creation);
        imageSelectFlag = false;

        dateTime = this.getIntent().getStringExtra("dateTime");
        mainText = this.getIntent().getStringExtra("mainText");


        final ImageButton backButton = findViewById(R.id.backImageButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                EditText mainEditText = findViewById(R.id.mainTextEditText);
                intent.putExtra("mainText", mainEditText.getText().toString());
                if((dateTime != null && !mainText.equals(mainTextEditText.getText().toString())) || dateTime == null) {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    intent.putExtra("dateTime", currentDateTimeString);
                } else {
                    intent.putExtra("dateTime", dateTime);
                }
                if (imageSelectFlag) {
                    image = Bitmap.createScaledBitmap(image, 120, 160, true);
                    String filePath = tempFileImage(NoteCreationActivity.this, image, "name");
                    intent.putExtra("image", filePath);
                    intent.putExtra("imgUri", imgUri);
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

                Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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
                //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
                //image = stream.toByteArray();
                imageSelectFlag = true;
                image = bitmap;
                imgUri = imageUri;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String tempFileImage(Context context, Bitmap bitmap, String name) {

        File outputDir = context.getCacheDir();
        File imageFile = new File(outputDir, name + ".png");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(context.getClass().getSimpleName(), "Error writing file", e);
        }

        return imageFile.getAbsolutePath();
    }
}

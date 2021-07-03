package com.example.speechtotext;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int REQUEST_CODE = 1;
    static int FLAG = 0;
    Button button;
    SpeechRecognizer speechRecognizer;
    private TextInputEditText editText;
    private CardView cardView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.textInputEditText);
        cardView = findViewById(R.id.card_view);
        imageView = findViewById(R.id.image);
        button = findViewById(R.id.clear_text);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(null);
            }
        });


        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FLAG == 0) {
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_24));
                    speechRecognizer.startListening(speechRecognizerIntent);
                    FLAG = 1;
                } else {
                    imageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_off_24));
                    speechRecognizer.stopListening();
                    FLAG = 0;

                }
            }
        });

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onBeginningOfSpeech() {
                cardView.setScaleX(1.1f);
                cardView.setScaleY(1.1f);
                imageView.setScaleX(1.1f);
                imageView.setScaleY(1.1f);
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onResults(Bundle results) {
                cardView.setScaleX(1f);
                cardView.setScaleY(1f);
                imageView.setScaleX(1f);
                imageView.setScaleY(1f);
                imageView.setImageDrawable(getDrawable(R.drawable.ic_baseline_mic_off_24));
                FLAG = 0;

                ArrayList<String> data = results.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);

                editText.setText(data.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
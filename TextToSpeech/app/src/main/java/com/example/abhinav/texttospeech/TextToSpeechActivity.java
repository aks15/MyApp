package com.example.abhinav.texttospeech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private Button btnSpeech;
    private ImageButton btnSpeak;
    private EditText etText;
    private TextView tvOutput;
    private final int SPEECH_REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        tts = new TextToSpeech(getBaseContext(), this);
        btnSpeech=(Button)findViewById(R.id.btnSpeech);
        etText=(EditText)findViewById(R.id.etText);
        tvOutput=(TextView)findViewById(R.id.tvOutput);
        btnSpeak=(ImageButton)findViewById(R.id.btnSpeak);


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btnSpeech:
                speakOut();
                break;
            case R.id.btnSpeak:
                showGoogleInputDialog();
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                btnSpeak.setEnabled(true);
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    private void speakOut() {
        String text = etText.getText().toString();
        if (text.endsWith("qualification?")){
            tts.speak("Engineering", TextToSpeech.QUEUE_FLUSH, null);
        }else if (text.endsWith("hobbies?")){
            tts.speak("Cooking, Travelling", TextToSpeech.QUEUE_FLUSH, null);
        }else if (text.endsWith("skills?")){
            tts.speak("Java, Android", TextToSpeech.QUEUE_FLUSH, null);
        }else {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public void showGoogleInputDialog() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), "Your device is not supported!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SPEECH_REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0).endsWith("name")){
                        tvOutput.setText("Abhinav Kumar");
                    }else if (result.get(0).endsWith("qualification")){
                        tvOutput.setText("Engineering");
                    }else if (result.get(0).endsWith("hobbies")){
                        tvOutput.setText("Watching Cricket");
                    }else if (result.get(0).endsWith("skills")){
                        tvOutput.setText("Java, Android");
                    }else {
                        tvOutput.setText(result.get(0));
                    }
                }
                break;
            }
        }
    }
}


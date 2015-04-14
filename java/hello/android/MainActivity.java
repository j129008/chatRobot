package hello.android;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {
    TextToSpeech mTTS;
    private static final int RQS_VOICE_RECOGNITION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTTS.setLanguage(Locale.getDefault());
                }
            }
        });
        Button buttonSpeech = (Button) findViewById(R.id.Speech);
        buttonSpeech.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "請跟機器人說話");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "zh_CN");
                try {
                    startActivityForResult(intent, RQS_VOICE_RECOGNITION);
                }catch (ActivityNotFoundException a) {
                    mTTS.speak("I feel uncomfortable", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RQS_VOICE_RECOGNITION) {
            String react = "";
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String userTalk = result.get(0);
                if(userTalk.contains("how")){
                    react += "I am fine, thank you";
                    mTTS.speak(react, TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    mTTS.speak(react = "do you say "+userTalk+"?", TextToSpeech.QUEUE_FLUSH, null);
                }

            }else{
                mTTS.speak("I can't understand you", TextToSpeech.QUEUE_FLUSH, null);
            }
            Log.d("j129008",react);
        }
    }

}

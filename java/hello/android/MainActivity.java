package hello.android;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
    TextView textResult;
    String setting = "設定";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    mTTS.setLanguage(Locale.ENGLISH);
                }
            }
        });
        Button buttonSpeech = (Button) findViewById(R.id.Speech);
        textResult = (TextView) findViewById(R.id.Result);
        buttonSpeech.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "請跟機器人說話");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                try {
                    startActivityForResult(intent, RQS_VOICE_RECOGNITION);
                    mTTS.speak("hello my master", TextToSpeech.QUEUE_FLUSH, null);
                }catch (ActivityNotFoundException a) {
                    mTTS.speak("I feel uncomfortable", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RQS_VOICE_RECOGNITION) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String userTalk = result.get(0);
                String react = "";
                if(userTalk.contains("how")){
                    react += "I am fine, thank you";
                    mTTS.speak(react, TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    mTTS.speak("do you say"+userTalk+"?", TextToSpeech.QUEUE_FLUSH, null);
                }
            }else{
                mTTS.speak("I can't hear you", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Setting button pressed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        mTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);

    }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }

}

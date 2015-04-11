package hello.android;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
// import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    // ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // image = (ImageView) findViewById(R.id.imageView1);

    }


    private void openSearch() {
        Toast.makeText(this, "Search button pressed", Toast.LENGTH_SHORT).show();
    }
    private void openSettings(){
        Toast.makeText(this, "Setting button pressed", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        if(message.equals("vc") ){
            intent.putExtra(EXTRA_MESSAGE, "Guitar God Jimi Hendrix!");
        }else {
            intent.putExtra(EXTRA_MESSAGE, message);
        }
        startActivity(intent);
    }


   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.menu_main, menu);
      return true;
   }



}

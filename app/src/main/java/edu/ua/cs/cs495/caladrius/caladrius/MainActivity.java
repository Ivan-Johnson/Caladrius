package edu.ua.cs.cs495.caladrius.caladrius;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.foo, menu);
        return true; // i.e, yes, we have a menu
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String text;
        switch (item.getItemId()) {
            case R.id.battery:
                text = "battery";
                break;
            case R.id.delete:
                text = "delete";
                break;
            case R.id.add:
                text = "add";
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        Log.i("sometag", "You clicked " + text);
        return true; // i.e. yes, we used the event
    }
}

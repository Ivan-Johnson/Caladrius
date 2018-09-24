package edu.ua.cs.cs495.caladrius.caladrius;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent nextScreen = new Intent (MainActivity.this, LoginScreen.class);
        this.startActivity(nextScreen);
    }
}

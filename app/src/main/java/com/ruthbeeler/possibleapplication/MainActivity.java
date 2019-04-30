package com.ruthbeeler.possibleapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayToast(View view) {
        Toast.makeText(getApplicationContext(),"Hello World",Toast.LENGTH_SHORT).show();
    }

    public void getMovieList(View view) {
        Intent intent = new Intent(this, listMoviesActivity.class);
        startActivity(intent);
    }
}

package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnclickAddPark() {
        //to open new activity from current to next
        Intent i = new Intent(MainActivity.this, AddPark.class);
        startActivity(i);
        //to close current activity
        finish();
    }
}
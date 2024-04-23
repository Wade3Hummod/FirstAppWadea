package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.om.FabricatedOverlay;
import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton fabAddPark;
    public SearchView srhV;
    public ListView lstvParks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAddPark =findViewById(R.id.fabAddPark);
        srhV =findViewById(R.id.srhV);
        lstvParks =findViewById(R.id.lstvParks);


    }

    public void OnclickAddPark() {
        //to open new activity from current to next
        Intent i = new Intent(MainActivity.this, AddPark.class);
        startActivity(i);
        //to close current activity
        finish();
    }
}
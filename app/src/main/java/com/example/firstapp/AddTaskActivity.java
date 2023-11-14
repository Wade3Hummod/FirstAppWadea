package com.example.firstapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class AddTaskActivity extends AppCompatActivity
{
    private Button btnSave;
    private Button btnCancel;
    private TextView tvImportance;
    private SeekBar skbrImportance;
    private TextInputEditText etEmail;
    private TextInputEditText etText;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);
        tvImportance=findViewById(R.id.tvImportance);
        skbrImportance=findViewById(R.id.skbrImportance);
        etEmail=findViewById(R.id.etEmail);
        etText=findViewById(R.id.etText);
    }
}
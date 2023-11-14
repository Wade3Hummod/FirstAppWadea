package com.example.firstapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class EditTaskActivity extends AppCompatActivity
{
    private Button btnUpdate;
    private Button btnCancel;
    private TextView tvImportance;
    private SeekBar skbrImportance;
    private TextInputEditText etShortTitle;
    private TextInputEditText etText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        btnUpdate=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);
        tvImportance=findViewById(R.id.tvImportance);
        skbrImportance=findViewById(R.id.skbrImportance);
        etShortTitle=findViewById(R.id.etShortTitle);
        etText=findViewById(R.id.etText);
    }

}
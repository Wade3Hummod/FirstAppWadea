package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class  SignUpActivity extends AppCompatActivity
{
    private Button btnSave;
    private Button btnCancel;
    private TextInputEditText etName;
    private TextInputEditText etPhone;
    private TextInputEditText etEmail;
    private TextInputEditText etText;
    private TextInputEditText etRe_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSave=findViewById(R.id.btnSave);
        btnCancel=findViewById(R.id.btnCancel);
        etName=findViewById(R.id.etName);
        etPhone=findViewById(R.id.etPhone);
        etEmail=findViewById(R.id.etEmail);
        etText=findViewById(R.id.etText);
        etRe_password=findViewById(R.id.etRe_password);

        public void onclickCancel(View v)
        {
            finish();

        }
    }
}
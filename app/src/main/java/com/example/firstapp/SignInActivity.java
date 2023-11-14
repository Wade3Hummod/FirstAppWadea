package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity
{


    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private Button btnSignIn1;
    private Button btnSingUp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etPassword=findViewById(R.id.etPassword);
        etEmail=findViewById(R.id.etEmail);
        btnSingUp2=findViewById(R.id.btnSingUp2);
        btnSignIn1=findViewById(R.id.btnSignIn1);
    }
    public void onclickSignUp(View v)
    {

        Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(i);
    }

}
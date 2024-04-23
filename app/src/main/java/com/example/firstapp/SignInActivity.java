package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firstapp.data.AppDatabase;
import com.example.firstapp.data.usersTable.MyUser;
import com.example.firstapp.data.usersTable.MyUserQuery;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {


    private TextInputEditText etEmail;//الايميل الشخصي
    private TextInputEditText etPassword;//الرقم السري
    private Button btnSignIn1;
    private Button btnSingUp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSingUp2 = findViewById(R.id.btnSingUp2);
        btnSignIn1 = findViewById(R.id.btnSignIn1);
    }

    public void onclickSignUp(View v) {

        Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    public void onclicksignIn(View v) {

        checkEmailPassw_FB();
    }

    private void checkEmailPassw() {
        boolean isAllok = true;
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        if (email.length() < 6 || email.contains("@") == false) {
            isAllok = false;
            etEmail.setError("Wrong Email");
        }
        if (password.length() < 8 || password.contains(" ") == true) {
            isAllok = false;
            etPassword.setError("Wrong password");
        }
        if (isAllok) {
            Toast.makeText(this, "ALL OK", Toast.LENGTH_SHORT).show();
        }
        if (isAllok) {
            Toast.makeText(this, "All Ok", Toast.LENGTH_SHORT).show();
            //بناء قاعدة بيانات وارجاع مؤشر عليها1
            AppDatabase db = AppDatabase.getDB(getApplicationContext());
            //مؤشر لكائن عمليات الجدول2
            MyUserQuery userQuery = db.getMyUserQuery();
            // 3.استدعاء العملية التي تنفذ الاستعلام الذي يفحص البريد وكلمة السر ويعيد كائنا ان كان موجود او ان لم يكن موجود null
            MyUser myUser = userQuery.checkEmailPassw(email, password);
            if (myUser == null)//هل يوجد كائن حسب الايميل والباسورد
                Toast.makeText(this, "Wrong Email Or Password", Toast.LENGTH_SHORT).show();
            else {//ان كان هنالك حساب الايميل والباسورد ننتقل الى الشاشة الرئيسية
                Intent i = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        }
    }
         private void checkEmailPassw_FB() {
            boolean isAllok = true;
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            if (email.length() < 6 || email.contains("@") == false) {
                isAllok = false;
                etEmail.setError("Wrong Email");
            }
            if (password.length() < 8 || password.contains(" ") == true) {
                isAllok = false;
                etPassword.setError("Wrong password");
            }
            if (isAllok) {
                Toast.makeText(this, "ALL OK", Toast.LENGTH_SHORT).show();
            }
            if (isAllok) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignInActivity.this, "Signing in", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignInActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(SignInActivity.this, "Signing in failed", Toast.LENGTH_SHORT).show();
                            etEmail.setError(task.getException().getMessage());
                        }


                    }
                });
            }
        }
    }



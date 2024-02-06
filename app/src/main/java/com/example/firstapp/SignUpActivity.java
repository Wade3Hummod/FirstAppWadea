package com.example.firstapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.data.AppDatabase;
import com.example.firstapp.data.usersTable.MyUser;
import com.example.firstapp.data.usersTable.MyUserQuery;
import com.google.android.material.textfield.TextInputEditText;

public class  SignUpActivity extends AppCompatActivity
{
    private Button btnSave;
    private Button btnCancel;
    private TextInputEditText etName;
    private TextInputEditText etPhone;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private TextInputEditText etRe_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRe_password = findViewById(R.id.etRe_password);
    }

            public void onClickSave(View view) {checkSignUpSave();}
    private void checkSignUpSave_FB() {
        boolean isAllok = true; //يفحص الحقول ان كانت سليمة
        //استخراج النص من حقل الايميل
        String email = etEmail.getText().toString();
        //استخراج نص كلمه المرور
        String password = etPassword.getText().toString();
        //استخراج نص اعادة كلمه المرور
        String repassword = etRe_password.getText().toString();
        // استخراج نص من رقم هاتف
        String phone = etPhone.getText().toString();
        //استخراج نص لأسمك
        String name = etName.getText().toString();

        //فحص الايمل ان كان طوله اقل من 6 او لا يحوي @ فهو خطأ
        if (email.length() < 6 || email.contains("@") == false) {
            //تعديل المتغير ليدل على ان الفحص يهطي نتيجه خاطئه
            isAllok = false;
            // عرض ملاحظه خطا على الشاشه داخل حقل البريد
            etEmail.setError("Wrong Email");

        }
        if (password.length() < 8 || password.length() > 20 || password.contains(" ") == true)
        {
            isAllok = false;
            etPassword.setError("Password between 8 - 20 letters");
        }
        if (!repassword.equals(password))
        {
            isAllok = false;
            etRe_password.setError("should be the same password");
        }


        if (phone.length() <10 || phone.contains(" ") == true)
        {
            isAllok = false;
            etPhone.setError("phone number is not 10  numbers");

        }


        if(isAllok)
        {
            //
            FireBaseAuth auth=fireBaseAuth.getInstance();

        }
    }

            private void checkSignUpSave() {
                boolean isAllok = true; //يفحص الحقول ان كانت سليمة
                //استخراج النص من حقل الايميل
                String email = etEmail.getText().toString();
                //استخراج نص كلمه المرور
                String password = etPassword.getText().toString();
                //استخراج نص اعادة كلمه المرور
                String repassword = etRe_password.getText().toString();
                // استخراج نص من رقم هاتف
                String phone = etPhone.getText().toString();
                //استخراج نص لأسمك
                String name = etName.getText().toString();

                //فحص الايمل ان كان طوله اقل من 6 او لا يحوي @ فهو خطأ
                if (email.length() < 6 || email.contains("@") == false) {
                    //تعديل المتغير ليدل على ان الفحص يهطي نتيجه خاطئه
                    isAllok = false;
                    // عرض ملاحظه خطا على الشاشه داخل حقل البريد
                    etEmail.setError("Wrong Email");

                }
                if (password.length() < 8 || password.length() > 20 || password.contains(" ") == true)
                {
                    isAllok = false;
                    etPassword.setError("Password between 8 - 20 letters");
                }
                if (!repassword.equals(password))
                {
                    isAllok = false;
                    etRe_password.setError("should be the same password");
                }


                if (phone.length() <10 || phone.contains(" ") == true)
                {
                    isAllok = false;
                    etPhone.setError("phone number is not 10  numbers");

                }


                if (isAllok) {
                    Toast.makeText(this, "All Ok", Toast.LENGTH_SHORT).show();
                    AppDatabase db = AppDatabase.getDB(getApplicationContext());
                    MyUserQuery usersQuery = db.getMyUserQuery();
                    //فحص هل البريد الالكتروني موجود من قبل اي تم تسجيل من قبل
                    if (usersQuery.checkEmail(email) != null) {
                        etEmail.setError("found Email");
                    } else// ان لم يكن موجودا نقوم ببناء كاءن للمستعمل وادخاله في الجدول Myuser المستعملين
                    {
                        // بناء الكائن
                        MyUser myUser = new MyUser();
                        //تحديد قيم الصفات بالقيم التي استخرجناها
                        myUser.email = email;
                        myUser.fullName = name;
                        myUser.phone = phone;
                        myUser.passw = password;
                        //اضافه الكائن الجديد للجدول
                        usersQuery.insert(myUser);
                        //اغلاق الشاشه الحالية
                        finish();


                    }
                }
            }
    public void onclickCancel(View v){

        finish();

    }


        }






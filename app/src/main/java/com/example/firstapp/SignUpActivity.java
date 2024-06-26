package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstapp.data.AppDatabase;
import com.example.firstapp.data.usersTable.MyUser;
import com.example.firstapp.data.usersTable.MyUserQuery;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;

public class  SignUpActivity extends AppCompatActivity {
    private Button btnSave;
    private Button btnCancel;
    private TextInputEditText etName;//الاسم الشخصي
    private TextInputEditText etPhone;//رقم الهاتف الشخصي
    private TextInputEditText etEmail;//الايميل الشخصي
    private TextInputEditText etPassword;//الرقم السري
    private TextInputEditText etRe_password;//تاكيد الرقم السري


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

    public void onClickSave(View view)
    {
        checkSignUpSave_FB();

    }
    private void saveUser_FB(String email,String name,String phone, String passw)
    {
        //مؤشر لقاعدة البيانات
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل"دوكيومنت"
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        //بناء الكائن الذي سيتم حفظه
        MyUser user=new MyUser();
        user.setEmail(email);
        user.setFullName(name);
        user.setPhone(phone);
        user.setPassw(passw);
        user.setId(uid);
        //اضافة كائن "لمجموعة" المستعملين ومعالج حدث لفحص نجاح المطلوب
        // معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyUsers").document(uid).set(user).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            //دالة معالج الحدث
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                //هل تم تنفيذ المطلوب بنجاح
                if (task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Succeed to add User", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(SignUpActivity.this,SignInActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"Failed to add User",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkSignUpSave_FB()
    {
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
        if (password.length() < 8 || password.length() > 20 || password.contains(" ") == true) {
            isAllok = false;
            etPassword.setError("Password between 8 - 20 letters");
        }
        if (!repassword.equals(password)) {
            isAllok = false;
            etRe_password.setError("should be the same password");
        }


        if (phone.length() < 10 || phone.contains(" ") == true) {
            isAllok = false;
            etPhone.setError("phone number is not 10  numbers");

        }


        if (isAllok) {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Signing up Succeeded", Toast.LENGTH_SHORT).show();
                        saveUser_FB(email,name,phone,password);

                    } else {
                        Toast.makeText(SignUpActivity.this, "Signing up Failed", Toast.LENGTH_SHORT).show();
                        etEmail.setError(task.getException().getMessage());
                    }

                }


            });

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
        if (password.length() < 8 || password.length() > 20 || password.contains(" ") == true) {
            isAllok = false;
            etPassword.setError("Password between 8 - 20 letters");
        }
        if (!repassword.equals(password)) {
            isAllok = false;
            etRe_password.setError("should be the same password");
        }


        if (phone.length() < 10 || phone.contains(" ") == true) {
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

    public void onclickCancel(View v) {

        finish();
    }
}





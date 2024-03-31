package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.data.parksTable.Park;
import com.example.firstapp.data.usersTable.MyUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddPark extends AppCompatActivity
{
    private TextInputEditText etSteet;
    private TextInputEditText etCity;
    private TextInputEditText etNumber;
    private Button btnSave;
    private ImageButton ibtnGps;
    private TextView tvgpslocatoin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_park);
        etSteet = findViewById(R.id.etSteet);
        etCity = findViewById(R.id.etCity);
        etNumber = findViewById(R.id.etNumber);
        btnSave = findViewById(R.id.btnSave);
        ibtnGps = findViewById(R.id.ibtnGps);
        tvgpslocatoin = findViewById(R.id.tvgpslocatoin);


    }
    public void savepark_FB(String street, String City, int number, double lat, double lng )
    {
        //قاعدة البيانات
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل"دوكيومينت
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        //بناء الكائن الذي سيتم حفظه
        Park park=new Park();
        park.setStreet(street);
        park.setCity(City);
        park.setNumber(number);
        park.setLat(lat);
        park.setLng(lng);
        park.setUserId(uid);
        String parkId = db.collection("MyParks").document().getId();
        park.setParkId(parkId);

        //اضافة كائن "لمجموعة"المستعملين ومعالج حدث لفحص نجاح المطلوب
        //معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyParks").document(uid).set(park).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            //دالة معالج الحدث
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                //هل تم تنفيذ المطلوب بنجاح
                if(task.isSuccessful()){
                    Toast.makeText(AddPark.this, "Succeeded to add User", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddPark.this, "Failed to add User", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}
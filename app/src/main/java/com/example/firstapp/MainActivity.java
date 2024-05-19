package com.example.firstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.content.om.FabricatedOverlay;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.firstapp.data.parksTable.MyParkAdapter;
import com.example.firstapp.data.parksTable.Park;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton fabAddPark;
    public SearchView srhV;
    public ListView lstvParks;

    private MyParkAdapter parkAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fabAddPark =findViewById(R.id.fabAddPark);
        srhV =findViewById(R.id.srhV);

        lstvParks =findViewById(R.id.lstvParks);
        parkAdapter=new MyParkAdapter(this,R.layout.park_item_layout);
        lstvParks.setAdapter(parkAdapter);
        fabAddPark.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent i = new Intent(MainActivity.this, AddPark.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        readParksFrom_FB();
    }

    /**
     *  קריאת נתונים ממסד הנתונים firestore
     * @return .... רשימת הנתונים שנקראה ממסד הנתונים
     */
    public void readParksFrom_FB()
    {
        //בניית רשימה ריקה
        ArrayList<Park> arrayList =new ArrayList<>();
        //קבלת הפנייה למסד הנתונים
        FirebaseFirestore ffRef = FirebaseFirestore.getInstance();
        //קישור לקבוצה collection שרוצים לקרוא
        ffRef.collection("MyParks").
                //הוספת מאזין לקריאת הנתונים
                        get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    /**
                     * תגובה לאירוע השלמת קריאת הנתונים
                     * @param task הנתונים שהתקבלו מענן מסד הנתונים
                     */
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {// אם בקשת הנתונים התקבלה בהצלחה
                            //מעבר על כל ה״מסמכים״= עצמים והוספתם למבנה הנתונים
                            for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                //המרת העצם לטיפוס שלו// הוספת העצם למבנה הנתונים
                                arrayList.add(document.toObject(Park.class));
                            }
                            parkAdapter.clear();//ניקוי המתאם מכל הנתונים
                            parkAdapter.addAll(arrayList);//הוספת כל הנתונים למתאם
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error Reading data"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    public void OnclickAddPark() {
        //to open new activity from current to next
        Intent i = new Intent(MainActivity.this, AddPark.class);
        startActivity(i);
        //to close current activity
        finish();
    }
}
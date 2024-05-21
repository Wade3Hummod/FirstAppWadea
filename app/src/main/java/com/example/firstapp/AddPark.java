package com.example.firstapp;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstapp.data.parksTable.Park;
import com.example.firstapp.data.usersTable.MyUser;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

// https://www.geeksforgeeks.org/how-to-get-user-location-in-android/?ref=header_search
public class AddPark extends AppCompatActivity
{
    private TextInputEditText etStreet;
    private TextInputEditText etCity;
    private TextInputEditText etNumber;
    private TextInputEditText etPhone;
    private Button btnSave;
    private ImageButton ibtnGps;
    private TextView tvgpslocatoin;
    private ImageView imgBtn;
    //upload: 1 add Xml image view or button and upload button
//upload: 2 add next fileds
    private final int IMAGE_PICK_CODE=100;// קוד מזהה לבקשת בחירת תמונה
    private final int IMAGE_PERMISSION_CODE =101;//קוד מזהה לבחירת הרשאת גישה לקבצים
    private Uri toUploadimageUri;// כתוב הקובץ(תמונה) שרוצים להעלות
    private Uri downladuri;//כתובת הקוץ בענן אחרי ההעלאה

    private Park park =new Park();//עצם/נתון שרוצים לשמור
    private MyUser user;//עצם/נתון שרוצים לשמור
    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    int LOCATION_PERMISSION_ID = 44;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_park);
        etStreet = findViewById(R.id.etSteet);
        etCity = findViewById(R.id.etCity);
        etNumber = findViewById(R.id.etNumber);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        ibtnGps = findViewById(R.id.ibtnGps);
        tvgpslocatoin = findViewById(R.id.tvgpslocatoin);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ibtnGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();
            }
        });
        btnSave=findViewById(R.id.btnSave);
        //upload: 3
        imgBtn=findViewById(R.id.imgView);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //upload: 8
                check_IMAGE_Permission();
    }
        });

        //معالج حدث الظغط الزر
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) 
            {
              checkAddPark();
            }
        });
        // method to get the location
       // getLastLocation();


    }

    public void savepark_FB( )
    {
        //قاعدة البيانات
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        String parkId = db.collection("MyParks").document().getId();
        park.setParkId(parkId);

        //اضافة كائن "لمجموعة"المستعملين ومعالج حدث لفحص نجاح المطلوب
        //معالج حدث لفحص هل تم المطلوب من قاعدة البيانات
        db.collection("MyParks").document(parkId).set(park).addOnCompleteListener(new OnCompleteListener<Void>()
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
    private void pickImageFromGallery(){
        //implicit intent (מרומז) to pick image
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);//הפעלתה האינטנט עם קוד הבקשה
    }
    //upload: 5:handle result of picked images
    /**
     *
     * @param requestCode מספר הקשה
     * @param resultCode תוצאה הבקשה (אם נבחר משהו או בוטלה)
     * @param data הנתונים שנבחרו
     */
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //אם נבחר משהו ואם זה קוד בקשת התמונה
        if (resultCode==RESULT_OK && requestCode== IMAGE_PICK_CODE){
            //a עידכון תכונת כתובת התמונה
            toUploadimageUri = data.getData();//קבלת כתובת התמונה הנתונים שניבחרו
            imgBtn.setImageURI(toUploadimageUri);// הצגת התמונה שנבחרה על רכיב התמונה
        }
    }
//upload: 6
    /**
     * בדיקה האם יש הרשאה לגישה לקבצים בטלפון
     */
    private void check_IMAGE_Permission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//בדיקת גרסאות
            //בדיקה אם ההשאה לא אושרה בעבר
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //רשימת ההרשאות שרוצים לבקש אישור
                String[] permissions = {android.Manifest.permission.READ_EXTERNAL_STORAGE};
                //בקשת אישור ההשאות (שולחים קוד הבקשה)
                //התשובה תתקבל בפעולה onRequestPermissionsResult
                requestPermissions(permissions, IMAGE_PERMISSION_CODE);
            } else {
                //permission already granted אם יש הרשאה מקודם אז מפעילים בחירת תמונה מהטלפון
                pickImageFromGallery();
            }
        }
        else {//אם גרסה ישנה ולא צריך קבלת אישור
            pickImageFromGallery();
        }
    }





    private void checkAddPark()
    {
        boolean isAllok = true; //يفحص الحقول ان كانت سليمة
        String street= etStreet.getText().toString();
        String city= etCity.getText().toString();
        String number= etNumber.getText().toString();
        String phone= etPhone.getText().toString();
        if (street.length()<1)
        {
            isAllok=false;
            etStreet.setError("must write street name");
        }
        if (city.length()<1)
        {
            isAllok=false;
            etStreet.setError("must write city name");
        }
        if (number.length()<1)
        {
            isAllok=false;
            etStreet.setError("must write number name");
        }
        if (location==null)
        {
            isAllok=false;
            tvgpslocatoin.setError("set location");
            tvgpslocatoin.setText("set location");

        }
        if (phone.length()!=10)
        {
            isAllok=false;
            etPhone.setError("must write number phone");
        }
        if(isAllok)
        {
            //todo get loc from adress and get andress from loc
            //استخراج الرقم المميز للمستعمل الذي سجل الدخول لاستعماله كاسم لل"دوكيومينت
            String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

            park.setStreet(street);
            park.setCity(city);
            park.setNumber(Integer.parseInt(number));
            park.setLat(location.getLatitude());
            park.setLng(location.getLongitude());
            park.setUserId(uid);
            uploadImage(toUploadimageUri);

        }



    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                         location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            tvgpslocatoin.setText("Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude() + "");

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            tvgpslocatoin.setText("Latitude: " + mLastLocation.getLatitude() + "Longitude: " + mLastLocation.getLongitude() + "");
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                ACCESS_COARSE_LOCATION,
                ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
//upload: 7
    /**
     * @param requestCode The request code passed in מספר בקשת ההרשאה
     * @param permissions The requested permissions. Never null. רשימת ההרשאות לאישור
     * @param grantResults The grant results for the corresponding permissions תוצאה עבור כל הרשאה
     *   PERMISSION_GRANTED אושר or PERMISSION_DENIED נדחה . Never null.
     */

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
        if (requestCode == IMAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImageFromGallery();
            }
        }
    }

    private void uploadImage(Uri filePath) {
        if (filePath != null) {
            //יצירת דיאלוג התקדמות
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();//הצגת הדיאלוג
            //קבלת כתובת האחסון בענן
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            //יצירת תיקיה ושם גלובלי לקובץ
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            // יצירת ״תהליך מקביל״ להעלאת תמונה
            ref.putFile(filePath)
                    //הוספת מאזין למצב ההעלאה
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();// הסתרת הדיאלוג
                                //קבלת כתובת הקובץ שהועלה
                                ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        downladuri = task.getResult();
                                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                        park.setImage(downladuri.toString());//עדכון כתובת התמונה שהועלתה
                                        savepark_FB();
                                    }
                                });
                            } else {
                                progressDialog.dismiss();//הסתרת הדיאלוג
                                Toast.makeText(getApplicationContext(), "Failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    //הוספת מאזין שמציג מהו אחוז ההעלאה
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //חישוב מה הגודל שהועלה
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()/ taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        } else {
            savepark_FB();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        }
    }
}
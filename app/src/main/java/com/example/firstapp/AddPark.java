package com.example.firstapp;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageButton;
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
// https://www.geeksforgeeks.org/how-to-get-user-location-in-android/?ref=header_search
public class AddPark extends AppCompatActivity
{
    private TextInputEditText etStreet;
    private TextInputEditText etCity;
    private TextInputEditText etNumber;
    private Button btnSave;
    private ImageButton ibtnGps;
    private TextView tvgpslocatoin;
    // initializing
    // FusedLocationProviderClient
    // object
    FusedLocationProviderClient mFusedLocationClient;

    // Initializing other items
    // from layout file
    int PERMISSION_ID = 44;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_park);
        etStreet = findViewById(R.id.etSteet);
        etCity = findViewById(R.id.etCity);
        etNumber = findViewById(R.id.etNumber);
        btnSave = findViewById(R.id.btnSave);
        ibtnGps = findViewById(R.id.ibtnGps);
        tvgpslocatoin = findViewById(R.id.tvgpslocatoin);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();


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
    private void checkAddPark()
    {
        boolean isAllok = true; //يفحص الحقول ان كانت سليمة
        String street= etStreet.getText().toString();
        String city= etCity.getText().toString();
        String number= etNumber.getText().toString();
        String location= tvgpslocatoin.getText().toString();


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
                        Location location = task.getResult();
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
                ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
}
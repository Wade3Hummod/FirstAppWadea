package com.example.firstapp.data.parksTable;

import static android.Manifest.permission.CALL_PHONE;
import static androidx.core.app.ActivityCompat.requestPermissions;
import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.PermissionChecker;

import com.example.firstapp.R;
import com.squareup.picasso.Picasso;

public class MyParkAdapter extends ArrayAdapter<Park> {
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;

    /**
     * פעולה בונה מתאם
     *
     * @param context  קישור להקשר (מסך- אקטיביטי)
     * @param resource עיצוב של פריט שיציג הנתונים של העצם
     */
    public MyParkAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //בניית הפריט הגרפי מתו קובץ העיצוב
        View vitem = convertView;
        if (vitem == null)
            vitem = LayoutInflater.from(getContext()).inflate(itemLayout, parent, false);
        //קבלת הפניות לרכיבים בקובץ העיצוב
        ImageView imageView1 = vitem.findViewById(R.id.imgVitm);
        TextView tvStreet = vitem.findViewById(R.id.tvItmStreet);
        TextView tvCity = vitem.findViewById(R.id.tvItmCity);
        TextView tvNumber = vitem.findViewById(R.id.tvItmNumber);
        TextView tvUserId = vitem.findViewById(R.id.tvItmUserId);
        TextView tvParkId = vitem.findViewById(R.id.tvItmParkId);
        ImageView BtnCall = vitem.findViewById(R.id.imgbtnCall);
        ImageView BtnDelete = vitem.findViewById(R.id.imgBtnDeleteitm);
        ImageView waze = vitem.findViewById(R.id.imgwaze);
        ImageView maps = vitem.findViewById(R.id.imgmaps);


        //קבלת הנתון (עצם) הנוכחי
        Park current = getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvStreet.setText(current.getStreet());
        tvCity.setText(current.getCity());
        tvNumber.setText("Number:" + current.getNumber());
        tvUserId.setText(current.getUserId());
        tvParkId.setText(current.getParkId());




        downloadImageUsingPicasso(current.getImage(), imageView1);

        BtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPhoneNymber(current.getPhone());
            }
        });
        BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        waze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWaze(current);
            }
        });
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps(current);
            }
        });



        return vitem;


    }

    /**
     * הצגת תמונה ישירות מהענן בעזרת המחלקה ״פיקאסו״
     *
     * @param imageUrL כתובת התמונה בענן/שרת
     * @param toView   רכיב תמונה המיועד להצגת התמונה אחרי ההורדה
     */
    private void downloadImageUsingPicasso(String imageUrL, ImageView toView) {
        // אם אין תמונה= כתובת ריקה אז לא עושים כלום מפסיקים את הפעולה
        if (imageUrL == null) return;
        //todo: add dependency to module gradle:
        //    implementation 'com.squareup.picasso:picasso:2.5.2'
        Picasso.with(getContext())
                .load(imageUrL)//הורדת התמונה לפי כתובת
                .centerCrop()//todo change eror icon
                .error(R.drawable.ic_launcher_background)//התמונה שמוצגת אם יש בעיה בהורדת התמונה
                .resize(90, 90)//שינוי גודל התמונה
                .into(toView);// להציג בריכיב התמונה המיועד לתמונה זו
    }

    private void callAPhoneNymber(String phone) {
        //בדיקה אם יש הרשאה לביצוע שיחה
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//בדיקת גרסאות
            //בדיקה אם ההרשאה לא אושרה בעבר
            if (checkSelfPermission(getContext(), CALL_PHONE) == PermissionChecker.PERMISSION_DENIED) {
                //רשימת ההרשאות שרוצים לבקש אישור
                String[] permissions = {CALL_PHONE};
                //בקשת אישור הרשאות (שולחים קוד הבקשה)
                //התשובה תתקבל בפעולה onRequestPermissionsResult
                requestPermissions((Activity) getContext(), permissions, 100);
            } else {
                //אינטנט מרומז לפתיחת אפליקצית ההודות סמס
                Intent phone_intent = new Intent(Intent.ACTION_CALL);
                phone_intent.setData(Uri.parse("tel:" + phone));
            }
        }
    }

    public void openWaze(Park p)
    {
        // Open Waze
        Intent wazeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://waze.com/ul?ll=" + p.getLat() + "," + p.getLng() + "&navigate=yes"));
        wazeIntent.setPackage("com.waze");
        if (wazeIntent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(wazeIntent);
        } else {
            Toast.makeText(getContext(), "Please install Waze", Toast.LENGTH_SHORT).show();
        }
    }
    public void openMaps(Park p)
    {
        // Open Google Maps if Waze is not installed
        Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + p.getLat() + "," + p.getLng() + "?q=" + p.getLat() + "," + p.getLng() + "(Label+Name)"));
        mapsIntent.setPackage("com.google.android.apps.maps");
        if (mapsIntent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(mapsIntent);
        } else {
            Toast.makeText(getContext(), "Please install Google Maps", Toast.LENGTH_LONG).show();
        }
    }

}

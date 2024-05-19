package com.example.firstapp.data.parksTable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.firstapp.R;

public class MyParkAdapter extends ArrayAdapter<Park> {
    //המזהה של קובץ עיצוב הפריט
    private final int itemLayout;
    /**
     * פעולה בונה מתאם
     * @param context קישור להקשר (מסך- אקטיביטי)
     * @param resource עיצוב של פריט שיציג הנתונים של העצם
     */
    public MyParkAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.itemLayout=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //בניית הפריט הגרפי מתו קובץ העיצוב
        View vitem= convertView;
        if(vitem==null)
            vitem= LayoutInflater.from(getContext()).inflate(itemLayout,parent,false);
        //קבלת הפניות לרכיבים בקובץ העיצוב
        ImageView imageView1=vitem.findViewById(R.id.imgVitm);
        TextView tvStreet=vitem.findViewById(R.id.tvItmStreet);
        TextView tvCity=vitem.findViewById(R.id.tvItmCity);
        TextView tvNumber=vitem.findViewById(R.id.tvItmNumber);
        TextView tvLocation=vitem.findViewById(R.id.tvImLocation);
        TextView tvUserId=vitem.findViewById(R.id.tvItmUserId);
        TextView tvParkId=vitem.findViewById(R.id.tvItmParkId);
        ImageView BtnEdit=vitem.findViewById(R.id.imgBtnEdititm);
        ImageView BtnDelete=vitem.findViewById(R.id.imgBtnDeleteitm);
        //קבלת הנתון (עצם) הנוכחי
        Park current=getItem(position);
        //הצגת הנתונים על שדות הרכיב הגרפי
        tvStreet.setText(current.getStreet());
        tvCity.setText(current.getCity());
        tvNumber.setText("Number:"+current.getNumber());
        tvLocation.setText("loc lat:"+current.getLat()+" loc lng:"+current.getLng());
        tvUserId.setText(current.getUserId());
        tvParkId.setText(current.getParkId());






        return vitem;


    }
}

package com.example.firstapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.firstapp.data.usersTable.MyUser;
import com.example.firstapp.data.usersTable.MyUserQuery;


/*
تعريف الجداول ورقم النسخة
version
عند تغير اي شيء يخص جدول او جداول علينا تغيير رقم الاصدار ليتم بناء قاعدة البيانات من جديد
 */
@Database(entities = {MyUser.class},version = 1)
/**
 * الفئة المسؤولة عن بناء قاعدة البيانات بكل جداولها
 * وتوفر لنا كائن لتعامل مع قاعدة البناء
 */
public abstract class AppDatabase extends RoomDatabase
{
    /**
     * كائن للتعامل مع قاعدة البيانات
     */
    private static AppDatabase db;

    /**
     * يعيد كائن لعمليات جدول المستعملين
     * @return
     */
    public abstract MyUserQuery getMyUserQuery();



    /**
     * بناء قاعدة البيانات واعادة كائن يؤشر عليها
     * @param context
     * @return
     */

    public static AppDatabase getDB(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,AppDatabase.class, "database-name")//اسم قاعدة البيانات
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()

                    .build();
        }
        return db;
    }
}
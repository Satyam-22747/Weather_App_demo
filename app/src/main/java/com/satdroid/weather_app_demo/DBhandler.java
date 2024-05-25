package com.satdroid.weather_app_demo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBhandler extends SQLiteOpenHelper {
    private static final String DB_Name="weatherdb";
    private static final int DB_Version=1;
    private static final String Table_name="favourite_cities";
    private static final String col_id="id";
    private static final String fav_cities="Fav_Cities";

    public DBhandler(Context context)
    {
        super(context,DB_Name,null,DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="CREATE TABLE "+Table_name+" ("
                +col_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +fav_cities+" TEXT)";
        db.execSQL(query);
    }

    public void addTofavourite(String cityName)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(fav_cities,cityName);
        db.insert(Table_name,null,values);
        db.close();
    }
    public ArrayList<favcitymodal> favcities()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor favcitycursor=db.rawQuery("SELECT * FROM "+Table_name,null);
        ArrayList<favcitymodal> allcitiesList=new ArrayList<>();
        if(favcitycursor.moveToFirst())
        {
            do{
                allcitiesList.add(0,new favcitymodal(favcitycursor.getString(1)));
            }while(favcitycursor.moveToNext());
        }
        favcitycursor.close();
        db.close();
        return allcitiesList;
    }
    public void deleteCity(String citySelected)
    {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete(Table_name,"Fav_Cities=?",new String[]{citySelected});
        sqLiteDatabase.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS "+Table_name);
onCreate(db);
    }
}

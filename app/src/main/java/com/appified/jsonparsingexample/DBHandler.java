package com.appified.jsonparsingexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by PP on 6/14/2015.
 */
public class DBHandler extends SQLiteOpenHelper implements CityListener {
    //http://www.appifiedtech.net/2015/06/19/android-json-parsing-with-sqlite-example/
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "CityDatabase.db";
    private static final String TABLE_NAME = "city_table";
    private static final String TABLE_NAME_JUR = "tb_jurusan";
    private static final String KEY_ID = "_id";
    private static final String KEY_NAME = "_name";
    private static final String KEY_STATE = "_state";
    private static final String KEY_DESCRIPTION = "_description";
    private static final String KEY_LOGO = "_logo";
    private static final String KEY_JURUSAN = "_jurusan";

    String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY,"+KEY_NAME+" TEXT,"+KEY_STATE+" TEXT,"+KEY_DESCRIPTION+" TEXT,"+KEY_LOGO+" TEXT)";
    String DROP_TABLE = "DROP TABLE IF EXISTS "+TABLE_NAME;

    String CREATE_TABLE_JUR = "CREATE TABLE "+TABLE_NAME_JUR+" ("+KEY_STATE+" TEXT,"+KEY_JURUSAN+" TEXT)";
    String DROP_TABLE_jur = "DROP TABLE IF EXISTS "+TABLE_NAME_JUR;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_JUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        db.execSQL(DROP_TABLE_jur);
        onCreate(db);
    }

    @Override
    public void addCity(City city) {
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, city.getName());
            values.put(KEY_STATE, city.getState());
            values.put(KEY_DESCRIPTION,city.getDescription());
            values.put(KEY_LOGO,city.getLogo());
            db.insert(TABLE_NAME, null, values);
            db.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }

    @Override
    public void addjur(jurusan jurnya) {
        SQLiteDatabase db1 = this.getWritableDatabase();
        try{
            ContentValues jur = new ContentValues();
            jur.put(KEY_STATE, jurnya.getIdkampus());
            jur.put(KEY_JURUSAN,jurnya.getJurusan());
            db1.insert(TABLE_NAME_JUR, null, jur);
            db1.close();
        }catch (Exception e){
            Log.e("problem",e+"");
        }
    }

    @Override
    public ArrayList<City> getAllCity() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<City> cityList = null;
        try{
            cityList = new ArrayList<City>();
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            if(!cursor.isLast())
            {
                while (cursor.moveToNext())
                {
                    City city = new City();
                    city.setId(cursor.getInt(0));
                    city.setName(cursor.getString(1));
                    city.setState(cursor.getString(2));
                    city.setDescription(cursor.getString(3));
                    city.setLogo(cursor.getString(4));
                    cityList.add(city);
                }
            }
            db.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }


        return cityList;
    }

    @Override
    public ArrayList<jurusan> getAlljur() {
        SQLiteDatabase db1 = this.getReadableDatabase();
        ArrayList<jurusan> jurursan_list = null;
        try{
            jurursan_list = new ArrayList<jurusan>();
            String QUERY1 = "SELECT * FROM "+TABLE_NAME_JUR;
            Cursor cursor1 = db1.rawQuery(QUERY1, null);
            if(!cursor1.isLast())
            {
                while (cursor1.moveToNext())
                {
                    jurusan jur = new jurusan();
                    jur.setIdkampus(cursor1.getString(0));
                    jur.setJurusan(cursor1.getString(1));
                    jurursan_list.add(jur);
                }
            }
            db1.close();
        }catch (Exception e){
            Log.e("error",e+"");
        }


        return jurursan_list;
    }

    @Override
    public int getCityCount() {
        int num = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            String QUERY = "SELECT * FROM "+TABLE_NAME;
            Cursor cursor = db.rawQuery(QUERY, null);
            num = cursor.getCount();
            db.close();
            return num;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }

    @Override
    public int getjur() {
        int num1 = 0;
        SQLiteDatabase db1 = this.getReadableDatabase();
        try{
            String QUERY1 = "SELECT * FROM "+TABLE_NAME_JUR;
            Cursor cursor1 = db1.rawQuery(QUERY1, null);
            num1 = cursor1.getCount();
            db1.close();
            return num1;
        }catch (Exception e){
            Log.e("error",e+"");
        }
        return 0;
    }
}

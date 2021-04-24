package com.example.car_dealership_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE USERS(EMAIL TEXT PRIMARY KEY," +
                "PASSWORD TEXT ,CITY TEXT ,COUNTRY TEXT, PHONE TEXT, FIRSTNAME TEXT,LASTNAME TEXT," +
                "GENDER TEXT, RESERVATIONS TEXT,FAVORITES TEXT , IsADMIN TEXT)");

        db.execSQL("CREATE TABLE FAVORITES (EMAIL TEXT NOT NULL , TITLE TEXT NOT NULL, PRIMARY KEY(EMAIL , TITLE))");
        //db.execSQL("CREATE TABLE RESERVATIONS (EMAIL TEXT NOT NULL, TITLE TEXT NOT NULL,  QUANTITY INTEGER , PRIMARY KEY(EMAIL , TITLE))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void AddUser(User users) {
        //password is hashed here

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", users.getEmail());
        contentValues.put("PASSWORD", HashHelper.md5(users.getPassword()) ); // hash of password
        contentValues.put("CITY", users.getCity());
        contentValues.put("COUNTRY", users.getCountry());
        contentValues.put("GENDER", users.getGender());
        contentValues.put("PHONE", users.getPhone());
        contentValues.put("FIRSTNAME", users.getFirstName());
        contentValues.put("LASTNAME", users.getLastName());
        contentValues.put("IsADMIN", users.getisAdmin());
        sqLiteDatabase.insert("USERS", null, contentValues);
    }

    public void updateUser(User users) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", users.getEmail());
        contentValues.put("PASSWORD", HashHelper.md5( users.getPassword()));
        contentValues.put("CITY", users.getCity());
        contentValues.put("COUNTRY", users.getCountry());
        contentValues.put("GENDER", users.getGender());
        contentValues.put("PHONE", users.getPhone());
        contentValues.put("FIRSTNAME", users.getFirstName());
        contentValues.put("LASTNAME", users.getLastName());
        contentValues.put("IsADMIN", users.getisAdmin());
        db.update("USERS", contentValues,"EMAIL =?" , new String[] {users.getEmail()}  );
    }

    public Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS", null);
    }

    //return the hash value of password from database for a specific user
    public Cursor getPasswordForEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT PASSWORD FROM USERS WHERE EMAIL = '" + email+"'", null);
    }

    public Cursor isAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT IsADMIN FROM USERS WHERE EMAIL = '" + email+"'", null);
    }
}

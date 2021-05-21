package com.example.car_dealership_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.car_dealership_project.models.Car;
import com.example.car_dealership_project.models.Reservation;
import com.example.car_dealership_project.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE USERS(EMAIL TEXT PRIMARY KEY," +
                "PASSWORD TEXT ,CITY TEXT ,COUNTRY TEXT, PHONE TEXT, FIRSTNAME TEXT,LASTNAME TEXT," +
                "GENDER TEXT, RESERVATIONS TEXT,FAVORITES TEXT , IsADMIN TEXT)");

        db.execSQL("CREATE TABLE FAVORITES (EMAIL TEXT NOT NULL , MODEL TEXT NOT NULL, DISTANCE TEXT NOT NULL, PRIMARY KEY(EMAIL , MODEL, DISTANCE))");
        db.execSQL("CREATE TABLE RESERVATIONS (EMAIL TEXT NOT NULL, MODEL TEXT NOT NULL,  DISTANCE TEXT NOT NULL, RESERVED_ON DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY(EMAIL , MODEL, DISTANCE, RESERVED_ON))");

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

    public void updateUser(String email, String city, String country, String phone, String firstName, String lastName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CITY", city);
        contentValues.put("COUNTRY", country);
        contentValues.put("PHONE", phone);
        contentValues.put("FIRSTNAME", firstName);
        contentValues.put("LASTNAME", lastName);
        db.update("USERS", contentValues,"EMAIL =?" , new String[] {email}  );
    }

    public void updatePassword(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PASSWORD", HashHelper.md5( password ));
        db.update("USERS", contentValues,"EMAIL =?" , new String[] {email}  );
    }
    public boolean deleteUser(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        long res = db.delete("USERS", "EMAIL=?", new String[]{email} );
        return res != -1;
    }

    public User getUser(String email){
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor query = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE EMAIL=?", new String[] {email});
            query.moveToFirst();
//            Log.e("GetUser",String.valueOf(query.getColumnCount()));
            int passwordIndex = query.getColumnIndexOrThrow("PASSWORD");
            int cityIndex = query.getColumnIndexOrThrow("CITY");
            int countryIndex = query.getColumnIndexOrThrow("COUNTRY");
            int phoneIndex = query.getColumnIndexOrThrow("PHONE");
            int fnIndex = query.getColumnIndexOrThrow("FIRSTNAME");
            int lnIndex = query.getColumnIndexOrThrow("LASTNAME");
            int genderIndex = query.getColumnIndexOrThrow("GENDER");
            int adminIndx = query.getColumnIndexOrThrow("IsADMIN");
            String password = query.getString(passwordIndex);
            String country = query.getString(countryIndex);
            String city = query.getString(cityIndex);
            String phone = query.getString(phoneIndex);
            String firstName = query.getString(fnIndex);
            String lastName = query.getString(lnIndex);
            String gender = query.getString(genderIndex);
            boolean isAdmin =  query.getInt(adminIndx) > 0;
            return new User(email,password,country,city,gender,phone,firstName,lastName,isAdmin);
//            Log.e("GetUser", password + country + city + phone + firstName + lastName + gender + isAdmin);
//            return new User();
        } catch (Exception e){
            Log.e("GETUSER",e.toString());
            throw e;
        }
    }

    public Cursor getAllUsers() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM USERS", null);
    }

    public List<User> getAllUsersRaw() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        List<User> listRes = new ArrayList<User>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor queryRes = db.rawQuery("SELECT * FROM USERS", null);
        queryRes.moveToFirst();
        while(!queryRes.isAfterLast()){
            //public User(String email, String password, String country, String city, String gender, String phone, String firstName, String lastName,  boolean isAdmin) {
            int emailIndex = queryRes.getColumnIndex("EMAIL");
            int passwordIndex = queryRes.getColumnIndex("PASSWORD");
            int countryIndex = queryRes.getColumnIndex("COUNTRY");
            int cityIndex = queryRes.getColumnIndex("CITY");
            int genderIndex = queryRes.getColumnIndex("GENDER");
            int phoneIndex = queryRes.getColumnIndex("PHONE");
            int fnIndex = queryRes.getColumnIndex("FIRSTNAME");
            int lnIndex = queryRes.getColumnIndex("LASTNAME");
            int isAdmin = queryRes.getColumnIndex("IsADMIN");
            User user = new User(queryRes.getString(emailIndex),
                    queryRes.getString(passwordIndex),
                    queryRes.getString(countryIndex),
                    queryRes.getString(cityIndex),
                    queryRes.getString(genderIndex),
                    queryRes.getString(phoneIndex),
                    queryRes.getString(fnIndex),
                    queryRes.getString(lnIndex),
                    queryRes.getInt(isAdmin) > 0);
            listRes.add(user);
            queryRes.moveToNext();
        }
        return listRes;
    }

    //return the hash value of password from database for a specific user
    public Cursor getPasswordForEmail(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT PASSWORD FROM USERS WHERE EMAIL='" + email+"'", null);
    }

    public Cursor isAdmin(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT IsADMIN FROM USERS WHERE EMAIL = '" + email+"'", null);
    }

    public Cursor getFirstName(String email) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery("SELECT FIRSTNAME FROM USERS WHERE EMAIL = '" + email+"'", null);
    }

    public boolean addFavourite(String email, Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("MODEL", car.getModel());
        contentValues.put("DISTANCE", car.getDistance());
        long result = db.insert("FAVORITES", null,contentValues);
        return result != -1;
    }

    public List<Car> getFavourites(String email) {
        List<Car> listRes = new ArrayList<Car>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor queryRes = db.rawQuery("SELECT * FROM FAVORITES WHERE EMAIL='"+email+"'", null);
        int modelIndex = queryRes.getColumnIndex("MODEL");
        int distanceIndex = queryRes.getColumnIndex("DISTANCE");
        queryRes.moveToFirst();
        while(!queryRes.isAfterLast()){
            String model = queryRes.getString(modelIndex);
            String distance = queryRes.getString(distanceIndex);
            for (Car car : Car.cars){
                if( model.contentEquals(car.getModel()) && distance.contentEquals(car.getDistance()) ){
                   listRes.add(car);
                   break;
                }
            }
            queryRes.moveToNext();
        }
        return listRes;
    }

    public boolean removeFavourite(String email, Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("FAVORITES", "EMAIL=? AND MODEL=? AND DISTANCE=?", new String[] { email, car.getModel(), car.getDistance()});
        if(result == -1) return false;
        else return true;
    }

    public boolean reserveCar(String email, Car car) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL", email);
        contentValues.put("MODEL", car.getModel());
        contentValues.put("DISTANCE", car.getDistance());
        long result = db.insert("RESERVATIONS", null,contentValues);
        return result != -1;
    }

    public List<Reservation> getReserved(String email){
        List<Reservation> listRes = new ArrayList<Reservation>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor queryRes = db.rawQuery("SELECT * FROM RESERVATIONS WHERE EMAIL='"+email+"'", null);
        int modelIndex = queryRes.getColumnIndex("MODEL");
        int distanceIndex = queryRes.getColumnIndex("DISTANCE");
        queryRes.moveToFirst();
        while(!queryRes.isAfterLast()){
            String model = queryRes.getString(modelIndex);
            String distance = queryRes.getString(distanceIndex);
            for (Car car : Car.cars){
                if( model.contentEquals(car.getModel()) && distance.contentEquals(car.getDistance()) ){
                    String date = queryRes.getString( queryRes.getColumnIndex("RESERVED_ON"));

                    listRes.add(new Reservation(car, date, email));
                    break;
                }
            }
            queryRes.moveToNext();
        }
        return listRes;
    }

    public List<Reservation> getAllReservations(){
        List<Reservation> listRes = new ArrayList<Reservation>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor queryRes = db.rawQuery("SELECT * FROM RESERVATIONS", null);
        int modelIndex = queryRes.getColumnIndex("MODEL");
        int distanceIndex = queryRes.getColumnIndex("DISTANCE");
        int emailIndex = queryRes.getColumnIndex("EMAIL");
        queryRes.moveToFirst();
        while(!queryRes.isAfterLast()){
            String model = queryRes.getString(modelIndex);
            String distance = queryRes.getString(distanceIndex);
            for (Car car : Car.cars){
                if( model.contentEquals(car.getModel()) && distance.contentEquals(car.getDistance()) ){
                    String date = queryRes.getString( queryRes.getColumnIndex("RESERVED_ON"));

                    listRes.add(new Reservation(car, date, queryRes.getString(emailIndex)));
                    break;
                }
            }
            queryRes.moveToNext();
        }
        return listRes;
    }
}

package com.example.asus.parkalot.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.example.asus.parkalot.model.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vishwesh on 05/03/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static String DATABASE_NAME = "vpark.db";
    public static final String TABLE_NAME = "users";
    public static final String TABLE_PLOTS ="parkingLots";
    public static final String TABLE_PARKD = "parkingDetails";
    public static final String TABLE_FEEDBACK ="feedback";
    public static final String TABLE_SERVICEREQUEST = "serviceRequest";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    //private static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "pass";
    public static final String COLUMN_LICENSENO = "license_no";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_isASSOCIATE = "isAssociate";
    public static final String COLUMN_isADMIN = "isAdmin";
    public static final String COLUMN_IMAGE = "image";
    /*SQLiteDatabase db;*/
    private static final String TABLE_CREATE = "create table users (id integer primary key autoincrement not null  , " +
            "name text not null , email text not null  , contact text not null, pass text, license_no text not null, isAssociate integer default 0, isAdmin integer default 0);";
    private static final String PLOTS_CREATE = "create table parkingLots (pid integer primary key autoincrement not null  , " +
            "lotName text not null , slotNos integer default 6  , latitude text not null, longitude text not null, charges integer not null, image blob default 0, category text default 'standard', rating int default 0);";
    private static final String PARKD_CREATE = "create table parkingDetails (bid integer primary key autoincrement not null  , " +
            "lotName text not null , pid integer not null  , uid integer not null, dateTime datetime not null, duration integer not null, slotno integer not null, serviceTaken integer default 0, charges int default 0,paymentDone integer default 0);";
    private static final String FEEDBACK_CREATE = "create table feedback(fid integer primary key autoincrement not null  , " +
            "feedback text not null , uid integer not null  , pid integer not null, stars integer not null);";
    public static final String SERVICEREQUEST_CREATE = "create table serviceRequest(sid integer primary key autoincrement not null  , " +
            "service text not null , uid integer not null  ,  pid integer not null, serviced integer default 0);";
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME , null, DATABASE_VERSION );

    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(TABLE_CREATE);
        db.execSQL(PLOTS_CREATE);
        db.execSQL(PARKD_CREATE);
        db.execSQL(FEEDBACK_CREATE);
        db.execSQL(SERVICEREQUEST_CREATE);


    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String query1 = "DROP TABLE IF EXISTS "+TABLE_NAME;
        String query2 = "DROP TABLE IF EXISTS "+TABLE_PLOTS;
        String query3 = "DROP TABLE IF EXISTS "+TABLE_PARKD;
        String query4 = "DROP TABLE IF EXISTS "+TABLE_FEEDBACK;
        String query5 = "DROP TABLE IF EXISTS "+TABLE_SERVICEREQUEST;

        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
        db.execSQL(query5);
        onCreate(db);
    }

    public boolean insertData (String name,String email,String contact,String pass,String licence){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(COLUMN_NAME , name);
        value.put(COLUMN_EMAIL, email);
        value.put(COLUMN_CONTACT, contact);
        value.put(COLUMN_PASSWORD, pass);
        value.put(COLUMN_LICENSENO, licence);
        long result=db.insert(TABLE_NAME,null,value);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean insertParkingLot (String lotName, int slotNos, String latitude,String longitude,int  charges , String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("lotName", lotName);
        value.put("slotNos", slotNos);
        value.put("latitude",latitude);
        value.put("longitude",longitude);
        value.put("charges" , charges);
        value.put("category" , category);
        long result=db.insert(TABLE_PLOTS,null,value);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertBooking (String lotName, int pid, int uid, String dateTime, int duration , int slotno, int serviceTaken, int charges, int paymentDone){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("lotName", lotName);
        value.put("pid", pid);
        value.put("uid",uid);
        value.put("dateTime",dateTime);
        value.put("duration" , duration);
        value.put("slotno" , slotno);
        value.put("serviceTaken" , serviceTaken);
        value.put("charges" , charges);
        value.put("paymentDone" , paymentDone);
        long result=db.insert(TABLE_PARKD,null,value);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertFeedback (String feedback,int uid, int pid, int stars){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("feedback" , feedback);
        value.put("uid", uid);
        value.put("pid", pid);
        value.put("stars", stars);
        long result=db.insert(TABLE_FEEDBACK,null,value);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertServiceRequest (String service,int uid, int pid, int serviced){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("feedback" , service);
        value.put("uid", uid);
        value.put("pid", pid);
        value.put("serviced", serviced);
        long result=db.insert(TABLE_SERVICEREQUEST,null,value);
        if (result == -1)
            return false;
        else
            return true;
    }




    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(int id, String name,String email,String contact,String pass,String license) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        long result;
        String id1 = Integer.toString(id);
        value.put(COLUMN_NAME , name);
        value.put(COLUMN_EMAIL, email);
        value.put(COLUMN_CONTACT, contact);
        value.put(COLUMN_PASSWORD,pass);
        value.put(COLUMN_LICENSENO,license);
        result = db.update(TABLE_NAME, value, "ID = ?",new String[] {id1});
        if (result == -1)
            return false;
        else
            return true;





    }

    public boolean insertImage( int pid, byte[] image)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        long result;
        String id1 = Integer.toString(pid);
        value.put("image", image);

        result = db.update(TABLE_PLOTS, value, "ID = ?",new String[] {id1});
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean updateUser(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        long result;
        String id1 = Integer.toString(id);
        value.put(COLUMN_isASSOCIATE, 1);

        result = db.update(TABLE_NAME, value, "ID = ?",new String[] {id1});
        if (result == -1)
            return false;
        else
            return true;



    }

    public boolean deleteData (int id) {
        long result;
        String id1 = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        result=  db.delete(TABLE_NAME, "ID = ?",new String[] {id1});
        if (result == -1)
            return false;
        else
            return true;

    }

    public boolean deleteBooking(int id){
        long result;
        String id1 = Integer.toString(id);
        SQLiteDatabase db = this.getWritableDatabase();
        result=  db.delete(TABLE_PARKD, "ID = ?",new String[] {id1});
        if (result == -1)
            return false;
        else
            return true;
    }

    public int findID(String userid)
    {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        String query = "select email, id from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int b;
        String a = userid;
        b= -1;
        if(cursor.moveToFirst())
        {
            do
            {
                a = cursor.getString(0);
                if(userid.equals(a))
                {
                    /*
                    user.setPassword(cursor.getString(1));
                    user.setId(cursor.getInt(2));
                    user.setName(cursor.getString(3));
                     */
                    b = cursor.getInt(1);
                    break;
                }
            }
            while(cursor.moveToNext());
        }

        return b;


    }

    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT lotName FROM " + TABLE_PLOTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if(cursor!=null && cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    labels.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}



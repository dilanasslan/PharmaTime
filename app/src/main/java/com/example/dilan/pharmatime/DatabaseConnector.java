package com.example.dilan.pharmatime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dilan on 05/08/2017.
 */

public class DatabaseConnector {
    private static final String DATABASE_NAME = "PharmaTimeDb";
    private SQLiteDatabase database;
    private DatabaseConnector.DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context) {
        this.databaseOpenHelper = new DatabaseConnector.DatabaseOpenHelper(context, "PharmaTimeDb", (CursorFactory)null, 1);
    }

    public void open() throws SQLException {
        this.database = this.databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if(this.database != null) {
            this.database.close();
        }

    }

    public void insertContact(String PharmaName, String Barcode, int NumberOfDailyUsing, Date BeginDate, Date EndDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        ContentValues newPharma = new ContentValues();
        newPharma.put("PharmaName", PharmaName);
        newPharma.put("Barcode", Barcode);
        newPharma.put("NumberOfDailyUsing", NumberOfDailyUsing);
        newPharma.put("BeginDate", dateFormat.format(BeginDate));
        newPharma.put("EndDate", dateFormat.format(EndDate));
        this.open();
        this.database.insert("Pharma", (String)null, newPharma);
        this.close();
    }


    public void updatePharma(String PharmaName, String Barcode, int NumberOfDailyUsing, String BeginDate, String EndDate) {
        ContentValues editPharma = new ContentValues();
        editPharma.put("PharmaName", PharmaName);
        editPharma.put("Barcode", Barcode);
        editPharma.put("NumberOfDailyUsing", NumberOfDailyUsing);
        editPharma.put("BeginDate", BeginDate);
        editPharma.put("EndDate", EndDate);
        this.open();
        this.database.update("Pharma", editPharma, "PharmaName=" + PharmaName, (String[])null);
        this.close();
    }

       public Cursor getAllPharma() {
           return this.database.query("Pharma", new String[]{"_id"}, null, null, null, null, null);
       }
       /*

    

/*
           public Cursor getOneContact(long id) {
               return this.database.query("contacts", (String[])null, "_id=" + id, (String[])null, (String)null, (String)null, (String)null);
           }

           public void deleteContact(long id) {
               this.open();

               this.database.delete("contacts", "_id=" + id, (String[])null);
               this.close();
           }
       */
    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public void onCreate(SQLiteDatabase db) {
            String createQuery1 = "CREATE TABLE Pharma(id integer primary key autoincrement,PharmaName TEXT, Barcode TEXT, NumberOfDailyUsing int,BeginDate TEXT, EndDate TEXT);";
            String createQuery2 = "CREATE TABLE NotificationTime(id integer primary key autoincrement,PharmaId int, DrugTime TEXT, IsNotificationActive boolean);";
            db.execSQL(createQuery1);
            db.execSQL(createQuery2);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}

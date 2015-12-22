package com.example.raintree.signature;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rainTree on 2015-12-03.
 */
public class SignDatabase {
    public static final String TAG = "SignDatabase";

    private static SignDatabase database;

     // table name for SIGNATURE
    public static String TABLE_SIGN = "SIGN";

     // database version
    public static int DATABASE_VERSION = 1;

     // database name
    public static String DATABASE_NAME = "sign.db";

     // Helper class defined
    private DatabaseHelper dbHelper;

     // SQLiteDatabase instance
    private SQLiteDatabase db;

    private Context context;

    private SignDatabase(Context context) {
        this.context = context;
    }

    public static SignDatabase getInstance(Context context) {
        if (database == null) {
            database = new SignDatabase(context);
        }

        return database;
    }

    /**
     * open database
     */
    public boolean open() {
        Log.d(TAG, "opening database [" + DATABASE_NAME + "].");

        dbHelper = new DatabaseHelper(this.context);
        if(dbHelper == null){
            Log.e(TAG, "dbHelper is null");
        }

        try {
            db = dbHelper.getWritableDatabase();
            Log.d(TAG, "Open OK");
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error Opening database");
        }
        return true;
    }

    /**
     * close database
     */
    public void close() {
        Log.d(TAG, "closing database [" + DATABASE_NAME + "].");
        db.close();

        database = null;
    }

    /**
     * execute raw query using the input SQL
     */
    public Cursor rawQuery(String SQL) {
        Cursor c1 = null;
        try {
            c1 = db.rawQuery(SQL, null);
            Log.d(TAG, "cursor count : " + c1.getCount());
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) {
        try {
            Log.d(TAG, "SQL : " + SQL);
            db.execSQL(SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }

    /**
     * Database Helper inner class
     */
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {

            // drop existing table
            String DROP_SQL = "drop table if exists " + TABLE_SIGN;
            try {
                db.execSQL(DROP_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in DROP_SQL", ex);
            }

            // create table
            String CREATE_SQL = "create table " + TABLE_SIGN + "("
                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
                    + "  URI TEXT, "
                    + "  USERLASTNAME TEXT, "
                    + "  USERFIRSTNAME TEXT, "
                    + "  DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ");";
            try {
                db.execSQL(CREATE_SQL);
            } catch(Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
        }

        public void onOpen(SQLiteDatabase db)
        {
            Log.d(TAG, "opened database [" + DATABASE_NAME + "].");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion,
                              int newVersion)
        {
            Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion + ".");
        }
    }



}

package com.example.hshakilst.sqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by hshakilst on 6/11/2017.
 */

public class DBHelperAdapter {
    private DBHelper helper;
    private Context context;

    public DBHelperAdapter(Context context){
        this.context = context;
        helper = new DBHelper(context);
    }

    public long insert(String name, String email, String phone, String address){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(helper.NAME, name);
        values.put(helper.EMAIL, email);
        values.put(helper.PHONE, phone);
        values.put(helper.ADDRESS, address);
        return db.insert(helper.TABLE_NAME, null, values);
    }

    public Cursor getAllData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        return db.query(helper.TABLE_NAME, null, null, null, null, null, null);
    }

    public Cursor getDataById(int _id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] selectionArgs = {""+_id};
        return db.query(helper.TABLE_NAME, null, helper.UID+" = ?", selectionArgs, null, null, null);
    }

    public int delete(long _id){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {""+_id};
        return db.delete(helper.TABLE_NAME, helper.UID+" = ?", whereArgs);
    }

    public int update(int _id, ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] whereArgs = {""+_id};
        return db.update(helper.TABLE_NAME, values, helper.UID+" = ?", whereArgs);
    }

    class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "demoDB";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_NAME = "demoTable";
        private static final String UID = "_id";
        private static final String NAME = "name";
        private static final String EMAIL = "email";
        private static final String PHONE = "phone";
        private static final String ADDRESS = "address";
        private Context context;

        public DBHelper(Context context){
            super(context,DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try{
                sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                        ""+NAME+" VARCHAR(255), "+EMAIL+" VARCHAR(255), "+PHONE+" VARCHAR(13)," +
                        " "+ADDRESS+" VARCHAR(255));");
            }catch (SQLException e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try{
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
                onCreate(sqLiteDatabase);
            }catch (SQLException e){
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }
}

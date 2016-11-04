package com.ahdollars.crazyeights.mcqsampleapp.databases;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Shashank on 03-11-2016.
 */

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String TAG="DBLOG";

    public static final String  DATABASE_NAME="questionbank.db";
    public static final String  TABLE_QUESTION_NAME="Questions";
    public static final String  TABLE_OPTIONS="Options";

    public static final int DATABASE_VERSION=3;
    public static final String UID="_id";
    public static final String Q_TEXT="question";
    public static final String OID="_id";
    public static final String QID="qid";
    public static final String OPTION_TEXT="option";
    public static final String OPTION_VALID="istrue";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="CREATE TABLE "+TABLE_QUESTION_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Q_TEXT+" VARCHAR(200));";
        try {
            sqLiteDatabase.execSQL(query);
        }catch (SQLException e){
            Log.d(TAG, "onCreate: "+e.toString());
        }
        String query1="CREATE TABLE "+TABLE_OPTIONS+" ("+OID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+QID+" INTEGER , "+OPTION_TEXT+" VARCHAR(200), "+OPTION_VALID+" VARCHAR(5));";
        try {
            sqLiteDatabase.execSQL(query1);
        }catch (SQLException e){
            Log.d(TAG, "onCreate: "+e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String query="DROP TABLE IF EXISTS "+TABLE_OPTIONS+"";

        sqLiteDatabase.execSQL(query);
        query="DROP TABLE IF EXISTS "+TABLE_QUESTION_NAME+"";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);

    }

    private OnMyDBhelperUpdater callback;

    public interface OnMyDBhelperUpdater{
        void onUpdate();
    }

    public void setOnMyDBHelperUpdater(OnMyDBhelperUpdater i){
        callback=i;
    }



}

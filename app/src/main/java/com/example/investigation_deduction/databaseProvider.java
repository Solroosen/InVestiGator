package com.example.investigation_deduction;

import android.content.Context;

import com.example.investigation_deduction.DBHandler;

/**
 * Created by Sofie on 2016-10-20.
 */

public class databaseProvider {
    private static DBHandler db;

    public static void setDB(Context context){
        db = new DBHandler(context);
    }
    public static DBHandler getDB(){
        return db;
    }
}

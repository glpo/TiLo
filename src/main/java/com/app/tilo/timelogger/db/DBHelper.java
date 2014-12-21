package com.app.tilo.timelogger.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper implements BaseColumns{

    private static final String DATABASE_NAME = "tilo3.db";
    private static final int DATABASE_VERSION = 1;

    public static final String CATEGORIES_DATABASE_TABLE = "categories";
    public static final String CATEGORY_NAME_COLUMN = "category_name";

    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + CATEGORIES_DATABASE_TABLE + " ("
            + BaseColumns._ID + " integer primary key autoincrement, "
            + CATEGORY_NAME_COLUMN + " text not null );";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);

        ContentValues values = new ContentValues();
        values.put(CATEGORY_NAME_COLUMN, "Work");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Shopping");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Education");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Sport");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Rest");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Entertainment");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Reading");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
        values.put(CATEGORY_NAME_COLUMN, "Cooking");
        db.insert(CATEGORIES_DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXIST " + CATEGORIES_DATABASE_TABLE);
        onCreate(db);
    }
}

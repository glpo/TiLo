package com.app.tilo.timelogger.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBTemplates {

    private static DBHelper dbHelper;

    public DBTemplates(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void addNewCategory(String categoryName) {
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(DBHelper.CATEGORY_NAME_COLUMN, categoryName);

        sdb.insert(DBHelper.CATEGORIES_DATABASE_TABLE, null, newValues);
        sdb.close();
    }

    public List<String> getAllCategories() {
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        List<String> categories = new ArrayList<>();
        Cursor cursor = sdb.query(DBHelper.CATEGORIES_DATABASE_TABLE, new String[] { DBHelper.CATEGORY_NAME_COLUMN },
                null, null,
                null, null, null) ;

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                categories.add(cursor.getString(cursor.getColumnIndex(DBHelper.CATEGORY_NAME_COLUMN)));
                cursor.moveToNext();
            }
        }
        sdb.close();
        return categories;
    }
}

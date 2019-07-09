package com.example.myapplication.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myapplication.data.TodoContract.TodoEntry;


public class DAO {

    TodoDbHelper dbHelper;
    SQLiteDatabase database;

    public DAO(Context context) {
        dbHelper = new TodoDbHelper(context);
    }

    public void openDb(){
        database = dbHelper.getWritableDatabase();
    }
    public void closeDb(){
        database.close();
    }
    public void createRow(String mtitle, String msubtitle){
        ContentValues values = new ContentValues();
        values.put(TodoEntry.COLUMN_NAME_TITLE,mtitle);
        values.put(TodoEntry.COLUMN_NAME_SUBTITLE,msubtitle);
        database.insert(TodoEntry.TABLE_NAME,null,values);

    }
    public  String readRow(){
        Cursor cursor =
                //database.rawQuery("select * from notes",null);
                database.query(TodoEntry.TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToLast();
        int titleIndex = cursor.getColumnIndexOrThrow(TodoEntry.COLUMN_NAME_TITLE);
        int subtitleIndex = cursor.getColumnIndexOrThrow(TodoEntry.COLUMN_NAME_SUBTITLE);

        String title = cursor.getString(titleIndex);
        String subtitle = cursor.getString(subtitleIndex);


        return title+"\n"+subtitle;
    }
    public void updateRow(){}
    public void deleteRow(){}



}

package com.btcsc.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DatabaseList {
    Context context;
    public DatabaseList(Context context) {
        this.context = context;
    }

    private SQLiteDatabase openDB(){
        return context.openOrCreateDatabase("Listplanning.db",
                Context.MODE_PRIVATE,null);
    }

    public void closeDB(SQLiteDatabase db){
        db.close();
    }

    public void createTable(){
        SQLiteDatabase db = openDB();
        String sql1 = "create table if not exists tblList(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "LIST TEXT)";
        db.execSQL(sql1);
        closeDB(db);
    }
    public void insertList(ListPlanning  listPlanning){
        SQLiteDatabase db = openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("LIST",listPlanning.name);
        db.insert("tblList", null, contentValues);
        closeDB(db);
    }
    public void deleteList(int id){
        SQLiteDatabase db = openDB();
        String[] ID = {String.valueOf(id)};
        db.delete("tblList","ID=?",ID);
        closeDB(db);
    }

    public ArrayList<ListPlanning> getAllList(){
        ArrayList<ListPlanning> tmp = new ArrayList<>();
        SQLiteDatabase db = openDB();
        String sql = "SELECT * FROM tblList";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            tmp.add(new ListPlanning(name,id));
        }
        closeDB(db);
        return tmp;
    }
}

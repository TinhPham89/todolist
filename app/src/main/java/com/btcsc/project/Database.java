package com.btcsc.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class Database  {
    Context context;


    public Database(Context context) {
        this.context = context;
    }

    private SQLiteDatabase openDB(){
        return context.openOrCreateDatabase("Planning.db",
                Context.MODE_PRIVATE,null);
    }

    public void closeDB(SQLiteDatabase db){
        db.close();
    }

    public void createTable(){
        SQLiteDatabase db = openDB();
        String sql1 = "create table if not exists tblPlanningBook("+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "SUBJECT TEXT," +
                "PLANNING TEXT,"+
                "STATUS INTEGER,"+
                "DATE TEXT,"+
                "TIME TEXT,"+
                "LIST TEXT)";
        db.execSQL(sql1);
        closeDB(db);
    }
    public void insertPlanning(Planning  planning){
        SQLiteDatabase data = openDB();
        ContentValues  contentValues = new ContentValues();
        contentValues.put("SUBJECT",planning.subject);
        contentValues.put("PLANNING",planning.plan);
        contentValues.put("DATE",planning.date);
        contentValues.put("STATUS",planning.status);
        contentValues.put("TIME",planning.time);
        contentValues.put("LIST",planning.list);
        data.insert("tblPlanningBook",null,contentValues);
        closeDB(data);
    }
    public void updatePlanning(Planning planning){
        SQLiteDatabase data = openDB();
        ContentValues  contentValues = new ContentValues();
        contentValues.put("SUBJECT",planning.subject);
        contentValues.put("PLANNING",planning.plan);
        contentValues.put("DATE",planning.date);
        contentValues.put("STATUS",planning.status);
        contentValues.put("TIME",planning.time);
        contentValues.put("LIST",planning.list);
        String[] ID = {String.valueOf(planning.id)};
        data.update("tblPlanningBook",contentValues,"ID=?",ID);
        closeDB(data);
    }

    public void deletePlanning(Planning planning){
        SQLiteDatabase db = openDB();

        String sql = "SELECT * FROM tblPlanningBook";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String subject = cursor.getString(1);
            String plan = cursor.getString(2);
            int status = cursor.getInt(3);
            String  date = cursor.getString(4);
            String time = cursor.getString(5);
            String list = cursor.getString(6);
            if(subject.equals(planning.subject)
                    &&plan.equals(planning.plan)
                    &&status==planning.status
                    && date.equals(planning.date)
                    &&time.equals(planning.time)
                    &&list.equals(planning.list))
            {
                planning.id=id;
                String ID[]= {String.valueOf(planning.id)};
                db.delete("tblPlanningBook","ID=?",ID);
               break;
            }
            else
            {
                continue;
            }

        }

        closeDB(db);
    }

    public ArrayList<Planning> getAllPlanningOverDue(){
        ArrayList<Planning> tmp = new ArrayList<>();
        SQLiteDatabase db = openDB();

        String sql = "SELECT * FROM tblPlanningBook";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String subject = cursor.getString(1);
            String plan = cursor.getString(2);
            int status = cursor.getInt(3);
            String  date = cursor.getString(4);
            String time = cursor.getString(5);
            String list = cursor.getString(6);

            if(status==-1)
            {

                tmp.add(new Planning(subject, plan, date, time, id,status,list));
            }
            else {
                continue;
            }
        }
        closeDB(db);
        return tmp;
    }
    public ArrayList<Planning> getAllPlanningToday(){
        ArrayList<Planning> tmp = new ArrayList<>();
        SQLiteDatabase db = openDB();

        String sql = "SELECT * FROM tblPlanningBook";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String subject = cursor.getString(1);
            String plan = cursor.getString(2);
            int status = cursor.getInt(3);
            String  date = cursor.getString(4);
            String time = cursor.getString(5);
            String list = cursor.getString(6);
            if(status==0)
            {
                tmp.add(new Planning(subject, plan, date, time, id,status,list));
            }
            else {
                continue;
            }
        }
        closeDB(db);
        return tmp;
    }
    public ArrayList<Planning> getAllPlanningTomorrow(){
        ArrayList<Planning> tmp = new ArrayList<>();
        SQLiteDatabase db = openDB();

        String sql = "SELECT * FROM tblPlanningBook";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String subject = cursor.getString(1);
            String plan = cursor.getString(2);
            int status = cursor.getInt(3);
            String  date = cursor.getString(4);
            String time = cursor.getString(5);
            String list = cursor.getString(6);
            if(status==1)
            {
                tmp.add(new Planning(subject, plan, date, time, id,status,list));
            }
            else {
                continue;
            }
        }
        closeDB(db);
        return tmp;
    }

    public Planning getOnePlanning(int id){
        Planning planning = new Planning();
        SQLiteDatabase db = openDB();
        String sql = "SELECT * FROM tblPlanningBook WHERE ID = " + id;
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        planning.id = cursor.getInt(0);
        planning.subject = cursor.getString(1);
        planning.plan = cursor.getString(2);
        planning.status = cursor.getInt(3);
        planning.date=cursor.getString(4);
        planning.time = cursor.getString(5);
        planning.list = cursor.getString(6);
        closeDB(db);
        return planning;
    }
    public int getID(Planning planning)
    {
        int ID =0;
        SQLiteDatabase db = openDB();

        String sql = "SELECT * FROM tblPlanningBook";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String subject = cursor.getString(1);
            String plan = cursor.getString(2);
            int status = cursor.getInt(3);
            String  date = cursor.getString(4);
            String time = cursor.getString(5);
            String list = cursor.getString(6);
            if(subject.equals(planning.subject)
                    &&plan.equals(planning.plan)
                    &&status==planning.status
                    && date.equals(planning.date)
                    &&time.equals(planning.time)
                    &&list.equals(planning.list))
            {
                planning.id=id;
                ID = id;
                break;
            }
            else
            {
                continue;
            }

        }

        closeDB(db);
        return ID;
    }

    public ArrayList<Planning> getList( String danhsach){
        ArrayList<Planning> tmp = new ArrayList<>();
        SQLiteDatabase db = openDB();

        String sql = "SELECT * FROM tblPlanningBook";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){

            int id = cursor.getInt(0);
            String subject = cursor.getString(1);
            String plan = cursor.getString(2);
            int status = cursor.getInt(3);
            String  date = cursor.getString(4);
            String time = cursor.getString(5);
            String list = cursor.getString(6);
            if(list.equals(danhsach))
            {
                tmp.add(new Planning(subject, plan, date, time, id,status,list));
            }
            else {
                continue;
            }
        }
        closeDB(db);
        return tmp;
    }


}

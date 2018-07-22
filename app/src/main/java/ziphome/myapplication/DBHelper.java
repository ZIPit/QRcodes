package ziphome.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DBclients";
    public static final String TABLE_CLIENTS = "clients";

    public static final String KEYID = "_id";
    public static final String CLIENTID = "Clientid";
    public static final String CLIENTNAME = "Client_name";
    public static final String EMAIL = "Email";
    public static final String SEMINARID = "SeminarID";

    public static final String MODE = "Mode";  // 0 - automatic insert , 1 - manual insert


    public static final String TABLE_EVENT = "event";
    public static final String EVENTID = "Eventid";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_CLIENTS+"("+ KEYID +
                " integer primary key,"+ CLIENTID +" text,"+ CLIENTNAME +" text," + EMAIL + " text, " +
                SEMINARID +" text," +MODE+" text)"
        );
        db.execSQL("create table "+TABLE_EVENT+"("+ KEYID +
                " integer primary key,"+ EVENTID +" text)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_CLIENTS);
        db.execSQL("drop table if exists "+TABLE_EVENT);
        onCreate(db);

    }

    public String addData(String[] mas, String mode){

        String clName;

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.CLIENTID, mas[0]);
        contentValues.put(DBHelper.CLIENTNAME, mas[1]);
        contentValues.put(DBHelper.EMAIL, mas[2]);
        contentValues.put(DBHelper.SEMINARID, mas[3]);
        contentValues.put(DBHelper.MODE, mode);
        long result = database.insert(DBHelper.TABLE_CLIENTS,null,  contentValues);
        if (result==-1){
          clName="";
        }
        else clName=mas[1];


        return clName;
    };

    public String getEvent(){
        String eventid;
        eventid=null;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+ DBHelper.TABLE_EVENT,null);
        if (cursor.moveToFirst()) {
            int indexEv = cursor.getColumnIndex(DBHelper.EVENTID);
            eventid = cursor.getString(indexEv);
        }

        cursor.close();
        ;

        Log.d("Eventcheck","Null "+eventid);

        return eventid;
    };


    public Boolean setEvent(String value) {
        long result=-1;
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try {

            database.delete(TABLE_EVENT, null, null);  // Remove old value
            contentValues.put(DBHelper.EVENTID, value);
            result = database.insert(DBHelper.TABLE_EVENT, null, contentValues);

        }
        catch (Exception e){
            result = -1;
        }
        finally {
            if (result ==-1){ return false;}
            else { return true;}
        }
    }  // END of SetEvent

    public ArrayList<ClientData> clientlistview() {

        ArrayList<ClientData> arrayList;
        arrayList = new ArrayList();
        ClientData cldata;


        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+DBHelper.TABLE_CLIENTS, null);

        if (cursor.moveToFirst()){
            int idIndex = cursor.getColumnIndex(DBHelper.KEYID);
            int cliIDIndex = cursor.getColumnIndex(DBHelper.CLIENTID);
            int clNameIndex = cursor.getColumnIndex(DBHelper.CLIENTNAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.EMAIL);
            int semidIndex = cursor.getColumnIndex(DBHelper.SEMINARID);

            do {
                cldata = new ClientData(
                        cursor.getString(cliIDIndex),
                        cursor.getString(clNameIndex),
                        cursor.getString(emailIndex),
                        cursor.getString(semidIndex)
                );
                arrayList.add(cldata);
                Log.d("DBRead","KEYID "+ cursor.getInt(idIndex) + ", CLIENTNAME = " + cursor.getString(clNameIndex)+"");

            }
            while (cursor.moveToNext());

        }
        else Log.d("DBRead","0 Rows");

        cursor.close();
        return arrayList;
    }

    public String[] showlastClient(){
        String[] mas;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " +DBHelper.TABLE_CLIENTS+" ORDER BY ROWID DESC LIMIT 1",null);
        if (cursor.moveToFirst()) {

            int cliIDIndex = cursor.getColumnIndex(DBHelper.CLIENTID);
            int clNameIndex = cursor.getColumnIndex(DBHelper.CLIENTNAME);
            int emailIndex = cursor.getColumnIndex(DBHelper.EMAIL);
            int semidIndex = cursor.getColumnIndex(DBHelper.SEMINARID);
            mas = new String[]{
                    cursor.getString(cliIDIndex),
                    cursor.getString(clNameIndex),
                    cursor.getString(emailIndex),
                    cursor.getString(semidIndex)
            };
        }
        else  mas= new String[]{ "","","",""};

        cursor.close();
        return mas;
    } ;



}

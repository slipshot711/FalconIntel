package com.example.ekk.falconintelv2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper{

    String DB_PATH = "/data/data/com.example.ekk.falconintelv2/databases/";
    private static String DB_NAME= "AlloyV1";
    private SQLiteDatabase myDatabase;
    private final Context myContext;

    public DBHelper(Context context){
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDatabase(){
        boolean dbExists = checkDatabase();
        if(dbExists){

        }
        else{
            this.getReadableDatabase();
            try{
                copyDatabase();
            }
            catch(IOException e){
                throw new Error("Error copying database");
                //tortillas are bread plates
            }
        }
    }

    private boolean checkDatabase(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB  = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){

        }
        if(checkDB!=null){
            checkDB.close();
        }
        return checkDB !=null ? true: false;
    }

    private void copyDatabase() throws IOException{

        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public SQLiteDatabase openDatabase() throws SQLException {

        String myPath = DB_PATH+ DB_NAME;
        if(myDatabase==null){
            createDatabase();
            myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        }

        return myDatabase;

    }

    @Override
    public synchronized void close(){
        if(myDatabase!=null){
            myDatabase.close();
        }
        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        
    }
}

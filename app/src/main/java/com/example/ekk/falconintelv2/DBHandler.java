package com.example.ekk.falconintelv2;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TableRow;

public class DBHandler{

    private static final String TAG = "DBHandler";

    public static final String ALLOY_ID = "_id";
    public static final String ALLOY_NAME = "name";
    public static final String MELTING_POINT = "melting_pt";
    public static final String TENSILE_STRENGTH = "tensile_strength";
    public static final String FATIGUE_STRENGTH = "fatigue_strength";
    public static final String YIELD_STRENGTH = "yield_strength";
    public static final String PERCENT_ELONGATION = "pt_elongation";
    public static final String CORROSION_RESITANCE = "cor_resist";
    public static final String HEAT_TREATMENT = "heat_treat";
    public static final String THERMAL_CONDUCTIVITY = "thermal_con";
    public static final String ELECTRIC_CONDUCTIVITY = "electric_con";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alloys";
    public static final String TABLE_ALLOY_DETAILS = "alloyDetails";


    public static final String[] ALL_KEYS = new String[]{
            ALLOY_ID,
            ALLOY_NAME,
            MELTING_POINT,
            TENSILE_STRENGTH,
            FATIGUE_STRENGTH,
            YIELD_STRENGTH,
            PERCENT_ELONGATION,
            CORROSION_RESITANCE,
            HEAT_TREATMENT,
            THERMAL_CONDUCTIVITY,
            ELECTRIC_CONDUCTIVITY
    };

    public static final int COL_ALLOY_ID = 0;
    public static final int COL_ALLOY_NAME = 1;
    public static final int COL_MELTING_POINT = 2;

    private static final String CREATE_ALLOY_DETAIL_TABLE =
            "CREATE TABLE " + TABLE_ALLOY_DETAILS + "("
            + ALLOY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALLOY_NAME + " TEXT NOT NULL, "
            + MELTING_POINT + " DOUBLE NOT NULL,"
            + TENSILE_STRENGTH + " DOUBLE NOT NULL,"
            + FATIGUE_STRENGTH + " DOUBLE NOT NULL,"
            + YIELD_STRENGTH + " DOUBLE NOT NULL,"
            + PERCENT_ELONGATION + " DOUBLE NOT NULL,"
            + CORROSION_RESITANCE + " INTEGER NOT NULL,"
            + HEAT_TREATMENT + " INTEGER NOT NULL,"
            + THERMAL_CONDUCTIVITY + " INTEGER NOT NULL,"
            + ELECTRIC_CONDUCTIVITY + " INTEGER NOT NULL" + ")";

    private final Context context;
    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBHandler(Context ctx){
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    public DBHandler open(){
        db = myDBHelper.getWritableDatabase();
        return this;
    }


    public void close(){
        myDBHelper.close();
    }

    public long insertRow(String name, double melt_pt, double tStrength, double fStrength, double yStrength,
                          double pElong, int corResist, int heatTreat, int tConduct, int eConduct){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ALLOY_NAME, name);
        initialValues.put(MELTING_POINT, melt_pt);
        initialValues.put(TENSILE_STRENGTH, tStrength);
        initialValues.put(FATIGUE_STRENGTH, fStrength);
        initialValues.put(YIELD_STRENGTH, yStrength);
        initialValues.put(PERCENT_ELONGATION, pElong);
        initialValues.put(CORROSION_RESITANCE, corResist);
        initialValues.put(HEAT_TREATMENT, heatTreat);
        initialValues.put(THERMAL_CONDUCTIVITY, tConduct);
        initialValues.put(ELECTRIC_CONDUCTIVITY, eConduct);

        return db.insert(TABLE_ALLOY_DETAILS, null, initialValues);
    }

    public boolean deleteRow(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        return db.delete(TABLE_ALLOY_DETAILS, loc, null) != 0;
    }

    public void deleteAll(){
        Cursor c = getAllRows();
        long rowID = c.getColumnIndexOrThrow(ALLOY_ID);
        if(c.moveToFirst()){
            do{
                deleteRow(c.getLong((int)rowID));
            }
            while(c.moveToNext());
        }
        c.close();
    }


    public Cursor getAllRows(){
        String loc = null;
        Cursor c =  db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null, null, null,null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }

    public Cursor filterData(String max, String min){
        String query = "SELECT * FROM " + TABLE_ALLOY_DETAILS + " WHERE " + MELTING_POINT + " BETWEEN " + min + " AND " + max;
        Cursor c = db.rawQuery(query, null);

        if(c!= null){
            c.moveToFirst();
        }
        return c;
    }

    public String getName(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_ALLOY_NAME);
    }

    public Double getMp(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getDouble(COL_MELTING_POINT);
    }

    public int getSize(){
        return (int) db.getPageSize();
    }

    public boolean seeIfAlreadyExists(String name){
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, new String[]{ALLOY_NAME}, ALLOY_NAME.replace("\\s+","") + " = ?", new String[] {name.replace("\\s+","")}, null, null, null, null);
        if(c.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateRow(long rowID, String name, String melt_pt){
        String loc = ALLOY_ID + "=" + rowID;
        ContentValues newValues = new ContentValues();
        newValues.put(ALLOY_NAME, name);
        newValues.put(MELTING_POINT, melt_pt);
        return db.update(TABLE_ALLOY_DETAILS, newValues, loc, null) != 0;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_ALLOY_DETAIL_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLOY_DETAILS);

            onCreate(db);
        }
    }
}

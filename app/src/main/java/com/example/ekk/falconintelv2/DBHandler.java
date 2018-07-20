package com.example.ekk.falconintelv2;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Text;

public class DBHandler{

    private static final String TAG = "DBHandler";

    public static final String ALLOY_ID = "_id";
    public static final String ALLOY_NAME = "name";
    public static final String MIN_TENSILE_STRENGTH = "min_tensile_strength";
    public static final String MIN_FATIGUE_STRENGTH = "min_fatigue_strength";
    public static final String MIN_YIELD_STRENGTH = "min_yield_strength";
    public static final String MIN_PERCENT_ELONGATION = "min_pt_elongation";
    public static final String MAX_TENSILE_STRENGTH = "max_tensile_strength";
    public static final String MAX_FATIGUE_STRENGTH = "max_fatigue_strength";
    public static final String MAX_YIELD_STRENGTH = "max_yield_strength";
    public static final String MAX_PERCENT_ELONGATION = "max_pt_elongation";
    public static final String CORROSION_RESISTANCE = "cor_resist";
    public static final String THERMAL_CONDUCTIVITY = "thermal_con";
    public static final String ELECTRIC_CONDUCTIVITY = "electric_con";
    public static final String SILICON = "silicon";
    public static final String IRON = "iron";
    public static final String COPPER = "copper";
    public static final String MANGANESE = "manganese";
    public static final String MAGNESIUM = "magnesium";
    public static final String ZINC = "zinc";
    public static final String TITANIUM = "titanium";

    public static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AlloyV1";
    public static final String TABLE_ALLOY_DETAILS = "alloyDetails";


    public static final String[] ALL_KEYS = new String[]{
            ALLOY_ID,
            ALLOY_NAME,
            MIN_TENSILE_STRENGTH,
            MIN_FATIGUE_STRENGTH,
            MIN_YIELD_STRENGTH,
            MIN_PERCENT_ELONGATION,
            MAX_TENSILE_STRENGTH,
            MAX_FATIGUE_STRENGTH,
            MAX_YIELD_STRENGTH,
            MAX_PERCENT_ELONGATION,
            CORROSION_RESISTANCE,
            THERMAL_CONDUCTIVITY,
            ELECTRIC_CONDUCTIVITY,
            SILICON,
            IRON,
            COPPER,
            MANGANESE,
            MAGNESIUM,
            ZINC,
            TITANIUM
    };

    public static final int COL_ALLOY_ID = 0;
    public static final int COL_ALLOY_NAME = 1;
    public static final int COL_MIN_TENSILE_STRENGTH = 2;
    public static final int COL_MIN_FATIGUE_STRENGTH = 3;
    public static final int COL_MIN_YIELD_STRENGTH = 4;
    public static final int COL_MIN_PERCENT_ELONGATION = 5;
    public static final int COL_MAX_TENSILE_STRENGTH = 6;
    public static final int COL_MAX_FATIGUE_STRENGTH = 7;
    public static final int COL_MAX_YIELD_STRENGTH = 8;
    public static final int COL_MAX_PERCENT_ELONGATION = 9;
    public static final int COL_CORROSION_RESISTANCE = 10;
    public static final int COL_THERMAL_CONDUCTIVITY = 11;
    public static final int COL_ELECTRIC_CONDUCTIVITY = 12;
    public static final int COL_SILICON = 13;
    public static final int COL_IRON = 14;
    public static final int COL_COPPER = 15;
    public static final int COL_MANGANESE = 16;
    public static final int COL_MAGNESIUM = 17;
    public static final int COL_ZINC = 18;
    public static final int COL_TITANIUM = 19;


    private static final String CREATE_ALLOY_DETAIL_TABLE =
            "CREATE TABLE " + TABLE_ALLOY_DETAILS + "("
            + ALLOY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ALLOY_NAME + " TEXT NOT NULL, "
            + MIN_TENSILE_STRENGTH + " DOUBLE,"
            + MIN_FATIGUE_STRENGTH + " DOUBLE,"
            + MIN_YIELD_STRENGTH + " DOUBLE,"
            + MIN_PERCENT_ELONGATION + " DOUBLE,"
            + MAX_TENSILE_STRENGTH + " DOUBLE,"
            + MAX_FATIGUE_STRENGTH + " DOUBLE,"
            + MAX_YIELD_STRENGTH + " DOUBLE,"
            + MAX_PERCENT_ELONGATION + " DOUBLE,"
            + CORROSION_RESISTANCE + " TEXT,"
            + THERMAL_CONDUCTIVITY + " DOUBLE,"
            + ELECTRIC_CONDUCTIVITY + " DOUBLE, "
            + SILICON + " DOUBLE, "
            + IRON + " DOUBLE, "
            + COPPER + " DOUBLE, "
            + MANGANESE + " DOUBLE, "
            + MAGNESIUM + " DOUBLE, "
            + ZINC + " DOUBLE, "
            + TITANIUM + " DOUBLE "
                    + ")";

    private final Context context;
    private DBHelper myDBHelper;
    private SQLiteDatabase db;

    public DBHandler(Context ctx){
        this.context = ctx;
        myDBHelper = new DBHelper(context);
    }

    public DBHandler open(){
        db = myDBHelper.openDatabase();
        return this;
    }


    public void close(){
        myDBHelper.close();
    }

    public long insertRow(String name, double tStrengthMax, double fStrengthMax, double yStrengthMax,
                          double pElongMax,double tStrengthMin, double fStrengthMin, double yStrengthMin,
                          double pElongMin, int corResist, double tConduct, double eConduct,
                          double silicon, double iron, double copper, double manganese, double magnesium,
                          double zinc, double titanium){
        ContentValues initialValues = new ContentValues();
        initialValues.put(ALLOY_NAME, name);
        initialValues.put(MIN_TENSILE_STRENGTH, tStrengthMin);
        initialValues.put(MIN_FATIGUE_STRENGTH, fStrengthMin);
        initialValues.put(MIN_YIELD_STRENGTH, yStrengthMin);
        initialValues.put(MIN_PERCENT_ELONGATION, pElongMin);
        initialValues.put(MAX_TENSILE_STRENGTH, tStrengthMax);
        initialValues.put(MAX_FATIGUE_STRENGTH, fStrengthMax);
        initialValues.put(MAX_YIELD_STRENGTH, yStrengthMax);
        initialValues.put(MAX_PERCENT_ELONGATION, pElongMax);
        initialValues.put(CORROSION_RESISTANCE, corResist);
        initialValues.put(THERMAL_CONDUCTIVITY, tConduct);
        initialValues.put(ELECTRIC_CONDUCTIVITY, eConduct);
        initialValues.put(SILICON, silicon);
        initialValues.put(IRON, iron);
        initialValues.put(COPPER, copper);
        initialValues.put(MANGANESE, manganese);
        initialValues.put(MAGNESIUM, magnesium);
        initialValues.put(ZINC, zinc);
        initialValues.put(TITANIUM, titanium);

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
        String query = "SELECT  * FROM " + TABLE_ALLOY_DETAILS;
        Cursor c =  db.rawQuery(query, null);
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

    public Cursor filterData(String t, String f,
                             String y, String p){
        String query;
        if(Double.parseDouble(t)==0 && Double.parseDouble(f)==0 && Double.parseDouble(y)==0 && Double.parseDouble(p)==0){
            query = "SELECT  * FROM " + TABLE_ALLOY_DETAILS;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE "+ MAX_FATIGUE_STRENGTH + " > " + f +
                    " AND " + MAX_YIELD_STRENGTH + " > " + y  +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)==0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_YIELD_STRENGTH + " > " + y  +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)==0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_FATIGUE_STRENGTH + " > " + f +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_FATIGUE_STRENGTH + " > " + f +
                    " AND " + MAX_YIELD_STRENGTH + " > " + y;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)==0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_YIELD_STRENGTH + " > " + y  +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)==0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_FATIGUE_STRENGTH + " > " + f +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_FATIGUE_STRENGTH + " > " + f +
                    " AND " + MAX_YIELD_STRENGTH + " > " + y;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)==0 && Double.parseDouble(y)==0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)==0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_YIELD_STRENGTH + " > " + y;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)==0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_FATIGUE_STRENGTH + " > " + f;
        }
        else if(Double.parseDouble(t)!=0 && Double.parseDouble(f)==0 && Double.parseDouble(y)==0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)!=0 && Double.parseDouble(y)==0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_FATIGUE_STRENGTH + " > " + f;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)==0 && Double.parseDouble(y)!=0 && Double.parseDouble(p)==0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_YIELD_STRENGTH + " > " + y;
        }
        else if(Double.parseDouble(t)==0 && Double.parseDouble(f)==0 && Double.parseDouble(y)==0 && Double.parseDouble(p)!=0){
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_PERCENT_ELONGATION + " > " + p;
        }
        else{
            query = "SELECT * FROM " + TABLE_ALLOY_DETAILS +
                    " WHERE " + MAX_TENSILE_STRENGTH + " > " + t +
                    " AND " + MAX_FATIGUE_STRENGTH + " > " + f +
                    " AND " + MAX_YIELD_STRENGTH + " > " + y  +
                    " AND " + MAX_PERCENT_ELONGATION + " > " + p;
        }

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

    public String getMaxTensileStrength(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MAX_TENSILE_STRENGTH);
    }

    public String getMaxFatigueStrength(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MAX_FATIGUE_STRENGTH);
    }

    public String getMaxYieldStrength(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MAX_YIELD_STRENGTH);
    }

    public String getMaxPercentElongation(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MAX_PERCENT_ELONGATION);
    }

    public String getMinTensileStrength(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MIN_TENSILE_STRENGTH);
    }

    public String getMinFatigueStrength(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MIN_FATIGUE_STRENGTH);
    }

    public String getMinYieldStrength(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MIN_YIELD_STRENGTH);
    }

    public String getMinPercentElongation(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MIN_PERCENT_ELONGATION);
    }

    public String getThermalConductivity(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_THERMAL_CONDUCTIVITY);
    }

    public String getElectricConductivity(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_ELECTRIC_CONDUCTIVITY);
    }

    public String getSilicon(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_SILICON);
    }

    public String getIron(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_IRON);
    }

    public String getCopper(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_COPPER);
    }

    public String getManganese(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MANGANESE);
    }

    public String getMagnesium(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_MAGNESIUM);
    }

    public String getZinc(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_ZINC);
    }

    public String getTitanium(long rowID){
        String loc = ALLOY_ID + "=" + rowID;
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, ALL_KEYS, loc, null,null,null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c.getString(COL_TITANIUM);
    }




    public int getSize(){
        return (int) db.getPageSize();
    }

    public String test(){
        return "test";
    }

    public boolean seeIfAlreadyExists(String name){
        Cursor c = db.query(true, TABLE_ALLOY_DETAILS, new String[]{ALLOY_NAME}, ALLOY_NAME + " = ?", new String[] {name}, null, null, null, null);
        if(c.moveToFirst()){
            return true;
            //Ryan Reynolds? Ryan Reynolds.
        }
        else{
            return false;
        }
    }

    public boolean updateRow(long rowID, String name, double tStrengthMax, double fStrengthMax, double yStrengthMax,
                             double pElongMax,double tStrengthMin, double fStrengthMin, double yStrengthMin,
                             double pElongMin, int corResist, double tConduct, double eConduct,
                             double silicon, double iron, double copper, double manganese, double magnesium,
                             double zinc, double titanium){
        String loc = ALLOY_ID + "=" + rowID;
        ContentValues newValues = new ContentValues();
        newValues.put(ALLOY_NAME, name);
        newValues.put(MIN_TENSILE_STRENGTH, tStrengthMin);
        newValues.put(MIN_FATIGUE_STRENGTH, fStrengthMin);
        newValues.put(MIN_YIELD_STRENGTH, yStrengthMin);
        newValues.put(MIN_PERCENT_ELONGATION, pElongMin);
        newValues.put(MAX_TENSILE_STRENGTH, tStrengthMax);
        newValues.put(MAX_FATIGUE_STRENGTH, fStrengthMax);
        newValues.put(MAX_YIELD_STRENGTH, yStrengthMax);
        newValues.put(MAX_PERCENT_ELONGATION, pElongMax);
        newValues.put(CORROSION_RESISTANCE, corResist);
        newValues.put(THERMAL_CONDUCTIVITY, tConduct);
        newValues.put(ELECTRIC_CONDUCTIVITY, eConduct);
        newValues.put(SILICON, silicon);
        newValues.put(IRON, iron);
        newValues.put(COPPER, copper);
        newValues.put(MANGANESE, manganese);
        newValues.put(MAGNESIUM, magnesium);
        newValues.put(ZINC, zinc);
        newValues.put(TITANIUM, titanium);

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


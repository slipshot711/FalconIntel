package com.example.ekk.falconintelv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MAS extends AppCompatActivity {

    DBHandler myDB;
    SimpleCursorAdapter myCursorAdapter;
    final Bundle filters = new Bundle();
    double mpMax = Double.MAX_VALUE;
    double mpMin = 0;
    double tStrength = Double.MAX_VALUE;
    double fStrength = Double.MAX_VALUE;
    double yStrength = Double.MAX_VALUE;
    double percentEl = Double.MAX_VALUE;
    int corResist = 0;
    int heatTreat = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas);

        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);

        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();


        try{
//            if(extrasBundle.containsKey("max_melting_pt")){
//                mpMax = extrasBundle.getDouble("max_melting_pt");
//                filters.putDouble("max_melting_pt", mpMax);
//            }
//            if(extrasBundle.containsKey("min_melting_pt")){
//                mpMin = extrasBundle.getDouble("min_melting_pt");
//                filters.putDouble("min_melting_pt", mpMin);
//            }
            if(extrasBundle.containsKey("tensile_strength")){
                tStrength = extrasBundle.getDouble("tensile_strength");
                filters.putDouble("tensile_strength", tStrength);
            }
            if(extrasBundle.containsKey("fatigue_strength")){
                fStrength = extrasBundle.getDouble("fatigue_strength");
                filters.putDouble("fatigue_strength", fStrength);
            }
            if(extrasBundle.containsKey("yield_strength")){
                yStrength = extrasBundle.getDouble("yield_strength");
                filters.putDouble("yield_strength", yStrength);
            }
            if(extrasBundle.containsKey("elongation")){
                percentEl = extrasBundle.getDouble("elongation");
                filters.putDouble("elongation", percentEl);
            }
            if(extrasBundle.containsKey("heat_treatment")){
                heatTreat = extrasBundle.getInt("heat_treatment");
                filters.putInt("heat_treatment", heatTreat);
            }
            if(extrasBundle.containsKey("corrosion_resistance")){
                corResist = extrasBundle.getInt("corrosion_resistance");
                filters.putInt("corrosion_resistance", corResist);
            }

        }
        catch (NullPointerException e){
            mpMax = Double.MAX_VALUE;
            mpMin = 0;
            tStrength = Double.MAX_VALUE;
            fStrength = Double.MAX_VALUE;
            yStrength = Double.MAX_VALUE;
            percentEl = Double.MAX_VALUE;
            corResist = 0;
            heatTreat = 0;
        }


        openDB();


        populateListView(mpMax,mpMin);
        listViewItemLongClick();


    }


    public void openMAS_F(View view){
        Intent masf = new Intent(getBaseContext(), MAS_F.class);
        masf.putExtras(filters);
        masf.putExtra("key","value");
        startActivityForResult(masf, 1);
    }

    public void openMAS_A(View view){
        Intent masa = new Intent(getBaseContext(), MAS_A.class);
        startActivity(masa);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }

    private void openDB(){
        myDB = new DBHandler(this);
        myDB.open();
    }

    private void closeDB(){
        myDB.close();
    }

    private void populateListView(double max, double min){

        String[] fromFieldNames = new String[]{DBHandler.ALLOY_NAME};
        int[] toViewIDs = new int[]{R.id.textView48};

        Cursor cursor = myDB.filterData(Double.toString(max), Double.toString(min));
        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, fromFieldNames,toViewIDs,0);

        ListView list = findViewById(R.id.listView);
        list.setAdapter(myCursorAdapter);

    }

    private void listViewItemLongClick() {
        final ListView myList = findViewById(R.id.listView);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position,final long id) {

                LayoutInflater inflater = getLayoutInflater();

                Vibrator vb = (Vibrator) getSystemService(MAS.this.VIBRATOR_SERVICE);
                vb.vibrate(50);

                View alertLayout = inflater.inflate(R.layout.alert_layout, null);
                final TextView Title = alertLayout.findViewById(R.id.textView50);
                Title.setText("Are you sure you want to delete " + myDB.getName(id) + "?");

                AlertDialog.Builder builder = new AlertDialog.Builder(MAS.this);
                builder.setCancelable(true);
                builder.setView(alertLayout);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDB.deleteRow(id);
                                populateListView(mpMax, mpMin);
                            }
                        });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return false;
            }
        });
    }





}
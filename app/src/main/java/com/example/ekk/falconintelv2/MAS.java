package com.example.ekk.falconintelv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FilterQueryProvider;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MAS extends AppCompatActivity {

    DBHandler myDB;
    SimpleCursorAdapter myCursorAdapter;
    final Bundle filters = new Bundle();
    final Bundle details  =new Bundle();
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


        if(savedInstanceState!=null) {
            mListState = savedInstanceState.getParcelable(LIST_STATE);
            getListView().onRestoreInstanceState(mListState);
        }


        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();


        TextView test = findViewById(R.id.textView56);

        try{
            if(extrasBundle.containsKey("tensile_strength")){
                tStrength = extrasBundle.getDouble("tensile_strength");
                filters.putDouble("tensile_strength", tStrength);
                details.putDouble("tensile_strength", tStrength);
            }

            if(extrasBundle.containsKey("fatigue_strength")){
                fStrength = extrasBundle.getDouble("fatigue_strength");
                filters.putDouble("fatigue_strength", fStrength);
                details.putDouble("fatigue_strength", fStrength);
            }

            if(extrasBundle.containsKey("yield_strength")){
                yStrength = extrasBundle.getDouble("yield_strength");
                filters.putDouble("yield_strength", yStrength);
                details.putDouble("yield_strength", yStrength);
            }

            if(extrasBundle.containsKey("elongation")){
                percentEl = extrasBundle.getDouble("elongation");
                filters.putDouble("elongation", percentEl);
                details.putDouble("elongation", percentEl);
            }

            if(extrasBundle.containsKey("heat_treatment")){
                heatTreat = extrasBundle.getInt("heat_treatment");
                filters.putInt("heat_treatment", heatTreat);
                details.putInt("heat_treatment", heatTreat);
            }

            if(extrasBundle.containsKey("corrosion_resistance")){
                corResist = extrasBundle.getInt("corrosion_resistance");
                filters.putInt("corrosion_resistance", corResist);
                details.putInt("corrosion_resistance", corResist);
            }

        }
        catch (NullPointerException e){
            mpMax = 0;
            mpMin = 0;
            tStrength = 0;
            fStrength = 0;
            yStrength = 0;
            percentEl = 0;
            corResist = 0;
            heatTreat = 0;
        }


        openDB();

        populateListView(tStrength, fStrength, yStrength, percentEl);
        listViewItemLongClick();
        listViewItemClick();


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

    public void openMAS_DB(){
        Intent masdb =  new Intent(getBaseContext(), MAS_DB.class);
        masdb.putExtras(details);
        masdb.putExtra("key","value");
        startActivityForResult(masdb,1);
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
        ListView list = findViewById(R.id.listView);
        mListState = list.onSaveInstanceState();
    }

    private void openDB(){
        myDB = new DBHandler(this);
        myDB.open();
    }

    private void closeDB(){
        myDB.close();
    }

    private void populateListView(double t, double f, double y, double p){

        Cursor cursor = myDB.filterData(Double.toString(t), Double.toString(f), Double.toString(y), Double.toString(p));
        String[] fromFieldNames = new String[]{DBHandler.ALLOY_NAME};
        int[] toViewIDs = new int[]{R.id.textView111};

        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, fromFieldNames,toViewIDs,0);

        ListView list = findViewById(R.id.listView);
        list.setAdapter(myCursorAdapter);



    }

    private void populateListView(){


        Cursor cursor = myDB.getAllRows();
        String[] fromFieldNames = new String[]{DBHandler.ALLOY_NAME};
        int[] toViewIDs = new int[]{R.id.textView111};

        myCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout.item_layout, cursor, fromFieldNames,toViewIDs,0);

        ListView list = findViewById(R.id.listView);
        list.setAdapter(myCursorAdapter);

    }

    private void listViewItemClick(){


        final ListView myList = findViewById(R.id.listView);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                details.putLong("id", id);
                //aaaaaaahhhahahhahhhhhhhhahhhhhh
                openMAS_DB();
            }
        });
    }

    public void delete(final long id){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_layout, null);
        final TextView Title = alertLayout.findViewById(R.id.textView1121);
        Title.setText("Are you sure you want to delete " + myDB.getName(id) + "?");

        AlertDialog.Builder builder = new AlertDialog.Builder(MAS.this);
        builder.setCancelable(true);
        builder.setView(alertLayout);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDB.deleteRow(id);
                populateListView();
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


    }

    private void listViewItemLongClick() {
        final ListView myList = findViewById(R.id.listView);
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override public boolean onItemLongClick(AdapterView<?> parent, View viewClicked, int position,final long id) {

                LayoutInflater inflater = getLayoutInflater();

                Vibrator vb = (Vibrator) getSystemService(MAS.this.VIBRATOR_SERVICE);
                vb.vibrate(50);


                View alertLayout = inflater.inflate(R.layout.alert_layout, null);
                final TextView Title = alertLayout.findViewById(R.id.textView1121);
                Title.setText("Are you sure you want to delete " + myDB.getName(id) + "?");

                AlertDialog.Builder builder = new AlertDialog.Builder(MAS.this);
                builder.setCancelable(true);
                builder.setView(alertLayout);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myDB.deleteRow(id);
                                    populateListView();
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
                return true;
                //uh yea i sure hope it does
            }
        });

    }

    private void listViewItemLongClick(int i) {
            i++;
            if(i < 3){
                i--;
                //white dove comment\\
                //\\
            }
    }


//    public void onBackPressed(){
//
//        Intent mainActivity = new Intent(getBaseContext(), MainActivity.class);
//        startActivity(mainActivity);
//        return;
//    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }

    private static final String LIST_STATE = "listState";
    private Parcelable mListState = null;

    public ListView getListView(){
        ListView list = findViewById(R.id.listView);
        return list;
    }

    @Override
    public void onPause() {
        ListView list = findViewById(R.id.listView);
        mListState = list.onSaveInstanceState();
        super.onPause();
    }

    @Override
    protected void onStop() {
        ListView list = findViewById(R.id.listView);
        mListState = list.onSaveInstanceState();
        super.onStop();
    }

    //
//    @Override
//    public void onResume() {
//        ListView list = findViewById(R.id.listView);
//        list.setAdapter(myCursorAdapter);
//        list.onRestoreInstanceState(state);
//        super.onResume();
//    }



//    @Override
//    public void onViewCreated(final View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        // Set new items
//        lv.setAdapter(myCursorAdapter);
//        // Restore previous state (including selected item index and scroll position)
//        if(state != null) {
//            lv.onRestoreInstanceState(state);
//        }
//    }

}

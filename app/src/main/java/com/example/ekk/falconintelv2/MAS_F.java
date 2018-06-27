package com.example.ekk.falconintelv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MAS_F extends AppCompatActivity {

    DBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas__f);

        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();final Intent mas = new Intent(getBaseContext(), MAS.class);
        final Bundle filters = new Bundle();

        mas.putExtra("key","value");

        openDB();


//        final EditText mpMaxInput = findViewById(R.id.editText11);
//        final EditText mpMinInput = findViewById(R.id.editText12);

        final SeekBar tStrength = findViewById(R.id.seekBar8);
        final SeekBar fStrength = findViewById(R.id.seekBar6);
        final SeekBar yStrength = findViewById(R.id.seekBar7);
        final SeekBar percentEl = findViewById(R.id.seekBar9);
        final int[] cor = {0};
        final TextView tDisplay = findViewById(R.id.textView95);
        final TextView fDisplay = findViewById(R.id.textView89);
        final TextView yDisplay = findViewById(R.id.textView92);
        final TextView elDisplay = findViewById(R.id.textView99);
        final Spinner corResist = findViewById(R.id.spinner3);

        Button save = findViewById(R.id.button11);
        Button clear = findViewById(R.id.button10);

        String[] items = new String[]{"No Selection", "Limited", "Sufficient", "Good", "Very Good", "Excellent"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        corResist.setAdapter(adapter);
        corResist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);


                if(selectedItem=="No Selection"){
                    cor[0] = 0;
                }
                else if(selectedItem=="Limited"){
                    cor[0] = 1;
                }
                else if(selectedItem=="Sufficient"){
                    cor[0] = 2;
                }
                else if(selectedItem=="Good"){
                    cor[0] = 3;
                }
                else if(selectedItem=="Very Good"){
                    cor[0] = 4;
                }
                else if(selectedItem=="Excellent"){
                    cor[0] = 5;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        tStrength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar metalThickness, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                tDisplay.setText(Double.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar metalThickness) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar metalThickness) {

            }
        });

        fStrength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar metalThickness, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                fDisplay.setText(Double.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar metalThickness) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar metalThickness) {

            }
        });

        yStrength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar metalThickness, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                yDisplay.setText(Double.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar metalThickness) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar metalThickness) {

            }
        });

        percentEl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar metalThickness, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                elDisplay.setText(Double.toString(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar metalThickness) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar metalThickness) {

            }
        });


        try{
//            if(extrasBundle.containsKey("max_melting_pt")){
//                mpMaxInput.setText(Double.toString(extrasBundle.getDouble("max_melting_pt")));
//            }
//            if(extrasBundle.containsKey("min_melting_pt")){
//                mpMinInput.setText(Double.toString(extrasBundle.getDouble("min_melting_pt")));
//            }
            if(extrasBundle.containsKey("tensile_strength")){
                tStrength.setProgress((int) extrasBundle.getDouble("tensile_strength"));
            }
            if(extrasBundle.containsKey("fatigue_strength")){
                fStrength.setProgress((int) extrasBundle.getDouble("fatigue_strength"));
            }
            if(extrasBundle.containsKey("yield_strength")){
                yStrength.setProgress((int) extrasBundle.getDouble("yield_strength"));
            }
            if(extrasBundle.containsKey("elongation")){
                percentEl.setProgress((int) extrasBundle.getDouble("elongation"));
            }
            if(extrasBundle.containsKey("corrosion_resistance")){
                int i = extrasBundle.getInt("corrosion_resistance");
                if(i==0){
                    corResist.setSelection(adapter.getPosition("No Selection"));
                }
                else if(i==1){
                    corResist.setSelection(adapter.getPosition("Limited"));
                }
                else if(i==2){
                    corResist.setSelection(adapter.getPosition("Sufficient"));
                }
                else if(i==3){
                    corResist.setSelection(adapter.getPosition("Good"));
                }
                else if(i==4){
                    corResist.setSelection(adapter.getPosition("Very Good"));
                }
                else if(i==5){
                    corResist.setSelection(adapter.getPosition("Excellent"));
                }


            }
        }
        catch (NullPointerException e){

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filters.putDouble("tensile_strength", tStrength.getProgress());
                filters.putDouble("fatigue_strength", fStrength.getProgress());
                filters.putDouble("yield_strength", yStrength.getProgress());
                filters.putDouble("elongation", percentEl.getProgress());
                filters.putInt("corrosion_resistance", cor[0]);


                mas.putExtra("key","value");
                mas.putExtras(filters);
                startActivityForResult(mas, 1);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tStrength.setProgress(0);
                fStrength.setProgress(0);
                yStrength.setProgress(0);
                percentEl.setProgress(0);
                corResist.setSelection(adapter.getPosition("No Selection"));
            }
        });

    }


    public void onBackPressed(){

        Intent mas = new Intent(getBaseContext(), MAS.class);
        final Bundle filters = new Bundle();

        final SeekBar tStrength = findViewById(R.id.seekBar8);
        final SeekBar fStrength = findViewById(R.id.seekBar6);
        final SeekBar yStrength = findViewById(R.id.seekBar7);
        final SeekBar percentEl = findViewById(R.id.seekBar9);
        final int[] cor = {0};
        final TextView tDisplay = findViewById(R.id.textView95);
        final TextView fDisplay = findViewById(R.id.textView89);
        final TextView yDisplay = findViewById(R.id.textView92);
        final TextView elDisplay = findViewById(R.id.textView99);
        final Spinner corResist = findViewById(R.id.spinner3);
        filters.putDouble("tensile_strength", tStrength.getProgress());
        filters.putDouble("fatigue_strength", fStrength.getProgress());
        filters.putDouble("yield_strength", yStrength.getProgress());
        filters.putDouble("elongation", percentEl.getProgress());
        filters.putInt("corrosion_resistance", cor[0]);


        mas.putExtra("key","value");
        mas.putExtras(filters);
        startActivityForResult(mas, 1);
        return;
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();

    }


//    public void OnBackPressed(){
//        MAS_F.super.onBackPressed();
//
//        filters.putDouble("tensile_strength", tStrength.getProgress());
//        filters.putDouble("fatigue_strength", fStrength.getProgress());
//        filters.putDouble("yield_strength", yStrength.getProgress());
//        filters.putDouble("elongation", yStrength.getProgress());
//        filters.putInt("heat_treatment", heatTreat[0]);
//        filters.putInt("corrosion_resistance", cor[0]);
//
//
//        mas.putExtra("key","value");
//        mas.putExtras(filters);
//        startActivityForResult(mas, 1);
//        finish();
//
//
//    }

    private void openDB(){
        myDB = new DBHandler(this);
        myDB.open();
    }

    private void closeDB(){
        myDB.close();
    }
}

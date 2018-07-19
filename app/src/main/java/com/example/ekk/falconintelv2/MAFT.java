package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MAFT extends MainActivity {

    final Bundle fillTime = new Bundle();
    double[] k = new double[1];
    double[] tF = new double[1];
    double[] z = new double[1];
    double[] T = new double[1];
    double[] S = new double[1];
    double[] tI = new double[1];
    double[] tD = new double[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maft);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);



        final TextView analysis = findViewById(R.id.textView8);
        final TextView dispMetalThickness = (TextView)findViewById(R.id.textView14);
        final TextView dispPercentSolids = findViewById(R.id.textView10);
        final Spinner alloy = findViewById(R.id.spinner2);

        final SeekBar metalThickness = findViewById(R.id.seekBar2);
        final SeekBar percentSol = findViewById(R.id.seekBar3);
        final EditText metalTemperature = (EditText) findViewById(R.id.editText3);
        final EditText dieTemperature = (EditText) findViewById(R.id.editText4);

        String[] items = new String[]{"Magnesium", "Aluminum 360, 380, 384", "Aluminum 390", "Zinc 12, 27", "Zinc 3, 5, 7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        alloy.setAdapter(adapter);
        alloy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                Toast toast = Toast.makeText (getApplicationContext(), "Selected : " + selectedItem, Toast.LENGTH_SHORT);
                toast.show();


                if(selectedItem=="Magnesium"){
                    k[0] = 0.063;
                    tF[0] = 1050;
                    z[0] = 6.6;

                }
                else if(selectedItem=="Aluminum 360, 380, 384"){
                    k[0] = 0.866;
                    tF[0] = 1060;
                    z[0] = 8.6;
                }
                else if(selectedItem=="Aluminum 390"){
                    k[0] = 0.866;
                    tF[0] = 1100;
                    z[0] = 10.6;
                }
                else if(selectedItem=="Zinc 12, 27"){
                    k[0] = 0.866;
                    tF[0] =835;
                    z[0] = 5.7;
                }
                else if(selectedItem=="Zinc 3, 5, 7"){
                    k[0] = 0.866;
                    tF[0] = 760;
                    z[0] = 4.5;

                }
                else{
                    k[0] = 0;
                    tF[0] = 0;
                    z[0] = 0;
                    //flaccid teeth

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        metalThickness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar metalThickness, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                double decProgress = progress/100.0;
                dispMetalThickness.setText(Double.toString(decProgress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar metalThickness) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar metalThickness) {

            }
        });


        percentSol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar percentSol, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                dispPercentSolids.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar percentSol) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar percentSol) {
                // TODO Auto-generated method stub
            }
        });



        Button calculate = (Button)findViewById(R.id.button);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(metalThickness.getProgress()==0 && percentSol.getProgress()==0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter metal thickness and percent solids", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(metalThickness.getProgress()==0 && percentSol.getProgress()!=0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter metal thickness", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(metalThickness.getProgress()!=0 && percentSol.getProgress()==0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter percent solids", Toast.LENGTH_SHORT);
                    toast.show();
                }

                if(metalThickness.getProgress()==0){
                    T[0] = 0;
                }
                else{
                    T[0] = metalThickness.getProgress()/100.0;
                }

                if(percentSol.getProgress()==0){
                    S[0] = 0;
                }
                else{
                    S[0] = percentSol.getProgress();
                }

                if(metalTemperature.getText().toString().trim().length()==0){
                    tI[0] = 1175;
                }
                else{
                    tI[0] = Double.parseDouble(metalTemperature.getText().toString());
                }
                if(dieTemperature.getText().toString().trim().length()==0){
                    tD[0] = 350;
                }
                else{
                    tD[0] = Double.parseDouble(dieTemperature.getText().toString());
                }

                double ft = k[0] *(((tI[0]-tF[0]) + (z[0]*S[0]))/(tF[0]-tD[0])) * T[0];
                DecimalFormat df = new DecimalFormat("#.######");
                analysis.setText(df.format(ft));

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    public void openMVA(View view){
        final SeekBar metalThickness = findViewById(R.id.seekBar2);
        final SeekBar percentSol = findViewById(R.id.seekBar3);
        final EditText metalTemperature = findViewById(R.id.editText3);
        final EditText dieTemperature = findViewById(R.id.editText4);


        if(metalThickness.getProgress()==0 && percentSol.getProgress()==0){
            Toast toast = Toast.makeText (getApplicationContext(), "Please enter metal thickness and percent solids", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(metalThickness.getProgress()==0 && percentSol.getProgress()!=0){
            Toast toast = Toast.makeText (getApplicationContext(), "Please enter metal thickness", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if(metalThickness.getProgress()!=0 && percentSol.getProgress()==0){
            Toast toast = Toast.makeText (getApplicationContext(), "Please enter percent solids", Toast.LENGTH_SHORT);
            toast.show();
        }

        if(metalThickness.getProgress()==0){
            T[0] = 0;
        }
        else{
            T[0] = metalThickness.getProgress()/100.0;
        }

        if(percentSol.getProgress()==0){
            S[0] = 0;
        }
        else{
            S[0] = percentSol.getProgress();
        }

        if(metalTemperature.getText().toString().trim().length()==0){
            tI[0] = 1175;
        }
        else{
            tI[0] = Double.parseDouble(metalTemperature.getText().toString());
        }
        if(dieTemperature.getText().toString().trim().length()==0){
            tD[0] = 350;
        }
        else{
            tD[0] = Double.parseDouble(dieTemperature.getText().toString());
        }

        double ft = k[0] *(((tI[0]-tF[0]) + (z[0]*S[0]))/(tF[0]-tD[0])) * T[0];
        fillTime.putDouble("fillTime", ft);
        Intent mva = new Intent(getBaseContext(), MVA.class);
        mva.putExtras(fillTime);
        startActivity(mva);
    }

}

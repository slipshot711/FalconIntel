package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class LT extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lt);

        Intent intent = getIntent();
        //our house: in the middle of our hearts

        final double[] h = new double[1];
        final double[] w = new double[1];
        final double[] p = new double[1];
        final double[] pR = new double[1];
        final TextView analysis = findViewById(R.id.textView37);
        final EditText height = findViewById(R.id.editText);
        final EditText width = findViewById(R.id.editText6);
        final SeekBar percent = findViewById(R.id.seekBar);
        final TextView dispPercent  = findViewById(R.id.textView32);
        final EditText pressure = findViewById(R.id.editText7);
        final Button calculate = findViewById(R.id.button5);

        percent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dispPercent.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                catch(Exception e){

                }

                if(width.getText().toString().trim().length()==0 && height.getText().toString().trim().length()==0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter projected height and width", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(width.getText().toString().trim().length()==0 && height.getText().toString().trim().length()!=0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter projected width", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(width.getText().toString().trim().length()!=0 && height.getText().toString().trim().length()==0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter projected height", Toast.LENGTH_SHORT);
                    toast.show();
                }


                if(height.getText().toString().trim().length()==0){
                    h[0] = 0;
                }
                else{
                    h[0] = Double.parseDouble(height.getText().toString());
                }
                if(width.getText().toString().trim().length()==0){
                    w[0] = 0;
                }
                else{
                    w[0] = Double.parseDouble(width.getText().toString());
                }
                if(pressure.getText().toString().trim().length()==0){
                    pR[0] = 10000;
                }
                else{
                    pR[0] = Double.parseDouble(pressure.getText().toString());
                }

                p[0] = percent.getProgress();

                double mp = 1 + (p[0]/100);
                double lb = h[0] * w[0] * mp * pR[0];
                double lt = lb/2000;
                DecimalFormat df = new DecimalFormat("#.######");
                analysis.setText(df.format(lt));
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

}


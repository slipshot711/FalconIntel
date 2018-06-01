package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MVA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mva);
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();
        final EditText fillTime = findViewById(R.id.editText13);
        final EditText partVolume = findViewById(R.id.editText14);
        final TextView analysis = findViewById(R.id.textView60);
        Button calculate  = findViewById(R.id.button13);
        final double[] t = new double[1];
        final double[] volume = new double[1];

        try{
            if(extrasBundle.containsKey("fillTime")){
                DecimalFormat df = new DecimalFormat("#.######");
                fillTime.setText(df.format(extrasBundle.getDouble("fillTime")));
            }
        }
        catch (NullPointerException e){

        }

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                catch(Exception e){

                }

                if(fillTime.getText().toString().trim().length()==0 && partVolume.getText().toString().trim().length()==0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter values", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(fillTime.getText().toString().trim().length()==0 && partVolume.getText().toString().trim().length()!=0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter fill time", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(fillTime.getText().toString().trim().length()!=0 && partVolume.getText().toString().trim().length()==0){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter part volume", Toast.LENGTH_SHORT);
                    toast.show();
                }


                if(fillTime.getText().toString().trim().length()==0){
                    t[0] = 0;
                }
                else{
                    t[0] = Double.parseDouble(fillTime.getText().toString());
                }
                if(partVolume.getText().toString().trim().length()==0){
                    volume[0] = 0;
                }
                else{
                    volume[0] = Double.parseDouble(partVolume.getText().toString());
                }

                double ftVol = volume[0]/1728;
                double mv = .2055116893 * ftVol/t[0];
                DecimalFormat df = new DecimalFormat("#.######");
                analysis.setText(df.format(mv));
            }
        });
    }
}

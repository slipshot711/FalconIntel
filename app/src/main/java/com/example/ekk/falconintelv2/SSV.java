package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class SSV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssv);

        Intent intent = getIntent();

        final double[] pD = new double[1];
        final double[] L = new double[1];
        final double[] V = new double[1];
        final double[] density = new double[1];
        final TextView analysis = findViewById(R.id.textView23);
        final TextView ld = findViewById(R.id.textView19);
        final EditText plungerDiameter = findViewById(R.id.editText2);
        final EditText sleeveLength = findViewById(R.id.editText5);
        final EditText ladled = findViewById(R.id.editText8);
        final String volume = "Volume Ladled";
        final String pounds = "Pounds Ladled";
        final Switch flip = findViewById(R.id.switch1);
        final Spinner alloy = findViewById(R.id.spinner);

        flip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ld.setText(pounds);
                }
                else{
                    ld.setText(volume);
                }
            }
        });


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
                    density[0] = .066;
                }
                else if(selectedItem=="Aluminum 360, 380, 384"){
                    density[0] = .093;
                }
                else if(selectedItem=="Aluminum 390"){
                    density[0] = .096;
                }
                else if(selectedItem=="Zinc 12, 27"){
                    density[0] = .181;
                }
                else if(selectedItem=="Zinc 3, 5, 7"){
                    density[0] = .245;
                }
                else{
                    density[0] = 0;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Button calculate = findViewById(R.id.button4);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(plungerDiameter.getText().toString().trim().length()==0){
                    pD[0] = 2.5;
                }
                else{
                    pD[0] = Double.parseDouble(plungerDiameter.getText().toString());
                }
                if(sleeveLength.getText().toString().trim().length()==0){
                    L[0] = 19;
                }
                else {
                    L[0] = Double.parseDouble(sleeveLength.getText().toString());
                }

                if(flip.isChecked()){
                    ld.setText(pounds);

                    if(ladled.getText().toString().trim().length()==0){
                        V[0] = 0.0;
                        Toast toast = Toast.makeText (getApplicationContext(), "Please enter pounds ladled", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        double m = Double.parseDouble(ladled.getText().toString());
                        V[0] = m/density[0];
                    }
                }
                else{
                    ld.setText(volume);

                    if(ladled.getText().toString().trim().length()==0){
                        V[0] = 0.0;
                        Toast toast = Toast.makeText (getApplicationContext(), "Please enter volume ladled", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        V[0] = Double.parseDouble(ladled.getText().toString());
                    }
                }


                double e = 3.14159*Math.pow((pD[0]/2),2) * L[0];
                double ssv = 22.8 * Math.sqrt(pD[0]) * (1-(V[0]/e));
                DecimalFormat df = new DecimalFormat("#.######");
                analysis.setText(df.format(ssv));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }
}

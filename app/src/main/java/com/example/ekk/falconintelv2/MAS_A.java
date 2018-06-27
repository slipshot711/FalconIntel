package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MAS_A extends AppCompatActivity {

    DBHandler myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas_a);

        Intent intent = getIntent();
        final Bundle extrasBundle = intent.getExtras();
        openDB();
        final Intent mas = new Intent(getBaseContext(), MAS.class);

        final EditText alloyName = findViewById(R.id.editText141);
        final EditText tStrengthMin = findViewById(R.id.editText151);
        final EditText tStrengthMax = findViewById(R.id.editText161);
        final EditText fStrengthMin = findViewById(R.id.editText171);
        final EditText fStrengthMax = findViewById(R.id.editText181);
        final EditText yStrengthMin = findViewById(R.id.editText191);
        final EditText yStrengthMax = findViewById(R.id.editText201);
        final EditText percentElMin = findViewById(R.id.editText211);
        final EditText percentElMax = findViewById(R.id.editText221);
        final EditText tConduct = findViewById(R.id.editText231);
        final EditText eConduct = findViewById(R.id.editText241);

        final double[] tSMx = new double[1];
        final double[] tSMn = new double[1];
        final double[] fSMx = new double[1];
        final double[] fSMn = new double[1];
        final double[] ySMx = new double[1];
        final double[] ySMn = new double[1];
        final double[] pEMx = new double[1];
        final double[] pEMn = new double[1];
        final double[] tC = new double[1];
        final double[] eC = new double[1];


        Button addAlloy = findViewById(R.id.button9);


        try{
            if(extrasBundle.containsKey("name")){
                alloyName.setText(extrasBundle.getString("name"));
            }
            if(extrasBundle.containsKey("tMax")){
                tStrengthMax.setText(extrasBundle.getString("tMax"));
            }
            if(extrasBundle.containsKey("fMax")){
                fStrengthMax.setText(extrasBundle.getString("fMax"));
            }
            if(extrasBundle.containsKey("yMax")){
                yStrengthMax.setText(extrasBundle.getString("yMax"));
            }
            if(extrasBundle.containsKey("pMax")){
                percentElMax.setText(extrasBundle.getString("pMax"));
            }
            if(extrasBundle.containsKey("tMin")){
                tStrengthMin.setText(extrasBundle.getString("tMin"));
            }
            if(extrasBundle.containsKey("fMin")){
                fStrengthMin.setText(extrasBundle.getString("fMin"));
            }
            if(extrasBundle.containsKey("yMin")){
                yStrengthMin.setText(extrasBundle.getString("yMin"));
            }
            if(extrasBundle.containsKey("pMin")){
                percentElMin.setText(extrasBundle.getString("pMin"));
            }
            if(extrasBundle.containsKey("tCon")){
                tConduct.setText(extrasBundle.getString("tCon"));
            }
            if(extrasBundle.containsKey("eCon")){
                eConduct.setText(extrasBundle.getString("eCon"));
            }

        }
        catch (NullPointerException e){
        }

        addAlloy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(alloyName.getText()) &&
                        !TextUtils.isEmpty(tStrengthMin.getText()) &&
                        !TextUtils.isEmpty(tStrengthMax.getText()) &&
                        !TextUtils.isEmpty(fStrengthMin.getText()) &&
                        !TextUtils.isEmpty(fStrengthMax.getText()) &&
                        !TextUtils.isEmpty(yStrengthMin.getText()) &&
                        !TextUtils.isEmpty(yStrengthMax.getText()) &&
                        !TextUtils.isEmpty(percentElMax.getText()) &&
                        !TextUtils.isEmpty(percentElMin.getText()) &&
                        !TextUtils.isEmpty(tConduct.getText()) &&
                        !TextUtils.isEmpty(eConduct.getText()) &&
                        !myDB.seeIfAlreadyExists(alloyName.getText().toString())){

                    myDB.insertRow(
                            alloyName.getText().toString(),
                            Double.parseDouble(tStrengthMax.getText().toString()),
                            Double.parseDouble(fStrengthMax.getText().toString()),
                            Double.parseDouble(yStrengthMax.getText().toString()),
                            Double.parseDouble(percentElMax.getText().toString()),
                            Double.parseDouble(tStrengthMin.getText().toString()),
                            Double.parseDouble(fStrengthMin.getText().toString()),
                            Double.parseDouble(yStrengthMin.getText().toString()),
                            Double.parseDouble(percentElMin.getText().toString()),
                            0,
                            Double.parseDouble(tConduct.getText().toString()),
                            Double.parseDouble(eConduct.getText().toString())

                    );

                    startActivity(mas);
                }
                else if(myDB.seeIfAlreadyExists(alloyName.getText().toString())){
                    try {
                        myDB.updateRow(
                                extrasBundle.getLong("id"),
                                alloyName.getText().toString(),
                                Double.parseDouble(tStrengthMax.getText().toString()),
                                Double.parseDouble(fStrengthMax.getText().toString()),
                                Double.parseDouble(yStrengthMax.getText().toString()),
                                Double.parseDouble(percentElMax.getText().toString()),
                                Double.parseDouble(tStrengthMin.getText().toString()),
                                Double.parseDouble(fStrengthMin.getText().toString()),
                                Double.parseDouble(yStrengthMin.getText().toString()),
                                Double.parseDouble(percentElMin.getText().toString()),
                                0,
                                Double.parseDouble(tConduct.getText().toString()),
                                Double.parseDouble(eConduct.getText().toString()));
                        startActivity(mas);
                    }
                    catch (NullPointerException e){
                        Toast toast = Toast.makeText(getApplicationContext(), "Alloy already exists", Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }
                else if(TextUtils.isEmpty(alloyName.getText()) ||
                        TextUtils.isEmpty(tStrengthMin.getText()) ||
                        TextUtils.isEmpty(tStrengthMax.getText()) ||
                        TextUtils.isEmpty(fStrengthMin.getText()) ||
                        TextUtils.isEmpty(fStrengthMax.getText()) ||
                        TextUtils.isEmpty(yStrengthMin.getText()) ||
                        TextUtils.isEmpty(yStrengthMax.getText()) ||
                        TextUtils.isEmpty(percentElMax.getText()) ||
                        TextUtils.isEmpty(percentElMin.getText()) ||
                        TextUtils.isEmpty(tConduct.getText()) ||
                        TextUtils.isEmpty(eConduct.getText())) {

                    if (TextUtils.isEmpty(alloyName.getText())) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Name Missing", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else if (!TextUtils.isEmpty(alloyName.getText())) {

                        if (TextUtils.isEmpty(tStrengthMin.getText())) {
                            tSMn[0] = 0;
                        }else{
                            tSMn[0] = Double.parseDouble(tStrengthMin.getText().toString());
                        }
                        if (TextUtils.isEmpty(tStrengthMax.getText())) {
                            tSMx[0] = 100000;
                        }else{
                            tSMx[0] = Double.parseDouble(tStrengthMax.getText().toString());
                        }
                        if (TextUtils.isEmpty(fStrengthMin.getText())) {
                            fSMn[0] = 0;
                        }else{
                            fSMn[0] = Double.parseDouble(fStrengthMin.getText().toString());
                        }
                        if (TextUtils.isEmpty(fStrengthMax.getText())) {
                            fSMx[0] = 100000;
                        }else{
                            fSMx[0] = Double.parseDouble(fStrengthMax.getText().toString());
                        }
                        if (TextUtils.isEmpty(yStrengthMin.getText())) {
                            ySMn[0] = 0;
                        }else{
                            ySMn[0] = Double.parseDouble(yStrengthMin.getText().toString());
                        }
                        if (TextUtils.isEmpty(yStrengthMax.getText())) {
                            ySMx[0] = 100000;
                        }else{
                            ySMx[0] = Double.parseDouble(yStrengthMax.getText().toString());
                        }
                        if (TextUtils.isEmpty(percentElMin.getText())) {
                            pEMn[0] = 0;
                        }else{
                            pEMn[0] = Double.parseDouble(percentElMin.getText().toString());
                        }
                        if (TextUtils.isEmpty(percentElMax.getText())) {
                            pEMx[0] = 100000;
                        }else{
                            pEMx[0] = Double.parseDouble(percentElMax.getText().toString());
                        }
                        if (TextUtils.isEmpty(tConduct.getText())) {
                            tC[0] = 0;
                        }else{
                            tC[0] =  Double.parseDouble(tConduct.getText().toString());
                        }
                        if (TextUtils.isEmpty(eConduct.getText())) {
                            eC[0] = 0;
                        }else{
                            eC[0] =  Double.parseDouble(eConduct.getText().toString());
                        }

                        myDB.insertRow(alloyName.getText().toString(), tSMx[0], fSMx[0], ySMx[0], pEMx[0], tSMn[0], fSMn[0], ySMn[0], pEMn[0], 0, tC[0], eC[0]);
                        startActivity(mas);
                    }
                }
            }
        });


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
}

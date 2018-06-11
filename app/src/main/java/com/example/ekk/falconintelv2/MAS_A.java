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


        Button addAlloy = findViewById(R.id.button9);

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
                else if(!TextUtils.isEmpty(alloyName.getText()) &&
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
                        myDB.seeIfAlreadyExists(alloyName.getText().toString())){

                    Toast toast = Toast.makeText (getApplicationContext(), "Alloy already exists in database", Toast.LENGTH_SHORT);
                    toast.show();
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
                        TextUtils.isEmpty(eConduct.getText())){

                    Toast toast = Toast.makeText (getApplicationContext(), "Missing Data", Toast.LENGTH_SHORT);
                    toast.show();
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

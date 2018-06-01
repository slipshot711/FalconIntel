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

        final EditText alloyName = findViewById(R.id.editText9);
        final EditText meltingPoint = findViewById(R.id.editText10);

        Button addAlloy = findViewById(R.id.button9);

        alloyName.requestFocus();

        addAlloy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(alloyName.getText()) && !TextUtils.isEmpty(meltingPoint.getText()) && !myDB.seeIfAlreadyExists(alloyName.getText().toString())){
                    myDB.insertRow(alloyName.getText().toString(), Double.parseDouble(meltingPoint.getText().toString()));
                    startActivity(mas);
                }
                else if(!TextUtils.isEmpty(alloyName.getText()) && !TextUtils.isEmpty(meltingPoint.getText()) && myDB.seeIfAlreadyExists(alloyName.getText().toString())){
                    Toast toast = Toast.makeText (getApplicationContext(), "Alloy already exists in database", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(TextUtils.isEmpty(alloyName.getText()) && !TextUtils.isEmpty(meltingPoint.getText())){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter alloy name", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(!TextUtils.isEmpty(alloyName.getText()) && TextUtils.isEmpty(meltingPoint.getText())){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter melting point", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(TextUtils.isEmpty(alloyName.getText()) && TextUtils.isEmpty(meltingPoint.getText())){
                    Toast toast = Toast.makeText (getApplicationContext(), "Please enter values", Toast.LENGTH_SHORT);
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

package com.example.ekk.falconintelv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MAS_F extends AppCompatActivity {

    DBHandler myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas__f);

        Intent intent = getIntent();
        final Intent mas = new Intent(getBaseContext(), MAS.class);
        mas.putExtra("key","value");
        final Bundle filters = new Bundle();
        Bundle extrasBundle = intent.getExtras();
        openDB();


        final EditText mpMaxInput = findViewById(R.id.editText11);
        final EditText mpMinInput = findViewById(R.id.editText12);
        Button save = findViewById(R.id.button10);

        mpMinInput.clearFocus();

        try{
            if(extrasBundle.containsKey("max_melting_pt")){
                mpMaxInput.setText(Double.toString(extrasBundle.getDouble("max_melting_pt")));
            }
            if(extrasBundle.containsKey("min_melting_pt")){
                mpMinInput.setText(Double.toString(extrasBundle.getDouble("min_melting_pt")));
            }
        }
        catch (NullPointerException e){

        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(mpMaxInput.getText()) && !TextUtils.isEmpty(mpMinInput.getText())){
                    if(Double.parseDouble(mpMaxInput.getText().toString()) < Double.parseDouble(mpMinInput.getText().toString())){
                        filters.putDouble("max_melting_pt", Double.parseDouble(mpMinInput.getText().toString()));
                        filters.putDouble("min_melting_pt", Double.parseDouble(mpMaxInput.getText().toString()));

                        Toast toast = Toast.makeText (getApplicationContext(), "Min value is larger max value", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else {
                        filters.putDouble("max_melting_pt", Double.parseDouble(mpMaxInput.getText().toString()));
                        filters.putDouble("min_melting_pt", Double.parseDouble(mpMinInput.getText().toString()));
                    }

                }

                else if(TextUtils.isEmpty(mpMaxInput.getText()) && !TextUtils.isEmpty(mpMinInput.getText())){
                    filters.putDouble("max_melting_pt", Double.MAX_VALUE);
                    filters.putDouble("min_melting_pt", Double.parseDouble(mpMinInput.getText().toString()));

                    Toast toast = Toast.makeText (getApplicationContext(), "Melting point max value missing", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(!TextUtils.isEmpty(mpMaxInput.getText()) && TextUtils.isEmpty(mpMinInput.getText())){
                    filters.putDouble("max_melting_pt", Double.parseDouble(mpMaxInput.getText().toString()));
                    filters.putDouble("min_melting_pt", 0);

                    Toast toast = Toast.makeText (getApplicationContext(), "Melting point min value missing", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(TextUtils.isEmpty(mpMaxInput.getText()) && TextUtils.isEmpty(mpMinInput.getText())){
                    filters.putDouble("max_melting_pt", Double.MAX_VALUE);
                    filters.putDouble("min_melting_pt", 0);

                    Toast toast = Toast.makeText (getApplicationContext(), "No filter applied", Toast.LENGTH_SHORT);
                    toast.show();
                }


                mas.putExtra("key","value");
                mas.putExtras(filters);
                startActivityForResult(mas, 1);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        EditText mpMaxInput = findViewById(R.id.editText11);
        EditText mpMinInput = findViewById(R.id.editText12);

        super.onSaveInstanceState(outState);
        outState.putString("max_mp", mpMaxInput.getText().toString());
        outState.putString("min_mp", mpMinInput.getText().toString());
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

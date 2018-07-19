package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        //sugar :) honey :) ice :) tea ;)

    }

    public void openMAFT(View view){
        Intent maft = new Intent(getBaseContext(), MAFT.class);
        startActivity(maft);
    }

    public void openSSV(View view){
        Intent ssv = new Intent(getBaseContext(), SSV.class);
        startActivity(ssv);
    }

    public void openLT(View view){
        Intent lt = new Intent(getBaseContext(), LT.class);
        startActivity(lt);
    }

    public void openMAS(View view){
        Intent mas = new Intent(getBaseContext(), MAS.class);
        startActivity(mas);
    }
    public void openMVA(View view){
        Intent mva = new Intent(getBaseContext(), MVA.class);
        startActivity(mva);
    }

}

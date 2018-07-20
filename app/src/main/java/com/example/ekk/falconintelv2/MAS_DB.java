package com.example.ekk.falconintelv2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.TextView;

public class MAS_DB extends AppCompatActivity {

    DBHandler myDB;
    final Bundle filters = new Bundle();
    final Bundle details  =new Bundle();

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mas__db);

        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();
        openDB();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        TextView name = findViewById(R.id.textView4001);
        ImageButton edit =  findViewById(R.id.imageButton3);
        final ImageButton delete = findViewById(R.id.imageButton4);
        final long id = getID();


        details.putString("name", myDB.getName(id));
                details.putString("tMax", myDB.getMaxTensileStrength(id));
                details.putString("fMax", myDB.getMaxFatigueStrength(id));
                details.putString("yMax", myDB.getMaxYieldStrength(id));
                details.putString("pMax", myDB.getMaxPercentElongation(id));
                details.putString("tMin", myDB.getMinTensileStrength(id));
                details.putString("fMin", myDB.getMinFatigueStrength(id));
                details.putString("yMin", myDB.getMinYieldStrength(id));
                details.putString("pMin", myDB.getMinPercentElongation(id));
                details.putString("tCon", myDB.getThermalConductivity(id));
                details.putString("eCon", myDB.getElectricConductivity(id));
                details.putString("Si", myDB.getElectricConductivity(id));
                details.putString("Fe", myDB.getElectricConductivity(id));
                details.putString("Cu", myDB.getElectricConductivity(id));
                details.putString("Mn", myDB.getElectricConductivity(id));
                details.putString("Mg", myDB.getElectricConductivity(id));
                details.putString("Zn", myDB.getElectricConductivity(id));
                details.putString("Ti", myDB.getElectricConductivity(id));


        name.setText(myDB.getName(id));
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent masa = new Intent(getBaseContext(), MAS_A.class);
                masa.putExtras(details);
                masa.putExtra("key","value");
                startActivity(masa);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();
                View alertLayout = inflater.inflate(R.layout.alert_layout, null);
                final TextView Title = alertLayout.findViewById(R.id.textView1121);
                Title.setText("Are you sure you want to delete " + myDB.getName(id) + "?");

                AlertDialog.Builder builder = new AlertDialog.Builder(MAS_DB.this);
                builder.setCancelable(true);
                builder.setView(alertLayout);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDB.deleteRow(id);
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });



        filters.putDouble("tensile_strength",extrasBundle.getDouble("tensile_strength"));
        filters.putDouble("fatigue_strength", extrasBundle.getDouble("fatigue_strength"));
        filters.putDouble("yield_strength", extrasBundle.getDouble("yield_strength"));
        filters.putDouble("elongation", extrasBundle.getDouble("elongation"));
        filters.putInt("corrosion_resistance", extrasBundle.getInt("corrosion_resistance"));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =  null;
            switch(position){
                case 0:
                    fragment = new MAS_DB_PP();
                    break;
                case 1:
                    fragment = new MAS_DB_CC();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }

    public long getID(){
        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();
        return extrasBundle.getLong("id");
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

    public void onBackPressed(){

        Intent mas = new Intent(getBaseContext(), MAS.class);



        mas.putExtra("key","value");
        mas.putExtras(filters);
        startActivity(mas);
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
}

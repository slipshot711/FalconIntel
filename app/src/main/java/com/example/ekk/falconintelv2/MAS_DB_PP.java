package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MAS_DB_PP extends Fragment {

    DBHandler myDB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDB = new DBHandler(getActivity());
        myDB.open();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fullScreen = inflater.inflate(R.layout.activity_mas_db_pp, container, false);

        MAS_DB tab = (MAS_DB) getActivity();
        long id = tab.getID();

        final TextView tStrengthMax = fullScreen.findViewById(R.id.textView45);
        final TextView tStrengthMin = fullScreen.findViewById(R.id.textView4001);
        final TextView fStrengthMax = fullScreen.findViewById(R.id.textView501);
        final TextView fStrengthMin = fullScreen.findViewById(R.id.textView47);
        final TextView yStrengthMax = fullScreen.findViewById(R.id.textView541);
        final TextView yStrengthMin = fullScreen.findViewById(R.id.textView521);
        final TextView percentElMax = fullScreen.findViewById(R.id.textView581);
        final TextView percentElMin = fullScreen.findViewById(R.id.textView561);
        final TextView tConduct = fullScreen.findViewById(R.id.textView821);
        final TextView eConduct = fullScreen.findViewById(R.id.textView841);

        tStrengthMax.setText(myDB.getMaxTensileStrength(id));
        fStrengthMax.setText(myDB.getMaxFatigueStrength(id));
        yStrengthMax.setText(myDB.getMaxYieldStrength(id));
        percentElMax.setText(myDB.getMaxPercentElongation(id));
        tStrengthMin.setText(myDB.getMinTensileStrength(id));
        fStrengthMin.setText(myDB.getMinFatigueStrength(id));
        yStrengthMin.setText(myDB.getMinYieldStrength(id));
        percentElMin.setText(myDB.getMinPercentElongation(id));
        tConduct.setText(myDB.getThermalConductivity(id));
        eConduct.setText(myDB.getElectricConductivity(id));


        return fullScreen;

    }


}

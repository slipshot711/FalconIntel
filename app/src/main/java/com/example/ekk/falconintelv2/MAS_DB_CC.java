package com.example.ekk.falconintelv2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MAS_DB_CC extends Fragment {

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
        View fullScreen = inflater.inflate(R.layout.activity_mas_db_cc, container, false);

        MAS_DB tab = (MAS_DB) getActivity();
        long id = tab.getID();

        final TextView silicon = fullScreen.findViewById(R.id.textView79);
        final TextView iron = fullScreen.findViewById(R.id.textView87);
        final TextView copper = fullScreen.findViewById(R.id.textView102);
        final TextView manganese = fullScreen.findViewById(R.id.textView106);
        final TextView magnesium = fullScreen.findViewById(R.id.textView109);
        final TextView zinc = fullScreen.findViewById(R.id.textView112);
        final TextView titanium = fullScreen.findViewById(R.id.textView115);

        silicon.setText(myDB.getSilicon(id));
        iron.setText(myDB.getIron(id));
        copper.setText(myDB.getCopper(id));
        manganese.setText(myDB.getManganese(id));
        magnesium.setText(myDB.getMagnesium(id));
        zinc.setText(myDB.getZinc(id));
        titanium.setText(myDB.getTitanium(id));

        return fullScreen;
    }


}

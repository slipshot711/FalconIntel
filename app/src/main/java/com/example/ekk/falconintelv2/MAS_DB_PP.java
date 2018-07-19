package com.example.ekk.falconintelv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        View v = inflater.inflate(R.layout.activity_mas_db_pp, container, false);

        TextView test = v.findViewById(R.id.textView42);

        MAS_DB tab = (MAS_DB) getActivity();
        long id = tab.getID();

        test.setText(Long.toString(id));

        return v;

    }




    private void closeDB(){
        myDB.close();
    }
}

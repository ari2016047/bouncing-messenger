package com0.example.android.bouncingmessengertestapp;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.wifidirect.discovery.R;

import java.util.ArrayList;


public class Contacts extends Activity {
DatabaseHelper db;
private ListView x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        x = (ListView)findViewById(R.id.list);
        db = new DatabaseHelper(this);
        Cursor res = db.getAllData();

        ArrayList<String> list1 = new ArrayList<>();
        if(res.getCount()==0)
        {

        }
        else {
            while (res.moveToNext()) {
                list1.add(res.getString(1));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list1);
                x.setAdapter(listAdapter);
            }
        }
    }
}

package com0.example.android.bouncingmessengertestapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.wifidirect.discovery.R;

public class add_contacts extends Activity {
    DatabaseHelper db;
    EditText name;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        db = new DatabaseHelper(this);
        name = (EditText)findViewById(R.id.editText);
        add = (Button)findViewById(R.id.button);
       AddData();
    }
    public void AddData()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           boolean x = db.insertData(name.getText().toString());
           if(x==true)
               Toast.makeText(add_contacts.this,"new Contact added successfully", Toast.LENGTH_LONG).show();
           else
               Toast.makeText(add_contacts.this,"Addition Failed", Toast.LENGTH_LONG).show();

                Intent intent = new Intent("com0.example.android.bouncingmessengertestapp.Contacts");
                startActivity(intent);
            }
        });
    }
}

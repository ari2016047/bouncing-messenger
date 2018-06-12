package com0.example.android.bouncingmessengertestapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.wifidirect.discovery.R;

public class SignUp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText userName = (EditText) findViewById(R.id.etNewName);
        final EditText password = (EditText) findViewById(R.id.etNewPassword);
        final EditText phoneNo = (EditText) findViewById(R.id.PhoneNo);
        Button btnRegister = (Button) findViewById(R.id.btnNewRegister);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("MYPREFS",MODE_PRIVATE);
                String newUser  = userName.getText().toString();
                String newPassword = password.getText().toString();
                String newPhoneNo = phoneNo.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();

                //stores 3 new instances of sharedprefs. Both the user and password's keys are the same as the input.
                //Must be done this way because sharedprefs is stupid and inefficient. You cannot store Arrays easily
                //so I use strings instead.
                editor.putString("username",newUser);
                editor.commit();
                editor.putString("password", newPassword);
                editor.commit();
                editor.putString(newUser + newPassword + "data", newUser + "\n" + newPhoneNo);
                editor.commit();
                Intent displayScreen = new Intent(SignUp.this, Activity_Login.class);
                startActivity(displayScreen);
            }
        });
    }
}

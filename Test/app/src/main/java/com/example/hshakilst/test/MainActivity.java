package com.example.hshakilst.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        EditText email = (EditText) findViewById(R.id.email);
        EditText pass = (EditText) findViewById(R.id.pass);
        if(email.getText().toString().equals("hshakilst@gmail.com") &&
                pass.getText().toString().equals("1234")){
            Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this, "Login Failed!", Toast.LENGTH_LONG).show();
        }
    }
}

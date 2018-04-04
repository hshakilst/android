package com.example.hshakilst.sqlitedatabasedemo;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextInputEditText name;
    TextInputEditText email;
    TextInputEditText phone;
    TextInputEditText address;
    TextInputLayout nameLayout;
    TextInputLayout emailLayout;
    TextInputLayout phoneLayout;
    TextInputLayout addressLayout;
    Button signUp;
    Button registerListBtn;
    Button cloud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (TextInputEditText) findViewById(R.id.name_edit);
        email = (TextInputEditText) findViewById(R.id.email_edit);
        phone = (TextInputEditText) findViewById(R.id.phone_edit);
        address = (TextInputEditText) findViewById(R.id.address_edit);
        nameLayout = (TextInputLayout) findViewById(R.id.name_layout);
        emailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        phoneLayout = (TextInputLayout) findViewById(R.id.phone_layout);
        addressLayout = (TextInputLayout) findViewById(R.id.address_layout);
        signUp = (Button) findViewById(R.id.signup_btn);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                emailLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                phoneLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addressLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(name.getText().toString().equals("")) && !(email.getText().toString().equals("")) &&
                        !(phone.getText().toString().equals("")) && !(address.getText().toString().equals(""))){
                    DBHelperAdapter dbAdapter = new DBHelperAdapter(getApplicationContext());
                    long id = dbAdapter.insert(name.getText().toString(), email.getText().toString(), phone.getText().toString(),
                            address.getText().toString());
                    if(id > -1){
                        Toast.makeText(getApplicationContext(), "Success! ID:"+id,Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Error!",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    TextInputEditText[] et = {name, email, phone, address};
                    TextInputLayout[] lay = {nameLayout, emailLayout, phoneLayout, addressLayout};
                    int count = 0;
                    for (TextInputEditText e: et) {
                        if (e.getText().toString().equals("")){
                            lay[count].setError("Required field!");
                        }
                        count++;
                    }
                }
            }
        });
        registerListBtn = (Button) findViewById(R.id.register_list_btn);
        registerListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationListActivity.class);
                intent.putExtra("db", "local");
                startActivity(intent);
            }
        });

        cloud = (Button) findViewById(R.id.cloud_list_btn);
        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrationListActivity.class);
                intent.putExtra("db", "cloud");
                startActivity(intent);
            }
        });
    }
}

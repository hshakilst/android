package com.example.hshakilst.sqlitedatabasedemo;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationDetailsActivity extends AppCompatActivity {
    TextView id;
    TextView name;
    TextView email;
    TextView phone;
    TextView address;
    Button delete;
    Button sync;
    private String db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);
        id = (TextView) findViewById(R.id.reg_id);
        name = (TextView) findViewById(R.id.reg_name);
        email = (TextView) findViewById(R.id.reg_email);
        phone = (TextView) findViewById(R.id.reg_phone);
        address = (TextView) findViewById(R.id.reg_address);
        delete = (Button) findViewById(R.id.delete_btn);
        sync = (Button) findViewById(R.id.sync_btn);

        Intent intent = getIntent();
        final long _id = intent.getLongExtra("ID", -1);
        final String _name = intent.getStringExtra("NAME");
        final String _email = intent.getStringExtra("EMAIL");
        final String _phone = intent.getStringExtra("PHONE");
        final String _address = intent.getStringExtra("ADDRESS");
        db = intent.getStringExtra("db");
        id.setText("ID: "+_id);
        name.setText("Name: "+_name);
        email.setText("Email: "+_email);
        phone.setText("Phone: "+_phone);
        address.setText("Address: "+_address);

        if(db.equals("cloud")){
            sync.setVisibility(View.INVISIBLE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(db.equals("local")) {
                     DBHelperAdapter dbAdapter = new DBHelperAdapter(getApplicationContext());
                     int count = dbAdapter.delete(_id);
                     if (count > 0) {
                         Toast.makeText(getBaseContext(), "Delete Success!", Toast.LENGTH_LONG).show();
                     } else {
                         Toast.makeText(getBaseContext(), "Delete Failed!", Toast.LENGTH_LONG).show();
                     }
                 }
                 else if (db.equals("cloud")){
                     RequestQueue requestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                     String url = "http://192.168.163.2:3000/persons/"+_id;
                     StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                         @Override
                         public void onResponse(String response) {
                             Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                         }
                     }, new Response.ErrorListener() {
                         @Override
                         public void onErrorResponse(VolleyError error) {
                             Toast.makeText(getBaseContext(), "Message: "+error, Toast.LENGTH_LONG).show();
                         }
                     });
                     requestQueue.add(request);
                 }
            }
        });

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue requestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                String url = "http://192.168.163.2:3000/persons";
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String message = response;
                        Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("name", _name);
                        params.put("email", _email);
                        params.put("phone", _phone);
                        params.put("address", _address);
                        return params;
                    }
                };
                requestQueue.add(request);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

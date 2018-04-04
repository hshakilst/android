package com.example.hshakilst.sqlitedatabasedemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationListActivity extends AppCompatActivity {
    ListView regList;
    RegisterListAdapter adapter;
    private String db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_list);
        regList = (ListView) findViewById(R.id.reg_list);
        Intent intent = getIntent();
         if(intent.hasExtra("db")) {
             if (intent.getStringExtra("db").equals("local")) {
                 Cursor cursor = new DBHelperAdapter(getApplicationContext()).getAllData();
                 adapter = new RegisterListAdapter(getBaseContext(), cursor);
                 regList.setAdapter(adapter);
                 db = "local";
             } else if (intent.getStringExtra("db").equals("cloud")) {
                 final MatrixCursor matrixCursor = new MatrixCursor(new String[]{"_id", "name", "email", "phone", "address"});
                 RequestQueue requestQueue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();
                 String url = "http://192.168.163.2:3000/persons";
                 JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                         new Response.Listener<JSONArray>() {
                             @Override
                             public void onResponse(JSONArray response) {
                                 for (int i = 0; i < response.length(); i++) {
                                     try {
                                         JSONObject jsonObject = response.getJSONObject(i);
                                         matrixCursor.addRow(new Object[]{jsonObject.getLong("id"),
                                                 jsonObject.getString("name"),
                                                 jsonObject.getString("email"),
                                                 jsonObject.getString("phone"),
                                                 jsonObject.getString("address")});
                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }
                                     SimpleCursorAdapter adapter = new SimpleCursorAdapter(getBaseContext(),
                                             R.layout.custom_list, matrixCursor, new String[]{"name"},
                                             new int[]{R.id.name_list}, SimpleCursorAdapter.IGNORE_ITEM_VIEW_TYPE);
                                     regList.setAdapter(adapter);
                                 }
                             }
                         }, new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                     }
                 });
                 requestQueue.add(request);
                 db = "cloud";
             }
         }

        regList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cur = (Cursor) adapterView.getAdapter().getItem(i);
                long id = cur.getLong(cur.getColumnIndexOrThrow("_id"));
                String name = cur.getString(cur.getColumnIndexOrThrow("name"));
                String email = cur.getString(cur.getColumnIndexOrThrow("email"));
                String phone = cur.getString(cur.getColumnIndexOrThrow("phone"));
                String address = cur.getString(cur.getColumnIndexOrThrow("address"));
                Intent intent = new Intent(getBaseContext(), RegistrationDetailsActivity.class);
                intent.putExtra("ID", id);
                intent.putExtra("NAME", name);
                intent.putExtra("EMAIL", email);
                intent.putExtra("PHONE", phone);
                intent.putExtra("ADDRESS", address);
                intent.putExtra("db", db);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

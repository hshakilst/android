package com.example.hshakilst.jessoretourism;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceDeatailsActivity extends AppCompatActivity {
    private ImageView image;
    private TextView name;
    private TextView desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_deatails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image = (ImageView) findViewById(R.id.details_image);
        name = (TextView) findViewById(R.id.details_name);
        desc = (TextView) findViewById(R.id.details_desc);
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra("Detail");
            if(!bundle.isEmpty()) {
                image.setImageResource(bundle.getInt("Image"));
                name.setText(bundle.getString("Name"));
                desc.setText(bundle.getInt("Desc"));
            }
        }
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

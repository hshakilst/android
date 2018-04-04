package com.example.hshakilst.listviewdemo;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        ArrayList<Student> dataSource = new ArrayList<Student>();
        Student s = new Student(R.mipmap.student_image, "ABCD", "abcd@gmail.com");
        Student s1 = new Student(R.mipmap.student_image, "ABCD", "abcd@gmail.com");
        Student s2 = new Student(R.mipmap.student_image, "ABCD", "abcd@gmail.com");
        Student s3 = new Student(R.mipmap.student_image, "ABCD", "abcd@gmail.com");
        Student s4 = new Student(R.mipmap.student_image, "ABCD", "abcd@gmail.com");
        Student s5 = new Student(R.mipmap.student_image, "ABCD", "abcd@gmail.com");
        dataSource.add(s);
        dataSource.add(s1);
        dataSource.add(s2);
        dataSource.add(s3);
        dataSource.add(s4);
        dataSource.add(s5);
        StudentAdapter adapter = new StudentAdapter(dataSource, this, R.layout.custom_list_item);
        listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), listView.getItemAtPosition(i).toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });*/
    }
}

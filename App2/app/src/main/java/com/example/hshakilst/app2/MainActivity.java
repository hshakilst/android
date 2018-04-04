package com.example.hshakilst.app2;

import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_view);
        btn = (Button) findViewById(R.id.load_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file = null;
                try {
                    file = getApplicationContext().getPackageManager().getApplicationInfo("com.example.hshakilst.app1",
                            PackageManager.GET_META_DATA).dataDir+"/files/data.txt";
                    FileInputStream fileInputStream = new FileInputStream(new File(file));
                    int read = -1;
                    StringBuffer buffer = new StringBuffer();
                    while ((read = fileInputStream.read()) != -1){
                        buffer.append((char) read);
                    }
                    textView.setText(buffer.toString());
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

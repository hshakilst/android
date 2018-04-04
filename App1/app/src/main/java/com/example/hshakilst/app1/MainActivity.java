package com.example.hshakilst.app1;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_text);
        button = (Button) findViewById(R.id.save_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = new Toast(getApplicationContext());
                File file = null;
                FileOutputStream fileOutputStream = null;
                file = getFilesDir();
                try{
                    fileOutputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
                    fileOutputStream.write(editText.getText().toString().getBytes());
                    toast.makeText(getApplicationContext(), "File saved to "+file.getAbsolutePath()+"", Toast.LENGTH_LONG).show();
                }catch (FileNotFoundException fe){
                    fe.printStackTrace();
                    toast.makeText(getApplicationContext(), "File not found!", Toast.LENGTH_LONG).show();
                }catch (IOException e) {
                    e.printStackTrace();
                    toast.makeText(getApplicationContext(), "IO Exception", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

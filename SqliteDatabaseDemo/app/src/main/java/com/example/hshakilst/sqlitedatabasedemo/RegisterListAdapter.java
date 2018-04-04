package com.example.hshakilst.sqlitedatabasedemo;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hshakilst on 6/13/2017.
 */

public class RegisterListAdapter extends CursorAdapter {
    private Context context;
    private Cursor cursor;

    public RegisterListAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name = (TextView) view.findViewById(R.id.name_list);
        System.out.println(cursor.getColumnIndexOrThrow("name"));
        String n = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        name.setText(n);
    }
}

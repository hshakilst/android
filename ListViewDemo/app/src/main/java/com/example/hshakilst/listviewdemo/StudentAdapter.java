package com.example.hshakilst.listviewdemo;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by hshakilst on 5/6/2017.
 */

public class StudentAdapter extends ArrayAdapter<Student> {
    private ArrayList<Student> list;
    private Context context;
    public StudentAdapter(ArrayList<Student> list, @NonNull Context context, @LayoutRes int resource) {
        super(context, resource, list);
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Student student = list.get(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.student_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.student_name);
            viewHolder.email = (TextView) convertView.findViewById(R.id.student_email);
            result = convertView;
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.image.setImageResource(student.getImageResId());
        viewHolder.name.setText(student.getName());
        viewHolder.email.setText(student.getEmail());
        return result;
    }

    public class ViewHolder{
        ImageView image;
        TextView name;
        TextView email;
    }
}

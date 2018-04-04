package com.example.hshakilst.jessoretourism;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hshakilst on 5/11/2017.
 */

public class PlaceAdapter extends ArrayAdapter<Places> {
    private  Context context;
    private ArrayList<Places> list;
    public PlaceAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<Places> objects) {
        super(context, resource, objects);
        this.context = context;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Places places = list.get(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.custom_list, parent, false);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.place_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.place_name);
            viewHolder.desc = (TextView) convertView.findViewById(R.id.place_desc);
            result = convertView;
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.image.setImageResource(places.getImage());
        viewHolder.name.setText(places.getName());
        viewHolder.desc.setText(places.getDescription());

        return result;
    }

    public class ViewHolder{
        ImageView image;
        TextView name;
        TextView desc;
    }
}

package com.example.hshakilst.projectskeleton;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hshakilst on 10/20/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private String[] items;

    public MyAdapter(String[] items){
        this.items = items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.listText.setText(items[position]);

    }
    @Override
    public int getItemCount() {
        return this.items.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listText;
        public MyViewHolder(View itemView) {
            super(itemView);
            listText = (TextView) itemView.findViewById(R.id.text_view);
        }
    }
}

package com.example.hshakilst.firebaseuploader;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.hshakilst.firebaseuploader.FileFragment.OnListFragmentInteractionListener;
import java.util.List;


public class MyFileRecyclerViewAdapter extends RecyclerView.Adapter<MyFileRecyclerViewAdapter.ViewHolder> {

    private final List<File> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFileRecyclerViewAdapter(List<File> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.fileName.setText(mValues.get(position).getFileName());
        holder.fileSize.setText(mValues.get(position).calSise());

        holder.getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentDownload(holder.mItem);
                }
            }
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null){
                    mListener.onListFrgmentShare(holder.mItem);
                }
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(holder.view.getContext(),
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Deleting...");
                progressDialog.show();
                if(mListener != null){
                    mListener.onListFragmentDelete(holder.mItem);
                    mValues.remove(position);
                    notifyDataSetChanged();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageButton getBtn;
        public final ImageButton shareBtn;
        public final ImageButton delBtn;
        public final TextView fileName;
        public final TextView fileSize;
        public final View view;
        public File mItem;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            getBtn = (ImageButton) view.findViewById(R.id.file_download_btn);
            shareBtn = (ImageButton) view.findViewById(R.id.file_share_btn);
            delBtn = (ImageButton) view.findViewById(R.id.file_delete_btn);
            fileName = (TextView) view.findViewById(R.id.file_name_text_view);
            fileSize = (TextView) view.findViewById(R.id.file_size_text_view);
        }
    }
}

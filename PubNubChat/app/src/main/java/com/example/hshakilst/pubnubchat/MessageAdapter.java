package com.example.hshakilst.pubnubchat;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.List;

/**
 * Created by hshakilst on 6/20/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {
    private Context context;
    private List<Message> messages;

    public MessageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Message> messages) {
        super(context, resource, messages);
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getViewTypeCount() {
        return 2;  //as 2 xml are used in listview so we need to return total num of xml
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).isMine()){
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Message message = messages.get(position);
        ViewHolder viewHolder;
        final View result;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            viewHolder = new ViewHolder();
            if (getItemViewType(position) == 1){
                convertView = inflater.inflate(R.layout.left_custom_list, parent, false);
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar_me);
                viewHolder.msg = (TextView) convertView.findViewById(R.id.msg_text_me);
                result = convertView;
                convertView.setTag(viewHolder);
            }
            else {
                convertView = inflater.inflate(R.layout.right_custom_list, parent, false);
                viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar_sender);
                viewHolder.msg = (TextView) convertView.findViewById(R.id.msg_text_sender);
                result = convertView;
                convertView.setTag(viewHolder);
            }

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder().beginConfig().width(40).height(40).endConfig().buildRound(message.getUserInitial(),
                generator.getColor(message.getUserName()));
        viewHolder.avatar.setImageDrawable(drawable);
        viewHolder.msg.setText(message.getContent());
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.up_from_bottom);
        result.startAnimation(animation);
        return result;
    }

    private class ViewHolder{
        ImageView avatar;
        TextView msg;
    }
}

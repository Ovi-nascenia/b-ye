package com.nascenia.biyeta.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.model.InboxAllThreads.Example;
import com.nascenia.biyeta.model.InboxAllThreads.Inbox;
import com.nascenia.biyeta.model.InboxMessageModel;

import java.util.List;

/**
 * Created by god father on 3/13/2017.
 */

public class InboxListAdapter extends RecyclerView.Adapter<InboxListAdapter.ViewHolder> {

    private Example example;
    private int itemLayout;

    public InboxListAdapter(Example example, int itemLayout) {
        this.example = example;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Inbox item = example.getInbox().get(position);
        holder.userName.setText(item.getSenderName());
        holder.time_date.setText(item.getMessage().getCreatedAt());
        holder.message.setText(item.getMessage().getText());

        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(holder.image.getContext(), InboxSingleChat.class);
                Bundle bundle=new Bundle();
                bundle.putInt("sender_id",item.getMessage().getUserId());
                bundle.putInt("receiver_id",item.getMessage().getReceiver());
                Log.e("come",item.getMessage().getUserId() +"  "+item.getMessage().getReceiver());
                in.putExtras(bundle);
                holder.image.getContext().startActivity(in);

            }
        });

        if (!item.getMessage().getIsSeen())
            holder.message.setTypeface(null, Typeface.BOLD);
        else
            holder.message.setTypeface(null, Typeface.NORMAL);


        Glide.
                with(holder.image.getContext()).
                load(item.getSenderImage()).
                placeholder(R.drawable.default_profile_female_icon).
                into(holder.image);
//        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return example.getInbox().size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public ImageView image;
        public TextView message;
        public TextView time_date;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.profile_image);
            message = (TextView) itemView.findViewById(R.id.message);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time_date = (TextView) itemView.findViewById(R.id.time);

        }
    }
}
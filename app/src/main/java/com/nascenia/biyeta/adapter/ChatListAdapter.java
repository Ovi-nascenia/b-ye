package com.nascenia.biyeta.adapter;



import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.InboxSingleChat;
import com.nascenia.biyeta.model.conversation.ChatHead;
import com.nascenia.biyeta.model.conversation.Message;

public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private ChatHead  messagesItems;

    public ChatListAdapter(Context context, ChatHead navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.getMessages().size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.getMessages().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        Message m = messagesItems.getMessages().get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (m.getReceiver()== InboxSingleChat.recevier_id) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.inbox_conversation_item_right,
                    null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.inbox_conversation_item_left,
                    null);
        }

       // TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);

        txtMsg.setText(m.getText());
       // lblFrom.setText(m.getText());

        return convertView;
    }
}
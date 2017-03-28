package com.nascenia.biyeta.adapter;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import com.nascenia.biyeta.utils.Utils;

public abstract class ChatListAdapter extends BaseAdapter {

    private Context context;
  //  private ChatHead  messagesItems;

    private List<Message> arrayListMessage;

    public ChatListAdapter(Context context, List<Message> arrayListMessage) {
        this.context = context;
      //  this.messagesItems = chatHead;
        this.arrayListMessage= arrayListMessage;
        Collections.reverse(this.arrayListMessage);
    }

    public abstract void load();

    @Override
    public int getCount() {
        return arrayListMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListMessage.get(position);
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

        if (position>=arrayListMessage.size()-1)
            load();

        Message m = arrayListMessage.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Identifying the message owner
        if (m.getReceiver()!= InboxSingleChat.current_user_id) {
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
        TextView txtTime= (TextView) convertView.findViewById(R.id.time) ;

        txtMsg.setText(m.getText().trim());
        txtTime.setText(Utils.getTime(m.getCreatedAt()));
       // lblFrom.setText(m.getText());

        return convertView;
    }
}
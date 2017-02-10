package biyeta.nas.biyeta.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import biyeta.nas.biyeta.model.UserProfileInfo;
import biyeta.nas.biyeta.R;

/**
 * Created by saiful on 2/8/17.
 */

public class UserProfileRecyclerAdapter extends RecyclerView.Adapter<UserProfileRecyclerAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private List<UserProfileInfo> userProfileInfoArrayList;
    private Context baseContext;

    public UserProfileRecyclerAdapter(Context baseContext, ArrayList<UserProfileInfo> userProfileInfoArrayList) {
        this.userProfileInfoArrayList = userProfileInfoArrayList;
        this.baseContext = baseContext;
        this.inflater = LayoutInflater.from(baseContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.profile_details_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {


        holder.titleTextView.setText(userProfileInfoArrayList.get(position).getTitle());
        holder.titleResultTextView.setText(userProfileInfoArrayList.get(position).getTitleResult());

    }

    @Override
    public int getItemCount() {
        return this.userProfileInfoArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView titleResultTextView;


        public MyViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            titleResultTextView = (TextView) itemView.findViewById(R.id.titleResultTextView);

        }
    }
}

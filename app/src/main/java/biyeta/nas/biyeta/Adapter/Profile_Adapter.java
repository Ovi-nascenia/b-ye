package biyeta.nas.biyeta.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 1/6/2017.
 */

import com.bumptech.glide.Glide;

import java.util.List;

import biyeta.nas.biyeta.Model.Profile;
import biyeta.nas.biyeta.R;

public abstract class Profile_Adapter extends RecyclerView.Adapter<Profile_Adapter.MyViewHolder> {
    public abstract void load();

    //list of all profile
    private List<Profile> profile_list;
    //parent context
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, profession, age,skin_color,marital_status,weight_status,religion,city;
        public ImageView profile_image;


        ///constractor
        //initialize all the view here
        public MyViewHolder(View view) {
            super(view);
            user_name=(TextView)view.findViewById(R.id.user_name);
            profile_image=(ImageView)view.findViewById(R.id.profile_image);
            profession=(TextView)view.findViewById(R.id.profession);
            age=(TextView)view.findViewById(R.id.age);
            religion=(TextView)view.findViewById(R.id.religion);



        }
    }


    public Profile_Adapter(List<Profile> moviesList) {
        this.profile_list = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        context=parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Profile prfile = profile_list.get(position);
        holder.user_name.setText(prfile.getDisplay_name());
        holder.profession.setText(prfile.getProfessional_group());
        holder.religion.setText(prfile.getReligion());
        Log.e("image_link",prfile.getImage());
        if ((position >= getItemCount() - 1))
            load();

       Glide.with(context)
               .load(Uri.parse("http://test.biyeta.com/"+prfile.getImage()))
               .placeholder(R.drawable.man)
               .into(holder.profile_image);



    }

    @Override
    public int getItemCount() {
        return profile_list.size();
    }
}
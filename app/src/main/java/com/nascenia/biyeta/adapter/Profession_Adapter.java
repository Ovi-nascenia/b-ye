package com.nascenia.biyeta.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.Search_Filter;
import com.nascenia.biyeta.fragment.Search;

/**
 * Created by god father on 2/3/2017.
 */

public class Profession_Adapter extends BaseAdapter {

    private final Context mContext;
    ArrayList<String> profession_name;
    ArrayList<Boolean> is_checked;

    private CheckBox checkBox;

    private String gridItemType;


    public Profession_Adapter(Context context, ArrayList profession_name, ArrayList is_checked, String gridItemType) {
        this.mContext = context;
        this.profession_name = profession_name;
        this.is_checked = is_checked;
        this.gridItemType = gridItemType;
    }


    @Override
    public int getCount() {
        return profession_name.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.i("view", "called");
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            checkBox = (CheckBox) grid.findViewById(R.id.checkbox);
            TextView textView = (TextView) grid.findViewById(R.id.title);
            checkBox.setChecked(is_checked.get(position));
            textView.setText(profession_name.get(position));

            //  addCheckBoxStatustoList(is_checked.get(position), position);

        } else {
            grid = (View) convertView;
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(mContext, "checked", Toast.LENGTH_LONG).show();


                if (isChecked) {

                    addCheckBoxStatustoList(isChecked, position);

                } else {

                    addCheckBoxStatustoList(isChecked, position);
                }

            }
        });


        return grid;

        /*final ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.grid_single, parent, false);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.title);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);

        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();

        }

        viewHolder.checkBox.setChecked(is_checked.get(position));
        viewHolder.titleTextView.setText(profession_name.get(position));
        return convertView;*/


    }

    private void addCheckBoxStatustoList(Boolean aBoolean, int position) {


        if (this.gridItemType.equals("LOCATION") && aBoolean) {

            Search_Filter.locationGridItemCheckedCheckBoxPositionList.add(position);
            Log.i("listdata", "LOCATION  " + Search_Filter.locationGridItemCheckedCheckBoxPositionList.size());


        } else if (this.gridItemType.equals("OCCUPATION") && aBoolean) {

            Search_Filter.occupationGridItemCheckedCheckBoxPositionList.add(position);
            //  Log.i("listdata", "OCCUPATION  " + Search_Filter.occupationGridItemCheckedCheckBoxPositionList.size());

        } else if (this.gridItemType.equals("PROFESSION") && aBoolean) {

            Search_Filter.professionGridItemCheckedCheckBoxPositionList.add(position);
            Log.i("listdata", "PROFESSION  " + Search_Filter.professionGridItemCheckedCheckBoxPositionList.size());

        } else if (this.gridItemType.equals("LOCATION") && !aBoolean) {

            removeItemfromGridViewList(Search_Filter.locationGridItemCheckedCheckBoxPositionList, position);

        } else if (this.gridItemType.equals("OCCUPATION") && !aBoolean) {

            removeItemfromGridViewList(Search_Filter.occupationGridItemCheckedCheckBoxPositionList, position);

        } else if (this.gridItemType.equals("PROFESSION") && !aBoolean) {

            removeItemfromGridViewList(Search_Filter.professionGridItemCheckedCheckBoxPositionList, position);

        } else {
            Toast.makeText(mContext, "no data found", Toast.LENGTH_LONG).show();
        }


    }

    private void removeItemfromGridViewList(ArrayList<Integer> checkBoxPositionList, int value) {


        for (int i = 0; i < checkBoxPositionList.size(); i++) {

            if (value == checkBoxPositionList.get(i)) {
                checkBoxPositionList.remove(i);
                Log.i("listdata", value + " " + this.gridItemType);
            }

        }


    }


    public class ViewHolder {

        CheckBox checkBox;
        TextView titleTextView;


    }
}

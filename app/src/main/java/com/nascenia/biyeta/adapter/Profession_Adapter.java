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


    // 1
    public Profession_Adapter(Context context, ArrayList profession_name, ArrayList is_checked, String gridItemType) {
        this.mContext = context;
        this.profession_name = profession_name;
        this.is_checked = is_checked;
        this.gridItemType = gridItemType;
    }

    // 2
    @Override
    public int getCount() {
        return profession_name.size() - 1;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            checkBox = (CheckBox) grid.findViewById(R.id.checkbox);
            TextView textView = (TextView) grid.findViewById(R.id.title);
            checkBox.setChecked(is_checked.get(position));
            textView.setText(profession_name.get(position));

            addCheckBoxStatustoList(is_checked.get(position), position);

        } else {
            grid = (View) convertView;
        }


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(mContext, "checked", Toast.LENGTH_LONG).show();


               /* switch (gridItemType){


                }*/


                if (isChecked) {
                    Log.i("checkbox", "checked");
                    addCheckBoxStatustoList(isChecked, position);
                } else {
                    Log.i("checkbox", "unchecked");

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
            /*if (aBoolean) {
                Search_Filter.locationGridItemCheckedCheckBoxPositionList.add(position);
            }else{

                for(int i=0;Search_Filter.locationGridItemCheckedCheckBoxPositionList.size();i++){

                }

            }*/

        } else if (this.gridItemType.equals("OCCUPATION")) {

            if (aBoolean) {

                Search_Filter.occupationGridItemCheckedCheckBoxPositionList.add(position);
            }

        } else {
            //PROFESSION
            if (aBoolean) {

                Search_Filter.professionGridItemCheckedCheckBoxPositionList.add(position);
            }
        }


        switch (this.gridItemType) {

            case "LOCATION":

                if (aBoolean) {

                    Search_Filter.locationGridItemCheckedCheckBoxPositionList.add(position);

                }
                break;

            case "OCCUPATION":

                if (aBoolean) {

                    Search_Filter.occupationGridItemCheckedCheckBoxPositionList.add(position);
                }
                break;
            case "PROFESSION":

                if (aBoolean) {

                    Search_Filter.professionGridItemCheckedCheckBoxPositionList.add(position);
                }
                break;


        }

    }


    public class ViewHolder {

        CheckBox checkBox;
        TextView titleTextView;


    }
}

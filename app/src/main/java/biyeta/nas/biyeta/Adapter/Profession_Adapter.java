package biyeta.nas.biyeta.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import biyeta.nas.biyeta.R;

/**
 * Created by god father on 2/3/2017.
 */

public class Profession_Adapter extends BaseAdapter {

    private final Context mContext;
    ArrayList<String> profession_name;
    ArrayList<Boolean>is_checked;

    // 1
    public Profession_Adapter(Context context, ArrayList profession_name,ArrayList is_checked) {
        this.mContext = context;
        this.profession_name = profession_name;
        this.is_checked=is_checked;
    }

    // 2
    @Override
    public int getCount() {
        return profession_name.size()-1;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            CheckBox checkBox = (CheckBox) grid.findViewById(R.id.checkbox);
            TextView textView=(TextView)grid.findViewById(R.id.title) ;
            checkBox.setChecked(is_checked.get(position));
            textView.setText(profession_name.get(position));

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}

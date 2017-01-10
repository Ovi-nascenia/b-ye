package biyeta.nas.biyeta;

/**
 * Created by user on 1/10/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import me.bendik.simplerangeview.SimpleRangeView;

import static me.bendik.simplerangeview.SimpleRangeView.*;

public class Search_Filter extends Activity  {

   // LinearLayout rangeViewsContainer;
    SimpleRangeView age_range,height_range;
    SimpleRangeView fixedRangeView;
    EditText editStart;
    EditText editEnd;
    TextView textView;
    private String[] labels = new String[] {"L1","L2","L3","L4","L5","L6","L7","L8","L9","L10"};

    public ArrayList<String> age_lebels;









    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_search);

        age_lebels=new ArrayList<>();
        for(int i=10;i<50;i++)
            age_lebels.add(i+"");

      //  rangeViewsContainer = (LinearLayout)findViewById(R.id.range_views_container);
       // editStart = (EditText)findViewById(R.id.edit_start);
      //  editEnd = (EditText)findViewById(R.id.edit_end);

        age_range = (SimpleRangeView)findViewById(R.id.age_level);
        height_range=(SimpleRangeView)findViewById(R.id.height_rangeview);

        textView=(TextView)findViewById(R.id.level);



       // fixedRangeView = (SimpleRangeView) findViewById(R.id.fixed_rangeview);
//        rangeView.setOnRangeLabelsListener(this);
//        rangeView.setOnTrackRangeListener(this);





        age_range.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return age_lebels.get(i);
            }
        });



//        rangeView.setActiveLineColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveThumbColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveLabelColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveThumbLabelColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveFocusThumbColor(getResources().getColor(R.color.colorAccent));
//        rangeView.setActiveFocusThumbAlpha(0.26f);


        age_range.setEnd(9);
        age_range.setEndFixed(9);
        age_range.setActiveLabelColor(Color.TRANSPARENT);
        //age_range.setLabelColor(Color.TRANSPARENT);


        age_range.setFixedLabelColor(Color.TRANSPARENT);

        height_range.setActiveLabelColor(Color.TRANSPARENT);


        height_range.setFixedLabelColor(Color.TRANSPARENT);

        age_range.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {




            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {




            }
        });
        height_range.setOnTrackRangeListener(new OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                height_range.setActiveLabelColor(Color.TRANSPARENT);
              //  height_range.setLabelColor(Color.TRANSPARENT);

            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {

                height_range.setActiveLabelColor(Color.TRANSPARENT);
                height_range.setLabelColor(Color.TRANSPARENT);

            }
        });


        height_range.setOnRangeLabelsListener(new OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull State state) {
                return labels[i];
            }
        });
    }
}
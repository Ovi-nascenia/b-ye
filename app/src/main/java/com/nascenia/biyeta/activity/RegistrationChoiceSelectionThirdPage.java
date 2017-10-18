package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.nascenia.biyeta.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import me.bendik.simplerangeview.SimpleRangeView;

public class RegistrationChoiceSelectionThirdPage extends AppCompatActivity {
    SimpleRangeView rangeView_namaj, rangeView_roja, rangeView_hijab;
    private ArrayList<String> namaj_lebel, roja_lebel, hijab_lebel;
    LinearLayout jobLayout, maritalLayout, religionLayout;
    public static String jobArray[] = {"করতে পারে", "অবশ্যই করবে", "করবে না"};
    public static String marriageArray[] = {"অবিবাহিত","বিধবা","তালাকপ্রাপ্ত"};
    public static String religionArray[] = {"মুসলিম","হিন্দু","বৌদ্ধ","খ্রিস্টান","অন্যান্য"};
    public static int job = -1;
    public static int marriage = -1;
    public static int religion = -1;
    public static int selectedPopUp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice_selection_third_page);
        rangeView_namaj = (SimpleRangeView) findViewById(R.id.namaj);
        rangeView_roja = (SimpleRangeView) findViewById(R.id.roja);
        rangeView_hijab = (SimpleRangeView) findViewById(R.id.hajab);

        jobLayout = (LinearLayout) findViewById(R.id.job);

        maritalLayout = (LinearLayout) findViewById(R.id.marital);

        religionLayout = (LinearLayout) findViewById(R.id.religion);

        namaj_lebel = new ArrayList<>();
        roja_lebel = new ArrayList<>();
        hijab_lebel = new ArrayList<>();

        namaj_lebel.add("কখনো না");
        namaj_lebel.add("খুব কম");
        namaj_lebel.add("প্রতিদিন কিন্তু ৫ ওয়াক্তের কম");
        namaj_lebel.add("৫ ওয়াক্ত");

        roja_lebel.add("কখনো না");
        roja_lebel.add("রমজানে ৩০ টির কম");
        roja_lebel.add("পুরো রমজান");

        hijab_lebel.add("করবে না");
        hijab_lebel.add("মতামত নেই");
        hijab_lebel.add("করতেই হবে");

        jobLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 1;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class ));
            }
        });
        maritalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 2;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class ));
            }
        });
        religionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPopUp = 3;
                startActivity(new Intent(RegistrationChoiceSelectionThirdPage.this, PopUpChoiceSelectionThirdPage.class ));
            }
        });

        rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setLabelColor(Color.TRANSPARENT);

        rangeView_namaj.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_namaj.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setLabelColor(Color.TRANSPARENT);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_namaj.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_namaj.setLabelColor(Color.TRANSPARENT);
            }
        });

        rangeView_namaj.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return namaj_lebel.get(i);
            }
        });
        rangeView_namaj.setStart(0);
        rangeView_namaj.setEnd(3);


        rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_roja.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_roja.setLabelColor(Color.TRANSPARENT);

        rangeView_roja.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_roja.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_roja.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_roja.setLabelColor(Color.TRANSPARENT);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_roja.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_roja.setLabelColor(Color.TRANSPARENT);
            }
        });

        rangeView_roja.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return roja_lebel.get(i);
            }
        });
        rangeView_roja.setStart(0);
        rangeView_roja.setEnd(2);


        rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setLabelColor(Color.TRANSPARENT);

        rangeView_hijab.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_hijab.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setLabelColor(Color.TRANSPARENT);
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_hijab.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_hijab.setLabelColor(Color.TRANSPARENT);
            }
        });

        rangeView_hijab.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return hijab_lebel.get(i);
            }
        });
        rangeView_hijab.setStart(0);
        rangeView_hijab.setEnd(2);
    }
}

package com.nascenia.biyeta.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nascenia.biyeta.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import me.bendik.simplerangeview.SimpleRangeView;

public class RegistrationChoiceSelectionFirstPage extends AppCompatActivity {

    SimpleRangeView rangeView_age, rangeView_height, rangeView_color, rangeView_education, rangeView_health;

    private ArrayList<String> skin_lebel, age_lebel, heightLebel, education_lebel, health_level;

    private static int minAgeRangePos;
    private static int maxAgeRangePos;

    Button next;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_choice_selection_first_page);
        rangeView_age = (SimpleRangeView) findViewById(R.id.age);
        rangeView_height = (SimpleRangeView) findViewById(R.id.height);
        rangeView_color = (SimpleRangeView) findViewById(R.id.color);
        rangeView_health = (SimpleRangeView) findViewById(R.id.shape);
        rangeView_education = (SimpleRangeView) findViewById(R.id.education);

        skin_lebel = new ArrayList<>();
        age_lebel = new ArrayList<>();
        heightLebel = new ArrayList<>();
        health_level = new ArrayList<>();
        education_lebel = new ArrayList<>();

        next = (Button) findViewById(R.id.next);

        back = (ImageView) findViewById(R.id.backPreviousActivityImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationChoiceSelectionFirstPage.this,RegistrationPersonalInformation.class ));
            }
        });

        for (int i = 18; i <= 50; i++)
            age_lebel.add(i + "");

        skin_lebel.add("শ্যামলা");
        skin_lebel.add("উজ্জ্বল শ্যামলা");
        skin_lebel.add("ফর্সা");
        skin_lebel.add("অনেক ফর্সা");

        for (int i = 4; i <= 7; i++){
            for (int j = 0; j < 12; j++){
                heightLebel.add(i + "'" + j + "\"");
            }
        }


        health_level.add("স্লিম");
        health_level.add("স্বাস্থ্যবান");
        health_level.add("বেশ স্বাস্থ্যবান");


        education_lebel.add("মাধ্যমিক");
        education_lebel.add("উচ্চমাধ্যমিক পড়ছি");
        education_lebel.add("উচ্চমাধ্যমিক/ডিপ্লোমা");
        education_lebel.add("বাচেলর পড়ছি");
        education_lebel.add("বাচেলর");
        education_lebel.add("মাস্টার্স পড়ছি");
        education_lebel.add("মাস্টার্স");
        education_lebel.add("ডক্টরেট পড়ছি");
        education_lebel.add("ডক্টরেট");

        rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_age.setLabelColor(Color.TRANSPARENT);

        rangeView_age.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_age.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);
                minAgeRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_age.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_age.setLabelColor(Color.TRANSPARENT);
                maxAgeRangePos = i;
            }
        });

        rangeView_age.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return age_lebel.get(i);
            }
        });

        rangeView_age.setStart(0);
        rangeView_age.setEnd(7);



        rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_color.setLabelColor(Color.TRANSPARENT);

        rangeView_color.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_color.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                minAgeRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_color.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_color.setLabelColor(Color.TRANSPARENT);
                maxAgeRangePos = i;
            }
        });

        rangeView_color.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return skin_lebel.get(i);
            }
        });
        rangeView_color.setStart(0);
        rangeView_color.setEnd(3);



        rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_height.setLabelColor(Color.TRANSPARENT);

        rangeView_height.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_height.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);
                minAgeRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_height.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_height.setLabelColor(Color.TRANSPARENT);
                maxAgeRangePos = i;
            }
        });

        rangeView_height.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return heightLebel.get(i);
            }
        });
        rangeView_height.setStart(10);
        rangeView_height.setEnd(19);



        rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_health.setLabelColor(Color.TRANSPARENT);

        rangeView_health.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_health.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_health.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_health.setLabelColor(Color.TRANSPARENT);
                minAgeRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_health.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_health.setLabelColor(Color.TRANSPARENT);
                maxAgeRangePos = i;
            }
        });

        rangeView_health.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return health_level.get(i);
            }
        });

        rangeView_health.setStart(0);
        rangeView_health.setEnd(1);



        rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
        rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
        rangeView_education.setLabelColor(Color.TRANSPARENT);

        rangeView_education.setFixedLabelColor(Color.TRANSPARENT);
        rangeView_education.setOnTrackRangeListener(new SimpleRangeView.OnTrackRangeListener() {
            @Override
            public void onStartRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setFixedThumbLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                minAgeRangePos = i;
            }

            @Override
            public void onEndRangeChanged(@NotNull SimpleRangeView simpleRangeView, int i) {
                rangeView_education.setActiveLabelColor(Color.TRANSPARENT);
                rangeView_education.setLabelColor(Color.TRANSPARENT);
                maxAgeRangePos = i;
            }
        });

        rangeView_education.setOnRangeLabelsListener(new SimpleRangeView.OnRangeLabelsListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public String getLabelTextForPosition(@NotNull SimpleRangeView simpleRangeView, int i, @NotNull SimpleRangeView.State state) {
                return education_lebel.get(i);
            }
        });
        rangeView_education.setStart(2);
        rangeView_education.setEnd(6);


    }
}

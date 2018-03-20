package com.nascenia.biyeta.activity;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.nascenia.biyeta.IntigrationGoogleAnalytics.AnalyticsApplication;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.faq.ExpandableAdapter;
import com.nascenia.biyeta.adapter.faq.ParentObj;
import java.util.ArrayList;

public class FAQActivity extends CustomActionBarActivity {


    private Tracker mTracker;
    private AnalyticsApplication application;
    private  RecyclerView recyclerView;
    @Override
    void setUpToolBar(String title, Context context) {
        super.setUpToolBar(title, context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq);
        setUpToolBar("FAQ",this);


        /*Google Analytics*/
        application = (AnalyticsApplication) AnalyticsApplication.getContext();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder()
                .setNewSession()
                .build());


        ArrayList<ParentObj> expandableListDetail=new ArrayList<>();

        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("বিয়েটাতে রেজিস্ট্রেশন করতে আমাদের ওয়েবসাইট www.biyeta.com এ গিয়ে ইমেইল আইডি এবং ৪ টি ডিজিট এর পাসওয়ার্ড দিয়ে রেজিস্ট্রেশন করতে হবে। এরপর নিজ তথসমূহ এবং পছন্দের তথ্যগুলো পূরণ করে খুঁজে নিন নিজের মনের মত পাত্র/পাত্রী।");

        ArrayList<String> ans2 = new ArrayList<String>();
        ans2.add("বিয়েটা ব্যবহার খুব সহজ। প্রথমে রেজিস্ট্রেশন করে নিজের প্রোফাইল তৈরি করুন। বিয়েটা আপনার পছন্দের উপর ভিত্তি করে ম্যাচ দেখাবে। আপনি একটি প্রিমিয়াম প্ল্যান নির্বাচন করুন। তারপর, কাউকে পছন্দ হলে, তাকে পুরো বায়োডাটা দেখার জন্য তাকে অনুরোধ করুন। এসময় আপনি তার যোগাযোগের ঠিকানা ও ফোন নম্বর ছাড়া সবই দেখতে পাবেন। পরবর্তীতে তাকে যোগাযোগের অনুরোধ পাঠাতে পারবেন এবং তা গৃহীত হলে তার সাথে যোগাযোগ করতে পারবেন। ");

        ArrayList<String> ans3 = new ArrayList<String>();
        ans3.add("আপনি ছাড়া আপনার বাবা, মা, ভাই ও বোন বিয়েটাতে অ্যাকাউন্ট তৈরি করতে পারবেন। ");

        ArrayList<String> ans4 = new ArrayList<String>();
        ans4.add("বিয়েটাতে রেজিস্ট্রেশন সম্পূর্ণ ফ্রী। যোগাযোগের অনুরোধ এবং বায়োডাটার অনুরোধ পাঠাতে আপনাকে পেইড মেম্বার হতে হবে। আমাদের ৩টি পেইড প্ল্যান আছেঃ সিলভার (১০০০ টাকা), গোল্ড (২৫০০ টাকা) এবং প্লাটিনাম (৫০০০ টাকা)। ");

        ArrayList<String> ans5 = new ArrayList<String>();
        ans5.add("বিয়েটার কাছে সবচেয়ে গুরুত্বপূর্ণ হল পাত্র-পাত্রী ও তাদের পরিবারের পছন্দ। তাদের ভালোলাগার উপর ভিত্তি করে বিয়েটার নিজস্ব এলগোরিদমের মাধ্যমে ম্যাচিং করানো হয়। পরবর্তীতে পাত্র-পাত্রী ম্যাচিং এর নানাদিক দেখতে পারেন ও সিদ্ধান্ত নিতে পারেন। ");
        ans5.add("পাত্র-পাত্রী নিজে বায়োডাটা তৈরি করতে পারেন। আবার তাদের বাবা, মা, ভাই ও বোন বায়োডাটা তৈরি করে দিতে পারেন।");
        ans5.add("ম্যাচিং এর পাশাপাশি জোর দেয়া হয় প্রাইভেসিতে। প্রথমে পাত্র-পাত্রী তাদের পছন্দের মানুষের কিছু মূল আকর্ষণ দেখতে পারেন। পরবর্তীতে তার অনুমতি সাপেক্ষে ছবিসহ পুরো বায়োডাটা দেখতে পারেন। ");
        ans5.add("ক্রেডিট কার্ড এবং বিকাশ এর মাধ্যমে আপনি বিয়েটাতে টাকা লোড করতে পারবেন। ");

        ArrayList<String> ans6 = new ArrayList<String>();
        ans6.add("“শুধু মাত্র অন্যান্য পারিবারিক পরিচয়” ছাড়া অন্য অংশগুলো শুরুতেই পূরণ করা বাধ্যতা মূলক। আপনার সাথে ম্যাচ করানোর জন্য এই তথ্যগুলো প্রয়োজন। ");

        ArrayList<String> ans7 = new ArrayList<String>();
        ans7.add("না। আপনি যেখানে চাওয়া হয় শুধুমাত্র সেখানেই মোবাইল নাম্বার, ইমেইল এড্রেস ও ফেইসবুক আইডি ব্যবহার করতে পারবেন। অন্য কোথাও উল্লেখ করলে আপনার অ্যাকাউন্ট ব্যান হতে পারে। ");

        ArrayList<String> ans8 = new ArrayList<String>();
        ans8.add("আপনার ব্যাপারে বিশ্বস্ততা বৃদ্ধির জন্য ভেরিফিকেশন করতে উৎসাহিত করা হচ্ছে। ");

        ArrayList<String> ans9 = new ArrayList<String>();
        ans9.add("আপনি আপনার পছন্দ যেকোন সময় পরিবর্তন করতে পারেন। সে অনুযায়ী আপনার ম্যাচ দেখানো হবে। ");

        ArrayList<String> ans10 = new ArrayList<String>();
        ans10.add("বিয়েটাতে রয়েছে ৩০০০০ এর অধিক পাত্র-পাত্রী। বিয়েটাতে অ্যাকাউন্ট খোলা মাত্রই "
                + "আপনি পাচ্ছেন আপনার পছন্দ অনুসারে পাত্র/পাত্রীর শর্ট-প্রোফাইল। যে কোন প্ল্যান "
                + "(সিলভার, গোল্ড, প্লাটিনাম) নির্বাচন করে আপনার পছন্দ অনুসারে বায়োডাটা দেখার "
                + "অনুরোধ পাঠিয়ে দেখে নিতে পারবেন  আপনার পছন্দের পাত্র/পাত্রীর সম্পূর্ণ "
                + "প্রোফাইলটি। যোগাযোগের অনুরোধ পাঠানোর মাধ্যমে তাদের সাথে যোগাযোগ করে খুঁজে নিতে"
                + " পারেন আপনার জীবনসঙ্গী।\n ");

        ArrayList<String> ans11 = new ArrayList<String>();
        ans11.add("বিয়েটাতে গোপনীয়তা রক্ষা অত্যন্ত সহজ। বিয়েটাতে আপনার সম্পূর্ণ প্রোফাইল আপনার অনুমতি ছাড়া কেউ দেখতে পারবে না। আপনার ছবি দেখার ক্ষেত্রেও প্রাইভেসি দিয়ে রাখতে পারবেন। আপনি অনুরোধ গ্রহণ না করা পর্যন্ত কেউ আপনার বায়োডাটা/যোগাযোগের তথ্য পাবে না। ");

        ArrayList<String> ans12 = new ArrayList<String>();
        ans12.add("আমাদের ইউজারদের অনেকের মনে এমন প্রশ্ন রয়ে গেছে, কিভাবে পছন্দের পাত্র অথবা "
                + "পাত্রীর সাথে যোগাযোগ করা যাবে। আর তাই সকলের সুবিধার জন্য যোগাযোগের সম্পূর্ণ "
                + "প্রক্রিয়া জানানো হল-\n"
                + "\n"
                + "যদি কোন পাত্র অথবা পাত্রীর বায়োডাটা আপনার পছন্দ হয় সেক্ষেত্রে আপনি তাকে "
                + "বায়োডাটা দেখার অনুরোধ পাঠাতে পারবেন।\n"
                + "\n"
                + "সেই পাত্র অথবা পাত্রী যদি আপনার অনুরোধ গ্রহণ করে এবং তার সম্পূর্ণ বায়োডাটা "
                + "দেখে আপনার পছন্দ হয়, তখন তাকে আপনি যোগাযোগের অনুরোধ পাঠাবেন।");

        ArrayList<String> ans13 = new ArrayList<String>();
        ans13.add("পূর্ণ বায়োডাটার অনুরোধ পাঠাতে এবং যোগাযোগের অনুরোধ পাঠাতে আপনাকে একটি প্রিমিয়াম প্ল্যান এর মধ্যে থাকতে হবে। আমাদের প্রিমিয়াম প্ল্যানগুলো হচ্ছেঃ সিলভার, গোল্ড এবং প্লাটিনাম। যদি যোগাযোগের অনুরোধ পাঠানোর ৭ দিন এর মধ্যে পাত্র বা পাত্রীপক্ষ সেটি গ্রহণ না করে তাহলে সেই যোগাযোগের অনুরোধটি ফেরত আসবে এবং তার বদলে আরেকজনকে যোগাযোগের অনুরোধ পাঠানো যাবে।");

        ArrayList<String> ans14 = new ArrayList<String>();
        ans14.add("প্রিমিয়াম প্ল্যান নির্বাচন করতে আপনাকে আপনার অ্যাকাউন্ট এ টাকা লোড করতে হবে। ক্রেডিট কার্ড (ভিসা, মাস্টারকার্ড), এবং বিকাশের মাধ্যমে আপনি আপনার বিয়েটা অ্যাকাউন্ট-এ টাকা লোড করতে পারবেন। ");

        ArrayList<String> ans15 = new ArrayList<String>();
        ans15.add("আপনি যদি নির্ধারিত স্থান ছাড়া প্রোফাইল এর অন্য কোথাও কোন যোগাযোগের তথ্য দিয়ে "
                + "থাকেন (যেমন ফেসবুক আইডি, ইমেইল, ফোন নাম্বার ইত্যাদি) তাহলে আপনার অ্যাকাউন্ট "
                + "বন্ধ করে দেয়া হবে। এছাড়া আপনার বয়স ২১ এর নিচে (ছেলে) অথবা ১৮ এর নিচে (মেয়ে) "
                + "হলে আপনার অ্যাকাউন্ট বন্ধ করে দেয়া হবে। আপনি যদি কোন ভুল বা অসম্পূর্ণ তথ্য বা "
                + "ছবি দিয়ে থাকেন বা আপনার অ্যাকাউন্ট দেখে মনে হয় আপনি বর্তমানে বিয়ের ব্যাপারে "
                + "আগ্রহী নন তাহলে আপনার অ্যাকাউন্ট বন্ধ করে দেয়া হতে পারে।  বিয়েটা-তে আপনার "
                + "একাধিক অ্যাকাউন্ট থাকলে আপনার অ্যাকাউন্ট বন্ধ করে দেয়া হতে পারে। এছাড়া আপনার "
                + "সাথে ফোন এ কথা বলে যদি মনে হয় আপনি ইচ্ছাকৃতভাবে মিথ্যা বা ভুল তথ্য বা ছবি "
                + "দিয়েছেন তাহলে আপনার অ্যাকাউন্ট বন্ধ করে দেয়া হবে।\n"
                + "এছাড়াও যদি কোনো ইউজার নিজের থেকে আইডি ব্যবহার করতে না চায় তাহলেও বিয়েটা থেকে "
                + "আইডি বন্ধ করে দেয়া হবে।  ");

        expandableListDetail.add(new ParentObj("১। কিভাবে বিয়েটাতে রেজিস্ট্রেশন করবেন? ", ans1));
        expandableListDetail.add(new ParentObj("২। বিয়েটা কিভাবে ব্যবহার করতে হয়? ", ans2));
        expandableListDetail.add(new ParentObj("৩। আমি ছাড়া আর কে আমার বায়োডাটা রেজিস্ট্রেশন করতে পারবে? ", ans3));
        expandableListDetail.add(new ParentObj("৪। বিয়েটাতে রেজিস্ট্রেশনের জন্য আমাকে কত টাকা দিতে হবে? ", ans4));
        expandableListDetail.add(new ParentObj("৫। বিয়েটার মূল ফিচার কি কি? ", ans5));
        expandableListDetail.add(new ParentObj("৬। রেজিস্ট্রেশন করতে অনেক সময় লাগে। আমাকে কি পুরো বায়োডাটা ফিল আপ করতে হবে? ", ans6));
        expandableListDetail.add(new ParentObj("৭। আমি কি আমার ইমেইল, ফোন নাম্বার এসব যথাযথ স্থান ছাড়া আর কোথাও উল্লেখ করতে পারবো? করলে তার ফলাফল কি হতে পারে? ", ans7));
        expandableListDetail.add(new ParentObj("৮। আমাকে কেন ভেরিফিকেশনের জন্য উৎসাহিত করা হচ্ছে? ", ans8));
        expandableListDetail.add(new ParentObj("৯। আমি কি আমার পছন্দ পরিবর্তন করতে পারবো? ", ans9));
        expandableListDetail.add(new ParentObj("১০। কিভাবে বিয়েটা আপনাকে সহায়তা করতে পারে আপনার মনের মত পাত্র/পাত্রী খুঁজতে? ", ans10));
        expandableListDetail.add(new ParentObj("১১। বিয়েটাতে কিভাবে গোপনীয়তা রক্ষা সম্ভব? ", ans11));
        expandableListDetail.add(new ParentObj("১২। বিয়েটা ইউজার কিভাবে যোগাযোগ করবেন পাত্র অথবা পাত্রীপক্ষের সাথে? ", ans12));
        expandableListDetail.add(new ParentObj("১৩। পূর্ণ বায়োডাটা দেখার পাঠানোর অনুরোধ এবং "
                + "যোগাযোগের অনুরোধ পাঠানোর নিয়ম\n ", ans13));
        expandableListDetail.add(new ParentObj("১৪। নিজ অ্যাকাউন্ট এ টাকা লোড করে প্ল্যান নির্বাচন এর নিয়ম। ", ans14));
        expandableListDetail.add(new ParentObj("১৫। বিয়েটা কী কী কারণে আমার অ্যাকাউন্ট বন্ধ করতে পারে? ", ans15));


        recyclerView= (RecyclerView) findViewById(R.id.recylcer_list);

        ExpandableAdapter adapter =new ExpandableAdapter(expandableListDetail);
        adapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
            @Override
            public void onParentExpanded(int parentPosition) {

                if (parentPosition==8)
                   recyclerView.smoothScrollToPosition(23);

            }

            @Override
            public void onParentCollapsed(int parentPosition) {

            }
        });
        recyclerView.setAdapter(adapter);

        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        /*Google Analytics*/
        mTracker.setScreenName("FAQ");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}

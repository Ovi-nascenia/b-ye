package com.nascenia.biyeta.activity;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.nascenia.biyeta.R;
import com.nascenia.biyeta.adapter.faq.ExpandableAdapter;
import com.nascenia.biyeta.adapter.faq.ParentObj;
import java.util.ArrayList;

public class FAQActivity extends CustomActionBarActivity {


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

        ArrayList<ParentObj> expandableListDetail=new ArrayList<>();

        ArrayList<String> ans1 = new ArrayList<String>();
        ans1.add("বিয়েটা ব্যবহার খুব সহজ। প্রথমে রেজিস্ট্রেশন করে নিজের প্রোফাইল তৈরি করুন। বিয়েটা আপনার পছন্দের উপর ভিত্তি করে ম্যাচ দেখাবে। যার সাথে আপনার ভালো মেলে, পুরো বায়োডাটা দেখার জন্য তাকে অনুরোধ করুন। এসময় আপনি তার যোগাযোগের ঠিকানা ও ফোন নম্বর ছাড়া সবই দেখতে পাবেন। পরবর্তীতে বিয়েটাকে সামান্য মূল্য প্রদানের মাধ্যমে তার সাথে যোগাযোগ করতে পারবেন। ");


        ArrayList<String> ans2 = new ArrayList<String>();
        ans2.add("আপনি ছাড়া আপনার বাবা, মা, ভাই ও বোন বিয়েটাতে একাউন্ট তৈরি করতে পারবেন। ");

        ArrayList<String> ans3 = new ArrayList<String>();
        ans3.add("বিয়েটাতে রেজিস্ট্রেসন ফ্রি। শুধুমাত্র আরেকজনের সাথে যোগাযোগ করার আগে টাকা দিতে হয়। ");

        ArrayList<String> ans4 = new ArrayList<String>();
        ans4.add("বিয়েটার কাছে সবচেয়ে গুরুত্বপূর্ণ হল পাত্র-পাত্রী ও তাদের পরিবারের পছন্দ। তাদের ভালোলাগার উপর ভিত্তি করে বিয়েটার নিজস্ব এলগোরিদমের মাধ্যমে ম্যাচিং করানো হয়। পরবর্তীতে পাত্র-পাত্রী ম্যাচিং এর নানাদিক দেখতে পারেন ও সিদ্ধান্ত নিতে পারেন। ");
        ans4.add("পাত্র-পাত্রী নিজে বায়োডাটা তৈরি করতে পারেন। আবার তাদের বাবা,মা,ভাই ও বোন বায়োডাটা তৈরি করে দিতে পারেন। ");
        ans4.add("ম্যাচিং এর পাশাপাশি জোর দেয়া হয় প্রাইভেসীতে।প্রথমে পাত্র-পাত্রী তাদের পছন্দের মানুষের কিছু মূল আকর্ষণ দেখতে পারেন। পরবর্তীতে তার অনুমতি সাপেক্ষে ছবিসহ পুরো বায়োডাটা দেখতে পারেন। পুরো বায়োডাটা দেখার জন্য কোন চার্জ করা হয়না। ");
        ans4.add("পুরো বায়োডাটা দেখার পর বিয়েটাকে সামান্য মূল্য প্রদানের মাধ্যমে পাত্র-পাত্রী তাদের পছন্দের সঙ্গীর সাথে যোগাযোগ করে পারেন। অন্যান্য বিয়ের প্ল্যাটফর্মের মত বিয়েটাতে কোন ব্যয়বহুল প্যাকেজ নেই। বরং প্রতিটি যোগাযোগের জন্য পাত্র-পাত্রী মূল্য প্রদান করে থাকেন। ");
        ans4.add("বায়োডাটা তৈরির পর পাত্র-পাত্রী অথবা তাদের পরিবার বিনামূল্যে ডাউনলোড করতে পারেন। ");
        ans4.add("দেশী-বিদেশী সবরকম ক্রেডিট/ডেবিট কার্ড এবং পেপ্যাল বিয়েটা সাপোর্ট করে। ");

        ArrayList<String> ans5 = new ArrayList<String>();
        ans5.add("“শুধু মাত্র অন্যান্য পারিবারিক পরিচয়” ছাড়া অন্য অংশগুলো শুরুতেই পূরণ করা বাধ্যতা মূলক। আপনার সাথে ম্যাচ করানোর জন্য এই তথ্যগুলো প্রয়োজন।  ");

        ArrayList<String> ans6 = new ArrayList<String>();
        ans6.add("না। আপনি যেখানে চাওয়া হয় শুধুমাত্র সেখানেই ইমেইল, ফোন নাম্বার, ঠিকানা ইত্যাদি পূরণ করতে পারবেন। অন্য কোথাও উল্লেখ করলে আপনার অ্যাকাউন্ট ব্যান হতে পারে। ");

        ArrayList<String> ans7 = new ArrayList<String>();
        ans7.add("আপনার ব্যাপারে বিশ্বস্ততা বৃদ্ধির জন্য ভেরিফিকেশন করতে উৎসাহিত করা হচ্ছে। ");

        ArrayList<String> ans8 = new ArrayList<String>();
        ans8.add("আপনি আপনার পছন্দ যেকোন সময় পরিবর্তন করতে পারেন। সে অনুযায়ী আপনার ম্যাচ দেখানো হবে। ");

        ArrayList<String> ans9 = new ArrayList<String>();
        ans9.add("আপনি যেকোন সময় নিজের বায়োডাটা doc অথবা pdf ফরম্যাটে ডাউনলোড করতে পারেন। ");

        expandableListDetail.add(new ParentObj("১। বিয়েটা কিভাবে ব্যবহার করতে হয়? ", ans1));
        expandableListDetail.add(new ParentObj("২। আমি ছাড়া আরকে আমার বায়োডাটা রেজিস্ট্রেশন করতে পারবে? ", ans2));
        expandableListDetail.add(new ParentObj("৩। বিয়েটাতে রেজিস্ট্রেশনের জন্য আমাকে কত টাকা দিতে হবে? ", ans3));
        expandableListDetail.add(new ParentObj("৪। বিয়েটার মূল ফিচার কি কি? ", ans4));
        expandableListDetail.add(new ParentObj("৫। রেজিস্ট্রেশন করতে অনেক সময় লাগে। আমাকে কি পুরো বায়োডাটা ফিল আপ করতে হবে? ", ans5));
        expandableListDetail.add(new ParentObj("৬। আমি কি আমার ইমেইল, ফোন নাম্বার এসব যথাযথ স্থান ছাড়া আর কোথাও উল্লেখ করতে পারবো? করলে তার ফলাফল কি হতে পারে? ", ans6));
        expandableListDetail.add(new ParentObj("৭। আমাকে কেন ভেরিফিকেশনের জন্য উৎসাহিত করা হচ্ছে? ", ans7));
        expandableListDetail.add(new ParentObj("৮। আমি কি আমার পছন্দ পরিবর্তন করতে পারবো? ", ans8));
        expandableListDetail.add(new ParentObj("৯। আমি আমার বায়োডাটা ডাউনলোড করতে পারবো? ", ans9));


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
}

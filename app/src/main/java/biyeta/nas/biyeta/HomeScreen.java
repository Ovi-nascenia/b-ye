package biyeta.nas.biyeta;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import biyeta.nas.biyeta.Fragment.Favourite;
import biyeta.nas.biyeta.Fragment.Inbox;
import biyeta.nas.biyeta.Fragment.Match;
import biyeta.nas.biyeta.Fragment.Profile;
import biyeta.nas.biyeta.Fragment.Search;


public class HomeScreen extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {

            R.drawable.search,
            R.drawable.match,
            R.drawable.fev,
            R.drawable.inbox,
            R.drawable.profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
       // viewPager.setOffscreenPageLimit(0);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(R.string.search);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.search, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(R.string.match);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.match, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(R.string.fev);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.fev, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabfour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfour.setText(R.string.inbox);
        tabfour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.inbox, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabfour);

        TextView tabfive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabfive.setText(R.string.profile);
        tabfive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.profile, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabfive);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Search(), "ONE");
        adapter.addFrag(new Match(), "TWO");
        adapter.addFrag(new Favourite(), "THREE");
        adapter.addFrag(new Inbox(), "FOUR");
        adapter.addFrag(new Profile(), "FIVE");


        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
       public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new Search();

                case 1:
                    return new Match();

                case 2:
                    return new Favourite();

                case 3:
                    return new Inbox();

                case 4:
                    return new Profile();

                default:

                    break;
            }

            return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
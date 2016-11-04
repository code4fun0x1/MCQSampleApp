package com.ahdollars.crazyeights.mcqsampleapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    Toolbar toolbar;
    TabLayout tablayout;
    CoordinatorLayout coordinator;
    MyAdapter adapter;
    FragmentManager manager;
    SharedPreferences myPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myPref=getSharedPreferences("ADMIN",MODE_PRIVATE);


        coordinator=(CoordinatorLayout)findViewById(R.id.main_layout);
        tablayout=(TabLayout)findViewById(R.id.tablayout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        pager=(ViewPager)findViewById(R.id.viewpager);
        manager=getSupportFragmentManager();
        adapter=new MyAdapter(manager);
        setSupportActionBar(toolbar);
        pager.setAdapter(adapter);
        tablayout.setupWithViewPager(pager);
        pager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setTabsFromPagerAdapter(adapter);


    }



    class MyAdapter extends FragmentPagerAdapter{

        FragmentManager m;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            m=fm;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment=null;
            if(position==0){
                if(m.findFragmentByTag("ZERO")==null) {
                    fragment = new AddQuestionFragment();

                }else{
                    fragment=(AddQuestionFragment)m.findFragmentByTag("ZERO");
                }
            }
            if(position==1){
                fragment=new TestFragment();

            }
            if(position==2){
                if(m.findFragmentByTag("TWO")==null) {
                    fragment = new ResultFragment();
                }else{
                    fragment=m.findFragmentByTag("TWO");
                }
            }
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position==0){
                return "ADD QUES.";
            }
            if(position==1){
                return "TAKE TEST";
            }
            if(position==2){
                return "RESULT";
            }
            return "Error";
        }
    }




}

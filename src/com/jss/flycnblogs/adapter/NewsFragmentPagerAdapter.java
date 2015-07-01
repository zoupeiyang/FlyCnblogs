package com.jss.flycnblogs.adapter;

import java.util.ArrayList;
import java.util.List;

import com.jss.flycnblogs.BlogFragment;
import com.jss.flycnblogs.HotBlogFragment;
import com.jss.flycnblogs.NewsFragment;
import com.jss.flycnblogs.RecommendBlogFragment;
import com.jss.flycnblogs.RecommendNewsFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
   
    
    private static List<Fragment> fragments;
    
    private Context mContext;
     static{
    	 fragments=new ArrayList<Fragment>();
         fragments.add(new NewsFragment());
         fragments.add(new RecommendNewsFragment());
    }
    public NewsFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
       
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String[]{"最新新闻", "推荐新闻"
              }[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
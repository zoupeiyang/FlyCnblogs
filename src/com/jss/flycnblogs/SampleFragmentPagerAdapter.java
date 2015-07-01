package com.jss.flycnblogs;

import java.util.ArrayList;
import java.util.List;

import com.jss.flycnblogs.BlogFragment;
import com.jss.flycnblogs.HotBlogFragment;
import com.jss.flycnblogs.NewsFragment;
import com.jss.flycnblogs.RecommendBlogFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
   
    
    private static List<Fragment> fragments;
    
    private Context mContext;
     static{
    	 fragments=new ArrayList<Fragment>();
         fragments.add(new RecommendBlogFragment());
         fragments.add(new HotBlogFragment());
         fragments.add(new BlogFragment());
    }
    public SampleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
       
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String[]{"10���Ƽ�", "48Сʱ����",
                "���²���"}[position];
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
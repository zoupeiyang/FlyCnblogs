package com.jss.flycnblogs;


import com.jss.flycnblogs.adapter.NewsFragmentPagerAdapter;
import com.jss.flycnblogs.adapter.SampleFragmentPagerAdapter;
import com.jss.flycnblogs.control.LineTabIndicator;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NewsMainFragment extends Fragment {
	
	 private LineTabIndicator lineTabIndicator;
	 private ViewPager viewPager;
	 private View rootView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView =inflater.inflate(R.layout.fragment_line_tab, container,false);
        lineTabIndicator = (LineTabIndicator) rootView.findViewById(R.id.line_tab_indicator);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        viewPager.setAdapter(new NewsFragmentPagerAdapter(this.getActivity(), getChildFragmentManager()));//±ÿ–Î”√getChildFragmentManager
        lineTabIndicator.setViewPager(viewPager);
		return rootView;
		    
	}




}

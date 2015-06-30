package com.jss.flycnblogs;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.jss.flycnblogs.adapter.BlogListAdapter;
import com.jss.flycnblogs.entity.Blog;
import com.jss.flycnblogs.enums.PullToRefreshFlag;
import com.jss.flycnblogs.service.BlogService;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MoreFragment extends Fragment {
	    View rootView;
	    private Switch switch_close_sound;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		    initControls(inflater,container);
	
 		   return rootView;
		    
	}

	/**
	 * ³õÊ¼»¯¿Ø¼þ
	 */
	private void initControls(LayoutInflater inflater,ViewGroup container)
	{
		
		 rootView =inflater.inflate(R.layout.fragment_more, container,false);
		 switch_close_sound = (Switch)rootView.findViewById(R.id.switch_close_sound);
		 switch_close_sound.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
				{
					
				}
				else {
					
				}
				
			}
		});
		 
		
	}



}

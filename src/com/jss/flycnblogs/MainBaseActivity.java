package com.jss.flycnblogs;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RadioButton;

public class MainBaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	
	}
	
	private void init()
	{
		ActionBar actionBar = getActionBar( );
		if( null != actionBar ){
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);

			LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.actionbar_layout, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			actionBar.setCustomView(v,layout);
		}
	}
	
	public void actionbarOnClick(View view)
	{
		switch (view.getId()) {
		case R.id.imageview_back:
			this.finish();
			break;

		default:
			break;
		}
	}

}

package com.jss.flycnblogs;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends MainBaseActivity {
	
	private static BlogMainFragment blogMainFragment=new BlogMainFragment();
	private static NewsMainFragment newsMainFragment=new NewsMainFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//默认打开触发博客这个按钮
		RadioButton radioButton = (RadioButton)findViewById(R.id.btn_blog);
		//LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		//View v = inflator.inflate(R.layout.actionbar_layout, null);
	 ImageView imageView=	(ImageView)findViewById(R.id.imageview_back);
	 TextView textView =(TextView)findViewById(R.id.textview_back);
	 TextView textViewTitle =(TextView)findViewById(R.id.textview_title);
	 imageView.setVisibility(View.GONE);
	 textView.setVisibility(View.GONE);
	 textViewTitle.setText("博客园");
		radioButton.performClick();
	//	getActionBar().setDisplayShowHomeEnabled(false);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 底部菜单按钮点击事件处理方法
	 * @param v
	 */
	public void rdButtonClick(View v)
	{
		Fragment fragment=new BlogFragment();
		int id =v.getId();
		switch (id) {
		case R.id.btn_blog:
			fragment=blogMainFragment;
			break;
		case R.id.btn_new:
			fragment=newsMainFragment;
			break;
		case R.id.btn_reading_list:
			fragment=new HotBlogFragment();
			break;
		case R.id.btn_member:
			fragment=new MoreFragment();
			break;

		default:
		//	fragment=new BlogFragment();
			break;
		}
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.main_content, fragment).commit();
		
	}
}

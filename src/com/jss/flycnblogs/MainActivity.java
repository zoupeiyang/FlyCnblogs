package com.jss.flycnblogs;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		Fragment fragment;
		int id =v.getId();
		switch (id) {
		case R.id.btn_blog:
			fragment=new BlogFragment();
			break;
		case R.id.btn_new:
			fragment=new BlogFragment();
			break;
		case R.id.btn_reading_list:
			fragment=new BlogFragment();
			break;
		case R.id.btn_member:
			fragment=new BlogFragment();
			break;

		default:
			fragment=new BlogFragment();
			break;
		}
		getFragmentManager().beginTransaction()
		.replace(R.id.main_content, fragment).commit();
		
	}
}

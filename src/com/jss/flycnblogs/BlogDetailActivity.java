package com.jss.flycnblogs;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.common.util.CacheManager;

import com.jss.flycnblogs.core.AppConfig;
import com.jss.flycnblogs.entity.Blog;
import com.jss.flycnblogs.service.BlogService;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BlogDetailActivity extends MainBaseActivity {
	
	private int id;
	private String blogInfo;
	private String blogTitle;
	private WebView webview;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_blog_detail);
		this.id=getIntent().getIntExtra("blogId", 0);
		this.blogTitle=getIntent().getStringExtra("blogTitle");
		this.blogInfo=getIntent().getStringExtra("blogInfo");
		this.progressBar=(ProgressBar)findViewById(R.id.progressbar_loading);
		this.webview = (WebView)findViewById(R.id.webview_content);
		// ImageView imageView=	(ImageView)findViewById(R.id.imageview_back);
		 TextView textView =(TextView)findViewById(R.id.textview_back);
		 TextView textViewTitle =(TextView)findViewById(R.id.textview_title);
		// imageView.setVisibility(View.GONE);
		 textView.setVisibility(View.GONE);
		 textViewTitle.setText("博客正文");
		DataTask dataTask = new DataTask(id);
//		getActionBar().setDisplayHomeAsUpEnabled(true);
//		getActionBar().setDisplayShowHomeEnabled(false);
//		getActionBar().setHomeButtonEnabled(true);
		//getActionBar().setHomeAsUpIndicator();
		dataTask.execute();
		
		
		
	}

	@Override
	public View onCreateView(View parent, String name, Context context,
			AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(parent, name, context, attrs);
	}
	
	private class DataTask extends AsyncTask<Void, Void, String> {
		private int blogId;
		public  DataTask(int blogId) {
			// TODO Auto-generated constructor stub
			this.blogId=blogId;
		}

		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String result="";
			try {
		//	Thread.sleep(2000);
			result=BlogService.getBlogContent(blogId, BlogDetailActivity.this);
			} 
			catch (Exception e) {
			}
			return result;
		}
		
		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);

		}

		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			String htmlContent = "";
			try {
				InputStream in = getAssets().open("NewsDetail.html");
				byte[] temp = readInputStream(in);
				htmlContent = new String(temp);
			} catch (Exception e) {
				Log.e("error", e.toString());
			}

		

			htmlContent = htmlContent.replace("#title#", blogTitle)
					.replace("#time#", blogInfo)
					.replace("#content#", result);
			webview.loadDataWithBaseURL(AppConfig.LOCAL_PATH, htmlContent, "text/html",
					AppConfig.ENCODE_TYPE, null);
			progressBar.setVisibility(View.GONE);
			
		}
		
	}
	
	/**
	 * 读取输入流
	 */
	private byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}

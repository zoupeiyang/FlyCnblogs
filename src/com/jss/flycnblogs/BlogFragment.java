package com.jss.flycnblogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.jss.flycnblogs.entity.Blog;

import android.R.integer;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BlogFragment extends Fragment {
	
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, String>> listDatas;
	private LinearLayout viewFooter;// footer view
	TextView tvFooterMore;// 底部更多显示
	ProgressBar list_footer_progress;// 底部进度条
	private View rootView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 viewFooter=(LinearLayout)inflater.inflate(R.layout.listview_footer, null,false);
		 rootView =inflater.inflate(R.layout.fragment_blog, container,false);
		 mPullRefreshListView= (PullToRefreshListView)rootView.findViewById(R.id.pull_refresh_list);
		 mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

				// Do work to refresh the list here.
				new DataTask(-1).execute();
			}
		});
	
		// Add an end-of-list listener
			mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

				@Override
				public void onLastItemVisible() {
				//	Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();

					//new GetDataTask().execute();
					//实现底部加载更多的逻辑
					new DataTask(0).execute();

				}
			});

			 actualListView = mPullRefreshListView.getRefreshableView();

			// Need to use the Actual ListView when registering for Context Menu
			registerForContextMenu(actualListView);

		
			String[] from = new String[]{"title","summary","diggs","comments","views"};
			int[] to = new int[]{R.id.textview_title,R.id.textview_summary,R.id.textview_diggs,R.id.textview_comments,R.id.textview_views};
			listDatas=getData(getBlogs(5, 1));
			simpleAdapter = new SimpleAdapter(this.getActivity(), listDatas, R.layout.blog_list_item, from, to);

			/**
			 * Add Sound Event Listener
			 */
			SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this.getActivity());
			soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
			soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
			soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
			mPullRefreshListView.setOnPullEventListener(soundListener);

			// You can also just use setListAdapter(mAdapter) or
			// mPullRefreshListView.setAdapter(mAdapter)
			//mPullRefreshListView.setAdapter(simpleAdapter);
			actualListView.setAdapter(simpleAdapter);
 		    actualListView.addFooterView(viewFooter);
		
		return rootView;
	}

	private List<Map<String, String>> getData(List<Blog> blogs) {
		List<Map<String, String>> datasList=new ArrayList<Map<String,String>>();
		for(int i=1;i<=blogs.size();i++)
		{
			Map<String, String> listItemValueMap = new HashMap<String, String>();
			Blog blog = blogs.get(i-1);
			listItemValueMap.put("id", Integer.toString(blog.getId()) );
			listItemValueMap.put("title", blog.getTitle() );
			listItemValueMap.put("summary", blog.getSummary() );
			listItemValueMap.put("url", blog.getUrl() );
			listItemValueMap.put("authorName", blog.getAuthorName());
			listItemValueMap.put("comments", Integer.toString(blog.getComments()));
			listItemValueMap.put("views", Integer.toString(blog.getViews()));
			listItemValueMap.put("published", blog.getPublished().toLocaleString());
			listItemValueMap.put("diggs", Integer.toString(blog.getDiggs()));
			datasList.add(listItemValueMap);
		}
	
		return datasList;
		
		
	}
	
	private List<Blog> getBlogs(int count,int pageIndex)
	{
		List<Blog> blogs = new ArrayList<Blog>();
		String title;
		if(pageIndex==-1)
		{
			title="new item";
			count=1;
		}
		else if(pageIndex==0) {
			title ="last item";
			count=1;
			
		}
		else {
			title="scala快速学习笔记（一）：变量函数，操作符，基本类型";
		}
		for(int i=1;i<=count;i++)
		{
			
			Blog blog = getBlog(i,title,"为什么要记录一下？因为今天我要设置一个字符加粗，然后就用font-weight:200,没有任何效果。现在看来很可笑，400才相当于normal，200怎么"+i);
			blogs.add(blog);
		}
		return blogs;
		
	}
	private Blog getBlog(int id,String title,String summary)
	{
		Blog blog = new Blog();
		blog.setId(id);
		blog.setTitle(title);
		blog.setSummary(summary);
		blog.setUrl("http://www.baidu.com");
		blog.setAuthorName("test");
		blog.setComments(23);
		blog.setViews(34);
		blog.setDiggs(20);
		blog.setPublished(new Date());
		return blog;
	}
	
	private class DataTask extends AsyncTask<Void, Void, List<Blog>> {
		
		private int pageIndex;
		public  DataTask(int pageIndex) {
			// TODO Auto-generated constructor stub
			this.pageIndex=pageIndex;
		}

		@Override
		protected List<Blog> doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return getBlogs(5,pageIndex);
		}
		
		@Override
		protected void onPreExecute() {
	

			//底部加载更多处理
			if (pageIndex==0) {
				TextView tvFooterMore = (TextView) getActivity().findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_refreshing_label);
				ProgressBar list_footer_progress = (ProgressBar) getActivity().findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected void onPostExecute(List<Blog> result) {
			List<Map<String, String>> datas=getData(result);
			if(pageIndex==-1)
			listDatas.addAll(0,datas);
			else {
				listDatas.addAll(datas);
			}
			simpleAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			//底部加载更多处理
			if (pageIndex==0) {
				TextView tvFooterMore = (TextView) getActivity().findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_more_label);
				ProgressBar list_footer_progress = (ProgressBar) getActivity().findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.GONE);
			}
			super.onPostExecute(result);
		}
		
	}

}

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RecommendBlogFragment extends Fragment {
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private LinearLayout viewFooter;// footer view
    TextView tvFooterMore;// 底部更多显示
	ProgressBar list_footer_progress;// 底部进度条
    View rootView;
	private List<Blog> blogList;
	private BlogListAdapter blogListAdapter;
	private int currentPageIndex;//要加载数据的页码
	private ProgressBar progressBar;
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
			blogList = new ArrayList<Blog>();
			blogListAdapter = new BlogListAdapter(this.getActivity(), blogList);
			// You can also just use setListAdapter(mAdapter) or
			// mPullRefreshListView.setAdapter(mAdapter)
			//mPullRefreshListView.setAdapter(simpleAdapter);
			actualListView.setAdapter(blogListAdapter);
 		   // actualListView.addFooterView(viewFooter);
 		    new DataTask(PullToRefreshFlag.FIRST).execute();
 		   return rootView;
		    
	}

	/**
	 * 初始化控件
	 */
	private void initControls(LayoutInflater inflater,ViewGroup container)
	{
		 currentPageIndex=1;
		 viewFooter=(LinearLayout)inflater.inflate(R.layout.listview_footer, null,false);
		 rootView =inflater.inflate(R.layout.fragment_blog, container,false);
		 progressBar=(ProgressBar)rootView.findViewById(R.id.progressbar_loading);
		 mPullRefreshListView= (PullToRefreshListView)rootView.findViewById(R.id.pull_refresh_list);
		 mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(),
							DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

					// Update the LastUpdatedLabel
					refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

					// Do work to refresh the list here.
					new DataTask(PullToRefreshFlag.REFRESH).execute();
				}
			});
		
			// Add an end-of-list listener
		 mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
					//	Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();

						//new GetDataTask().execute();
						//实现底部加载更多的逻辑
						currentPageIndex=currentPageIndex+1;//页码加1
						new DataTask(PullToRefreshFlag.LOADMORE).execute();

					}
				});
	
			//点击行事件处理方法
		 mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					try {
					intent.setClass(getActivity(), BlogDetailActivity.class);
					Blog blog = blogList.get(position-1);
					Bundle bundle = new Bundle();
					bundle.putInt("blogId", blog.getId());
					bundle.putString("blogTitle", blog.getTitle());
					bundle.putString("blogInfo", blog.getUserName()+"发布于"+blog.getPublished().toLocaleString());
					intent.putExtras(bundle);
					startActivity(intent);

				} catch (Exception ex) {
					ex.printStackTrace();
				}}});
	     actualListView = mPullRefreshListView.getRefreshableView();
			// Need to use the Actual ListView when registering for Context Menu
		 registerForContextMenu(actualListView);
			/**
			 * Add Sound Event Listener
			 */
			SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(this.getActivity());
			soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
			soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
			soundListener.addSoundEvent(State.REFRESHING, R.raw.refreshing_sound);
			mPullRefreshListView.setOnPullEventListener(soundListener);
	}


	
	private class DataTask extends AsyncTask<Void, Void, List<Blog>> {
		
		private PullToRefreshFlag flag;
		public  DataTask(PullToRefreshFlag flag) {
			// TODO Auto-generated constructor stub
			this.flag=flag;
		}

		@Override
		protected List<Blog> doInBackground(Void... params) {
			// Simulates a background job.
			List<Blog> blogList=new ArrayList<Blog>();
			try {
				switch (flag) {
				case REFRESH:
					blogList=BlogService.getRecommendBlogList(1, getActivity());
					break;
				case LOADMORE:
					blogList=BlogService.getRecommendBlogList(currentPageIndex, getActivity());
				case FIRST:
					blogList=BlogService.getRecommendBlogList(1, getActivity());
				default:
					blogList=BlogService.getRecommendBlogList(currentPageIndex, getActivity());
					break;
				}
				//Thread.sleep(4000);
			} 
			catch (Exception e) {
			}
			return blogList;
		}
		
		@Override
		protected void onPreExecute() {
	

			if(flag==PullToRefreshFlag.FIRST)
			{
				progressBar.setVisibility(View.VISIBLE);
			}
			//底部加载更多处理
			if (flag==PullToRefreshFlag.LOADMORE) {
				TextView tvFooterMore = (TextView) getActivity().findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_refreshing_label);
				ProgressBar list_footer_progress = (ProgressBar) getActivity().findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected void onPostExecute(List<Blog> result) {
			switch (flag) {
			case REFRESH://表示刷新
			//	blogList.addAll(0,result);
				blogListAdapter.addAll(0, result);
				break;
			case LOADMORE://表示加载更多
				blogListAdapter.addAll(result);
			case FIRST:
				blogListAdapter.addAll(result);
			default:
				blogListAdapter.addAll(result);
				break;
			}
			blogListAdapter.notifyDataSetChanged();
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();
			if(flag==PullToRefreshFlag.FIRST)
			{
				progressBar.setVisibility(View.GONE);
				actualListView.addFooterView(viewFooter);
			}
			//底部加载更多处理
			if (flag==PullToRefreshFlag.LOADMORE) {
				TextView tvFooterMore = (TextView) getActivity().findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_more_label);
				ProgressBar list_footer_progress = (ProgressBar) getActivity().findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.GONE);
			}
			super.onPostExecute(result);
		}
		
	}
	

}

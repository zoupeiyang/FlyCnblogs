package com.jss.flycnblogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.util.CacheManager;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.jss.flycnblogs.adapter.BlogListAdapter;
import com.jss.flycnblogs.entity.Blog;
import com.jss.flycnblogs.service.BlogService;

import android.R.integer;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BlogFragment extends Fragment {
	
	private PullToRefreshListView mPullRefreshListView;
	private ListView actualListView;
	private LinearLayout viewFooter;// footer view
	TextView tvFooterMore;// �ײ�������ʾ
	ProgressBar list_footer_progress;// �ײ�������
	private View rootView;
	private List<Blog> blogList;
	private BlogListAdapter blogListAdapter;
	private int currentPageIndex;//Ҫ�������ݵ�ҳ��
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
				new DataTask(BlogFlag.REFRESH).execute();
			}
		});
	
		// Add an end-of-list listener
			mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

				@Override
				public void onLastItemVisible() {
				//	Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();

					//new GetDataTask().execute();
					//ʵ�ֵײ����ظ�����߼�
					currentPageIndex=currentPageIndex+1;//ҳ���1
			 	    
					new DataTask(BlogFlag.LOADMORE).execute();

				}
			});
			
			
			//������¼�������
			mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					try {
					intent.setClass(getActivity(), BlogDetailActivity.class);
					Blog blog = blogList.get(position);
					Bundle bundle = new Bundle();
					bundle.putInt("blogId", blog.getId());
					bundle.putString("blogTitle", blog.getTitle());
					bundle.putString("blogInfo", blog.getUserName()+"������"+blog.getPublished().toLocaleString());
					intent.putExtras(bundle);
					startActivity(intent);

				} catch (Exception ex) {
					ex.printStackTrace();
				}
					
					
				}
				
			});
			 actualListView = mPullRefreshListView.getRefreshableView();

			// Need to use the Actual ListView when registering for Context Menu
			registerForContextMenu(actualListView);

		

			blogList = new ArrayList<Blog>();
			blogListAdapter = new BlogListAdapter(this.getActivity(), blogList, actualListView);

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
			actualListView.setAdapter(blogListAdapter);
 		   // actualListView.addFooterView(viewFooter);
 		    new DataTask(BlogFlag.FIRST).execute();
		
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
			title="scala����ѧϰ�ʼǣ�һ������������������������������";
		}
		for(int i=1;i<=count;i++)
		{
			
			Blog blog = getBlog(i,title,"ΪʲôҪ��¼һ�£���Ϊ������Ҫ����һ���ַ��Ӵ֣�Ȼ�����font-weight:200,û���κ�Ч�������ڿ����ܿ�Ц��400���൱��normal��200��ô"+i);
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
		
		private BlogFlag blogFlag;
		public  DataTask(BlogFlag blogFlag) {
			// TODO Auto-generated constructor stub
			this.blogFlag=blogFlag;
		}

		@Override
		protected List<Blog> doInBackground(Void... params) {
			// Simulates a background job.
			List<Blog> blogList=new ArrayList<Blog>();
			try {
				switch (blogFlag) {
				case REFRESH:
					blogList=BlogService.getBlogList(1, getActivity());
					break;
				case LOADMORE:
					blogList=BlogService.getBlogList(currentPageIndex, getActivity());
				case FIRST:
					blogList=BlogService.getBlogList(1, getActivity());
				default:
					blogList=BlogService.getBlogList(currentPageIndex, getActivity());
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
	

			if(blogFlag==BlogFlag.FIRST)
			{
				progressBar.setVisibility(View.VISIBLE);
			}
			//�ײ����ظ��ദ��
			if (blogFlag==BlogFlag.LOADMORE) {
				TextView tvFooterMore = (TextView) getActivity().findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_refreshing_label);
				ProgressBar list_footer_progress = (ProgressBar) getActivity().findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected void onPostExecute(List<Blog> result) {
			switch (blogFlag) {
			case REFRESH://��ʾˢ��
			//	blogList.addAll(0,result);
				blogListAdapter.addAll(0, result);
				break;
			case LOADMORE://��ʾ���ظ���
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
			if(blogFlag==BlogFlag.FIRST)
			{
				progressBar.setVisibility(View.GONE);
				actualListView.addFooterView(viewFooter);
			}
			//�ײ����ظ��ദ��
			if (blogFlag==BlogFlag.LOADMORE) {
				TextView tvFooterMore = (TextView) getActivity().findViewById(R.id.tvFooterMore);
				tvFooterMore.setText(R.string.pull_to_refresh_more_label);
				ProgressBar list_footer_progress = (ProgressBar) getActivity().findViewById(R.id.list_footer_progress);
				list_footer_progress.setVisibility(View.GONE);
			}
			super.onPostExecute(result);
		}
		
	}
	
	private enum BlogFlag{
		REFRESH,FIRST,LOADMORE
	}

}

package com.jss.flycnblogs.adapter;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.trinea.android.common.util.ImageUtils;
import cn.trinea.android.common.util.TimeUtils;

import com.jss.flycnblogs.R;
import com.jss.flycnblogs.core.AppConfig;
import com.jss.flycnblogs.entity.Blog;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BlogListAdapter extends BaseAdapter {

	private List<Blog> list;
	private LayoutInflater mInflater;
	private Context currentContext;
	private Map<Integer, Integer> blogMap;
	public BlogListAdapter(Context context,List<Blog> list)
	{
		this.list=list;
		this.mInflater=(LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.currentContext=context;
		blogMap = new HashMap<Integer, Integer>();
		
	}
	
	
	public class ViewHolder {
		TextView text_id;
		TextView text_title;
		TextView text_summary;
		ImageView imageIcon;
		TextView text_diggs;
		TextView text_views;
		TextView text_comments;
		TextView text_published;
		
		
	}
	
	public boolean addAll(int location, Collection<? extends Blog> collection)
	{
		for (Blog blog : collection) {
			if(!blogMap.containsKey(blog.getId()))
			{
				blogMap.put(blog.getId(), blog.getId());
				list.add(location,blog);
				
			}
		}
		return true;
	}
	
	public boolean addAll(Collection<? extends Blog> collection)
	{
		for (Blog blog : collection) {
			if(!blogMap.containsKey(blog.getId()))
			{
				blogMap.put(blog.getId(), blog.getId());
				list.add(blog);
				
			}
		}
		return true;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		Blog blog = list.get(position);
		if(convertView!=null&&convertView.getId()==R.id.pull_refresh_list)
		{
			viewHolder=(ViewHolder)convertView.getTag();
		}
		else {
			viewHolder = new ViewHolder();
			convertView=mInflater.inflate(R.layout.blog_list_item, null);
			viewHolder.text_title=(TextView) convertView.findViewById(R.id.textview_title);
			viewHolder.text_id=(TextView) convertView.findViewById(R.id.textview_id);
			viewHolder.text_summary=(TextView) convertView.findViewById(R.id.textview_summary);
			viewHolder.text_comments=(TextView) convertView.findViewById(R.id.textview_comments);
			viewHolder.text_diggs=(TextView) convertView.findViewById(R.id.textview_diggs);
			viewHolder.text_views=(TextView) convertView.findViewById(R.id.textview_views);
			viewHolder.text_published=(TextView) convertView.findViewById(R.id.textview_published);
		    viewHolder.imageIcon=(ImageView) convertView.findViewById(R.id.imageview_user_avatar);
			
		}
		
		viewHolder.text_comments.setText(String.valueOf(blog.getComments()));
		viewHolder.text_diggs.setText(String.valueOf(blog.getDiggs()));
		viewHolder.text_published.setText(TimeUtils.getCurrentTimeInString(TimeUtils.DATE_FORMAT_DATE));
		viewHolder.text_summary.setText(blog.getSummary());
		viewHolder.text_title.setText(blog.getTitle());
		viewHolder.text_id.setText(String.valueOf(blog.getId()));
		viewHolder.text_views.setText(String.valueOf(blog.getViews()));
		AppConfig.IMAGE_CACHE.get(blog.getAuthorAvatar(), viewHolder.imageIcon);
		//viewHolder.imageIcon.setImageDrawable(ImageUtils.getDrawableFromUrl("http://pic.cnblogs.com/face/79603/20150318151528.png", 3000));
		convertView.setTag(viewHolder);
		return convertView;
	}

}

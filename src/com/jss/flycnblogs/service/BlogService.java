package com.jss.flycnblogs.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import android.util.Log;
import cn.trinea.android.common.util.CacheManager;
import cn.trinea.android.common.util.HttpUtils;
import cn.trinea.android.common.util.JSONUtils;

import com.jss.flycnblogs.BlogDetailActivity;
import com.jss.flycnblogs.core.AppConfig;
import com.jss.flycnblogs.entity.Blog;
import com.jss.flycnblogs.parser.BlogListXmlParser;
import com.jss.flycnblogs.parser.BlogXmlParser;

public class BlogService {
	
	/**
	 * 根据分页页码返回Blog对象集合
	 * @param pageIndex
	 * @return
	 */
	public static List<Blog> getBlogList(int pageIndex,Context context)
	{
		int pageSize = AppConfig.BLOG_PAGE_SIZE;
		String url = AppConfig.URL_GET_BLOG_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex)).replace("{pageSize}",
				String.valueOf(pageSize));// 数据地址
	//	String dataString = CacheManager.getHttpCache(context).httpGetString(url);
		String dataString = HttpUtils.httpGetString(url);
//		Map<String, String> paramsMap=new HashMap<String, String>();
//		paramsMap.put("mobileNumber", "15989076971");
//		paramsMap.put("password", "123456");
//		String httpUrl="http://192.168.1.105/?m=Home&c=Member&a=login&enterpriseUid=654321";
//		
//		String dataString = HttpUtils.httpPostString(httpUrl,paramsMap);
//		List<String> statusString=JSONUtils.getStringList(dataString, "data", null);
//		for (String string : statusString) {
//			
//			Log.i("tet","dd:"+string);
//			
//		}
//		String msgString=JSONUtils.getString(dataString, "msg", "");



		ArrayList<Blog> list = ParseString(dataString);
		return list;
	}
	
	/**
	 * 根据分页页码返回48小时阅读排行Blog对象集合
	 * @param pageIndex
	 * @return
	 */
	public static List<Blog> getHotBlogList(int pageIndex,Context context)
	{
		
		int pageSize = AppConfig.NUM_48HOURS_TOP_VIEW;
		int itemCount=pageIndex*pageSize;
		String url = AppConfig.URL_48HOURS_TOP_VIEW_LIST.replace("{size}",
				String.valueOf(itemCount));
		String dataString = CacheManager.getHttpCache(context).httpGetString(url);
		ArrayList<Blog> list = ParseString(dataString);
		return list;
	}
	
	/**
	 * 根据分页页码返回10天推荐播客对象集合
	 * @param pageIndex
	 * @return
	 */
	public static List<Blog> getRecommendBlogList(int pageIndex,Context context)
	{
		
		int pageSize = AppConfig.NUM_TENDAYS_TOP_DIGG;
		int itemCount=pageIndex*pageSize;
		String url = AppConfig.URL_TENDAYS_TOP_DIGG_LIST.replace("{size}",
				String.valueOf(itemCount));
		String dataString = CacheManager.getHttpCache(context).httpGetString(url);
		ArrayList<Blog> list = ParseString(dataString);
		return list;
	}
	
	/**
	 * 将字符串转换为Blog集合
	 * 
	 * @return
	 */
	private static ArrayList<Blog> ParseString(String dataString) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		ArrayList<Blog> listBlog = new ArrayList<Blog>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			BlogListXmlParser handler = new BlogListXmlParser(listBlog);
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));
			listBlog = handler.GetBlogList();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listBlog;
	}

	/**
	 * 根据博客Id返回博客内容
	 * @param blogId
	 */
	public static String getBlogContent(int blogId,Context context)
	{
		String url = AppConfig.URL_GET_BLOG_DETAIL.replace("{0}",
				String.valueOf(blogId));// 网址
		String result = CacheManager.getHttpCache(context).httpGetString(url);
		return ParseBlogString(result);

		
	}
	
	/**
	 * 将字符串转换为Blog集合
	 * 
	 * @return
	 */
	private static String ParseBlogString(String dataString) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		String blogContent = "";
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			BlogXmlParser handler = new BlogXmlParser(blogContent);
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));
			blogContent = handler.GetBlogContent();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return blogContent;
	}

}

package com.jss.flycnblogs.service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.content.Context;
import cn.trinea.android.common.util.CacheManager;

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
		String dataString = CacheManager.getHttpCache(context).httpGetString(url);
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

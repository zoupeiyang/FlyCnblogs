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
import com.jss.flycnblogs.adapter.BlogXmlParser;
import com.jss.flycnblogs.core.AppConfig;
import com.jss.flycnblogs.entity.Blog;
import com.jss.flycnblogs.parser.BlogListXmlParser;

public class BlogService {
	
	/**
	 * ���ݷ�ҳҳ�뷵��Blog���󼯺�
	 * @param pageIndex
	 * @return
	 */
	public static List<Blog> getBlogList(int pageIndex,Context context)
	{
		int pageSize = AppConfig.BLOG_PAGE_SIZE;
		String url = AppConfig.URL_GET_BLOG_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex)).replace("{pageSize}",
				String.valueOf(pageSize));// ���ݵ�ַ
		String dataString = CacheManager.getHttpCache(context).httpGetString(url);
		ArrayList<Blog> list = ParseString(dataString);
		return list;
	}
	
	
	/**
	 * ���ַ���ת��ΪBlog����
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
	 * ���ݲ���Id���ز�������
	 * @param blogId
	 */
	public static String getBlogContent(int blogId,Context context)
	{
		String url = AppConfig.URL_GET_BLOG_DETAIL.replace("{0}",
				String.valueOf(blogId));// ��ַ
		String result = CacheManager.getHttpCache(context).httpGetString(url);
		return ParseBlogString(result);

		
	}
	
	/**
	 * ���ַ���ת��ΪBlog����
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

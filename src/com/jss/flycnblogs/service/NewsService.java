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
import android.util.Log;
import cn.trinea.android.common.util.CacheManager;

import com.jss.flycnblogs.BlogDetailActivity;
import com.jss.flycnblogs.core.AppConfig;
import com.jss.flycnblogs.entity.Blog;
import com.jss.flycnblogs.entity.New;
import com.jss.flycnblogs.parser.BlogListXmlParser;
import com.jss.flycnblogs.parser.BlogXmlParser;
import com.jss.flycnblogs.parser.NewListXmlParser;
import com.jss.flycnblogs.parser.NewXmlParser;

public class NewsService {
	
	/**
	 * 根据分页页码返回Blog对象集合
	 * @param pageIndex
	 * @return
	 */
	public static List<New> getNewsList(int pageIndex,Context context)
	{
		int pageSize = AppConfig.NEWS_PAGE_SIZE;
		String url = AppConfig.URL_GET_NEWS_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex)).replace("{pageSize}",
				String.valueOf(pageSize));// 数据地址
		String dataString = CacheManager.getHttpCache(context).httpGetString(url);
		Log.i("http1", dataString);
		List<New> list = ParseString(dataString);
		return list;
	}
	
	/**
	 * 根据分页页码返回推荐新闻对象集合
	 * @param pageIndex
	 * @return
	 */
	public static List<New> getRecommendNewsList(int pageIndex,Context context)
	{
		int pageSize = AppConfig.COMMENT_PAGE_SIZE;
		String url = AppConfig.URL_RECOMMEND_NEWS_LIST.replace("{pageIndex}",
				String.valueOf(pageIndex)).replace("{pageSize}",
				String.valueOf(pageSize));// 数据地址
		String dataString = CacheManager.getHttpCache(context).httpGetString(url);
		Log.i("http1", dataString);
		List<New> list = ParseString(dataString);
		return list;
	}
	
	/**
	 * 将字符串转换为Blog集合
	 * 
	 * @return
	 */
	private static List<New> ParseString(String dataString) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<New> newsList = new ArrayList<New>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			NewListXmlParser handler = new NewListXmlParser(newsList);
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));
			newsList = handler.getNewList();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newsList;
	}

	/**
	 * 根据博客Id返回博客内容
	 * @param blogId
	 */
	public static String getNewsContent(int newsId,Context context)
	{
		String url = AppConfig.URL_GET_NEWS_DETAIL.replace("{0}",
				String.valueOf(newsId));// 网址
		String result = CacheManager.getHttpCache(context).httpGetString(url);
		return ParseNewsString(result);

		
	}
	
	/**
	 * 将字符串转换为Blog集合
	 * 
	 * @return
	 */
	private static String ParseNewsString(String dataString) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		String newsContent = "";
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			NewXmlParser handler = new NewXmlParser();
			xmlReader.setContentHandler(handler);

			xmlReader.parse(new InputSource(new StringReader(dataString)));
			newsContent = handler.getContent();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newsContent;
	}

}

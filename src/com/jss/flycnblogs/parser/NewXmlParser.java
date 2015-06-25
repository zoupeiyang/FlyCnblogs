package com.jss.flycnblogs.parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class NewXmlParser extends DefaultHandler {
	final String ENTRY_CONTENT_TAG = "content";
	final String ENTRY_IMAGEURL_TAG="ImageUrl";
	final String ENTRY_PREVNEWS_TAG="PrevNews";
	final String ENTRY_NEXTNEWS_TAG="NextNews";
	private String content;// 单个对象
	private String imageUrl;
	private int prevNews;
	private int nextNews;
	private boolean isStartParse;// 开始解析
	private StringBuilder currentDataBuilder;// 当前取到的值
	
	public  NewXmlParser() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public String getContent()
	{
		return content;
	}
	
	public String getImageUrl()
	{
		return imageUrl;
	}
	
	public int getPrevNews() {
		return prevNews;
		
	}
	
	public int getNextNews() {
		return nextNews;
	}
	
	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		currentDataBuilder=new StringBuilder();
	}
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if(localName.equalsIgnoreCase(ENTRY_CONTENT_TAG))
		{
			isStartParse=true;
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(isStartParse)
		{
			String chars = currentDataBuilder.toString();
			if(localName.equalsIgnoreCase(ENTRY_CONTENT_TAG))
			{
				content=chars;
			}
			else if(localName.equalsIgnoreCase(ENTRY_IMAGEURL_TAG)) {
				imageUrl=chars;
				
			}
			else if(localName.equalsIgnoreCase(ENTRY_PREVNEWS_TAG)) {
				prevNews = Integer.parseInt(chars);
			}
			else if(localName.equalsIgnoreCase(ENTRY_NEXTNEWS_TAG)) {
				nextNews=Integer.parseInt(chars);
				
			}
		}
		currentDataBuilder.setLength(0);
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		currentDataBuilder.append(ch, start, length);
	}
	

}

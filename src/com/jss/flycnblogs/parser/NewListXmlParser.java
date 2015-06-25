package com.jss.flycnblogs.parser;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import cn.trinea.android.common.util.StringUtils;
import cn.trinea.android.common.util.TimeUtils;

import com.jss.flycnblogs.entity.New;

public class NewListXmlParser extends DefaultHandler {
	final String ENTRY_TAG="entry";
	final String ENTRY_ID_TAG="id";
	final String ENTRY_TITLE_TAG="title";
	final String ENTRY_SUMMARY_TAG="summary";
	final String ENTRY_PUBLISHED_TAG="published";
	final String ENTRY_UPDATED_TAG="updated";
	final String ENTRY_LINK_TAG="link";
	final String ENTRY_DIGGS_TAG="diggs";
	final String ENTRY_VIEWS_TAG="views";
	final String ENTRY_COMMENTS_TAG="comments";
	final String ENTRY_TOPIC_TAG="topic";
	final String ENTRY_TOPICICON_TAG="topicIcon";
	final String ENTRY_SOURCENAME_TAG="sourceName";
	final String ENTRY_URL_TAG = "link";//  µº Õ¯÷∑±Í«©
	final String ENTRY_URL_ATTRIBUTE_TAG = "href";// Õ¯÷∑ Ù–‘±Í«©
	private List<New> newList;
	private New entry;
	private boolean isStartParse;
	private StringBuilder currentDataBuilder;
	
	public NewListXmlParser()
	{
		super();
	}
	
	public NewListXmlParser(List<New> newList)
	{
		this.newList=newList;
	}
	
	public List<New> getNewList()
	{
		return newList;
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		newList = new ArrayList<New>();
		currentDataBuilder=new StringBuilder();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if(localName.equalsIgnoreCase(ENTRY_TAG))
		{
			entry=new New();
			isStartParse=true;
			
		}
		if(isStartParse&&localName.equalsIgnoreCase(ENTRY_URL_TAG))
		{
			entry.setLink(attributes.getValue(ENTRY_URL_ATTRIBUTE_TAG));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		if(isStartParse)
		{
			String chars=currentDataBuilder.toString();
			if(localName.equalsIgnoreCase(ENTRY_TITLE_TAG))
			{
				chars=StringUtils.HtmlToText(chars);
				entry.setTitle(chars);
			}
				
			else if (localName.equalsIgnoreCase(ENTRY_SUMMARY_TAG)) {
				entry.setSummary(chars);
			}
			else if (localName.equalsIgnoreCase(ENTRY_SUMMARY_TAG)) {
				entry.setSummary(chars);
			}
			else if (localName.equalsIgnoreCase(ENTRY_ID_TAG)) {
				entry.setId(Integer.parseInt(chars));
			}
			else if (localName.equalsIgnoreCase(ENTRY_PUBLISHED_TAG)) {
				entry.setPublished(TimeUtils.ParseUTCDate(chars));
			}
			else if (localName.equalsIgnoreCase(ENTRY_UPDATED_TAG)) {
				entry.setUpdated(TimeUtils.ParseUTCDate(chars));
			}
			else if (localName.equalsIgnoreCase(ENTRY_DIGGS_TAG)) {
				entry.setDiggs(Integer.parseInt(chars));
			}
			else if (localName.equalsIgnoreCase(ENTRY_VIEWS_TAG)) {
				entry.setViews(Integer.parseInt(chars));
			}
			else if (localName.equalsIgnoreCase(ENTRY_COMMENTS_TAG)) {
				entry.setComments(Integer.parseInt(chars));
			}
			else if (localName.equalsIgnoreCase(ENTRY_TOPIC_TAG)) {
				entry.setTopic(chars);
			}
			else if (localName.equalsIgnoreCase(ENTRY_TOPICICON_TAG)) {
				entry.setTopicIcon(chars);
			}
			else if (localName.equalsIgnoreCase(ENTRY_SOURCENAME_TAG)) {
				entry.setSourceName(chars);
			}
		    else if (localName.equalsIgnoreCase(ENTRY_TAG)) {// Ωÿ÷π
			newList.add(entry);
			isStartParse = false;
		}
		}
		currentDataBuilder.setLength(0);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		currentDataBuilder.append(ch,start,length);
	}
	
	

}

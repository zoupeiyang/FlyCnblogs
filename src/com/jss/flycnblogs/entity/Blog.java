package com.jss.flycnblogs.entity;

import java.util.Date;
public class Blog {

	private int id;
	private String title;
	private String url;
	private String summary;
	private Date published;
	private Date updated;
	private String authorName;
	private String authorUrl;
	private String authorAvatar;
	private String userName;
	private int diggs;
	private int views;
	private int comments;
	private String content;
	private Boolean isReaded;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getPublished() {
		return published;
	}
	public void setPublished(Date published) {
		this.published = published;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public String getAuthorAvatar() {
		return authorAvatar;
	}
	public void setAuthorAvatar(String authorAvatar) {
		this.authorAvatar = authorAvatar;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getDiggs() {
		return diggs;
	}
	public void setDiggs(int diggs) {
		this.diggs = diggs;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public int getComments() {
		return comments;
	}
	public void setComments(int comments) {
		this.comments = comments;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Boolean getIsReaded() {
		return isReaded;
	}
	public void setIsReaded(Boolean isReaded) {
		this.isReaded = isReaded;
	}
	
}

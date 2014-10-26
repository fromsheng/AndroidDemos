package com.artion.androiddemos.domain;

import java.io.Serializable;

public class SessionItem implements Serializable {
	private static final long serialVersionUID = -3554762058133279668L;
	
	public static final int SESSION_TYPE_TEXT = 0;
	public static final int SESSION_TYPE_PHOTO = 1;
	public static final int SESSION_TYPE_VOICE = 2;
	public static final int SESSION_TYPE_NEWS = 3;
	public static final int SESSION_TYPE_PLAN = 4;
	public static final int SESSION_TYPE_SHARE = 5;
	public static final int SESSION_TYPE_STATUS = 6;
	
	
	
	public int sessionType = SESSION_TYPE_TEXT;
	
	public String userName;
	public String sessionTime;
	public String sessionContent;
	
	public String shareTitle;

}

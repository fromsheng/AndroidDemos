package com.artion.androiddemos.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.artion.androiddemos.ImageViewSrcDemo;
import com.artion.androiddemos.MainActivity;
import com.artion.androiddemos.utils.ActivityIntentTools;
import com.artion.androiddemos.utils.ToastUtils;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class HighLightTextView extends TextView {

	public String groupId ;
	public String groupName ;
	

	// 匹配@+人名
	public static final Pattern NAME_Pattern = Pattern.compile(
			"@[^\\s\\$\\^\\[\\]\\?:!#%&=;@'\"<>\f$，；：“”。！、？]+", Pattern.CASE_INSENSITIVE);

	// 匹配话题#...#
	public static final Pattern TOPIC_PATTERN = Pattern
			.compile("#([^#\\\\]+?)#");

	// 匹配网址
	public final static Pattern URL_PATTERN = Pattern
			.compile("http://([\\w-]+\\.)+[\\w-]+(/[\\w-\\./?%&=]*)?");
	
	// 匹配邮箱
	//public final static Pattern EMAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");

	public HighLightTextView(Context context) {
		this(context, null);
	}

	public HighLightTextView(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.textViewStyle);
	}

	public HighLightTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFocusable(false);
	}

	public void setText(String text,String group_id, String group_name) {
		groupId = group_id;
		groupName = group_name;
		setHighLightText(this, text);
	}
	
//	public void setText(SpannableString text,String group_id, String group_name) {
//		groupId = group_id;
//		groupName = group_name;
//		setHighLightText(this, text);
//	}

	/**
	 * 实现人名/话题/连接等高亮，以及显示表情
	 * 
	 * @param textView
	 * @param text
	 */
	public void setHighLightText(TextView textView, String text) {
		SpannableStringBuilder style = new SpannableStringBuilder(text);
		Matcher nameMatcher = NAME_Pattern.matcher(text);
		while (nameMatcher.find()) {
//			style.setSpan(new ForegroundColorSpan(Color.BLUE),
//					nameMatcher.start(), nameMatcher.end(),
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			style.setSpan(new NoLineClickSpan(nameMatcher.group(), LinkType.LINK_NAME),
					nameMatcher.start(), nameMatcher.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		Matcher topicMatcher = TOPIC_PATTERN.matcher(text);
		while (topicMatcher.find()) {
//			style.setSpan(new ForegroundColorSpan(Color.BLUE),
//					topicMatcher.start(), topicMatcher.end(),
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			
			style.setSpan(new NoLineClickSpan(topicMatcher.group(), LinkType.LINK_TOPIC),
					topicMatcher.start(), topicMatcher.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}

		Matcher urlMatcher = URL_PATTERN.matcher(text);
		while (urlMatcher.find()) {
//			style.setSpan(new URLSpan(urlMatcher.group()), urlMatcher.start(),
//					urlMatcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			style.setSpan(new NoLineClickSpan(urlMatcher.group(), LinkType.LINK_URL),
					urlMatcher.start(), urlMatcher.end(),
					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		
		textView.setText(style);

		textView.setMovementMethod(LinkMovementMethod.getInstance());
		
//		if (textView instanceof TextView) {
//			textView.setAutoLinkMask(0);
//			Linkify.addLinks(textView, NAME_Pattern,HighLightTextView.NAME_SCHEMA, null, new TransformFilter() {
//				@Override
//				public String transformUrl(Matcher match, String url) {
//					return url.substring(1, url.length());
//				}
//			});
//			Linkify.addLinks(textView, TOPIC_PATTERN,HighLightTextView.TREND_SCHEMA, null, new TransformFilter() {
//				@Override
//				public String transformUrl(Matcher match, String url) {
//					return url.substring(1, url.length() - 1)
//							+ Properties.isGroupId + groupId
//							+ Properties.isGroupId + groupName;
//				}
//			});
//			Linkify.addLinks(textView, URL_PATTERN,null, null, new TransformFilter() {
//				@Override
//				public String transformUrl(Matcher match, String url) {
//					return match.group(1);
//				}
//			});
//			textView.setMovementMethod(null);
//		}
	}
//	/**
//	 * 实现人名/话题/连接等高亮，以及显示表情
//	 * 
//	 * @param textView
//	 * @param text
//	 */
//	public void setHighLightText(TextView textView, SpannableString text) {
//		SpannableStringBuilder style = new SpannableStringBuilder(text);
//		Matcher nameMatcher = NAME_Pattern.matcher(text);
//		while (nameMatcher.find()) {
//			style.setSpan(new ForegroundColorSpan(Color.BLUE),
//					nameMatcher.start(), nameMatcher.end(),
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//		Matcher topicMatcher = TOPIC_PATTERN.matcher(text);
//		while (topicMatcher.find()) {
//			style.setSpan(new ForegroundColorSpan(Color.BLUE),
//					topicMatcher.start(), topicMatcher.end(),
//					Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//
//		Matcher urlMatcher = URL_PATTERN.matcher(text);
//		while (urlMatcher.find()) {
//			style.setSpan(new URLSpan(urlMatcher.group()), urlMatcher.start(),
//					urlMatcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//		}
//		
//		textView.setText(style);
//
//		if (textView instanceof TextView) {
//			textView.setAutoLinkMask(0);
//			Linkify.addLinks(textView, NAME_Pattern,HighLightTextView.NAME_SCHEMA, null, new TransformFilter() {
//				@Override
//				public String transformUrl(Matcher match, String url) {
//					return url.substring(1, url.length());
//				}
//			});
//			Linkify.addLinks(textView, TOPIC_PATTERN,HighLightTextView.TREND_SCHEMA, null, new TransformFilter() {
//				@Override
//				public String transformUrl(Matcher match, String url) {
//					return url.substring(1, url.length() - 1)
//							+ Properties.isGroupId + groupId
//							+ Properties.isGroupId + groupName;
//				}
//			});
//			Linkify.addLinks(textView, URL_PATTERN,null, null, new TransformFilter() {
//				@Override
//				public String transformUrl(Matcher match, String url) {
//					return match.group(1);
//				}
//			});
//			textView.setMovementMethod(null);
//		}
//	}

	/**
	 * 给文字加了超链接以后，listview的onItemClickListener就会失效，自己重写onTouchEvent方法可以解决这问题	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		TextView widget = (TextView) this;
		Object text = widget.getText();
		if (text instanceof Spanned) {
			Spannable buffer;
			try {
				buffer = (Spannable) text;
			} catch (ClassCastException cce) {
				cce.printStackTrace();
				return false;
			}
			int action = event.getAction();
			if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
				int x = (int) event.getX();
				int y = (int) event.getY();
				x -= widget.getTotalPaddingLeft();
				y -= widget.getTotalPaddingTop();
				x += widget.getScrollX();
				y += widget.getScrollY();
				Layout layout = widget.getLayout();
				int line = layout.getLineForVertical(y);
				int off = layout.getOffsetForHorizontal(line, x);
				ClickableSpan[] link = buffer.getSpans(off, off,ClickableSpan.class);
				if (link.length != 0) {
					if (action == MotionEvent.ACTION_UP) {
						link[0].onClick(widget);
					} else if (action == MotionEvent.ACTION_DOWN) {
						Selection.setSelection(buffer,
								buffer.getSpanStart(link[0]),
								buffer.getSpanEnd(link[0]));
					}
					return true;
				}
			}
		}
//		return super.onTouchEvent(event);
		return false;//返回false直接不处理交由其他控件接收
	}
	@Override    
    public boolean getDefaultEditable() {    
        return false;    
    }
	@Override    
    protected void onCreateContextMenu(ContextMenu menu) {    
        //不做任何处理，为了阻止长按的时候弹出上下文菜单    
    } 
	
	public enum LinkType {
		LINK_URL, LINK_NAME, LINK_TOPIC
	}
	
	//无下划线超链接，使用textColorLink、textColorHighlight分别修改超链接前景色和按下时的颜色
	private class NoLineClickSpan extends ClickableSpan {
	    String text;
	    LinkType mLinkType;
	    
	    public NoLineClickSpan(String text, LinkType linkType) {
	        super();
	        this.text = text;
	        this.mLinkType = linkType;
	    }

//	    public NoLineClickSpan(String text) {
//	        super();
//	        this.text = text;
//	    }

	    @Override
	    public void updateDrawState(TextPaint ds) {
	        ds.setColor(ds.linkColor);
	        ds.setUnderlineText(false);//去掉下划线</span>
	    }

	    @Override
	    public void onClick(View widget) { 
//	        processHyperLinkClick(text);//点击超链接时调用</span>
//	    	ToastUtils.showMessage(getContext(), "你点击了我 + "+ text);
	    	switch (mLinkType) {
			case LINK_URL:
				ToastUtils.showMessage(getContext(), "跳转到浏览器 -> "+ text);
				break;
			case LINK_NAME:
				ToastUtils.showMessage(getContext(), "打开人名 -> "+ text);
				break;
			case LINK_TOPIC:
				ToastUtils.showMessage(getContext(), "打开话题 -> "+ text);
				
				ActivityIntentTools.gotoActivityNotFinish(getContext(), ImageViewSrcDemo.class);
				break;
			}
	    }
	}
}

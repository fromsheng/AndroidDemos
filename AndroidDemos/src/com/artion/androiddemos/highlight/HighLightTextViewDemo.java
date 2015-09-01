package com.artion.androiddemos.highlight;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.utils.VerifyTools;
import com.artion.androiddemos.view.HighLightTextView;

public class HighLightTextViewDemo extends BaseActivity {

	TextView tv, tv2, tv4;
	HighLightTextView tv3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_highlight);
		
		initLayout();
		
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		
		tv = (TextView) findViewById(R.id.tv);
		tv2 = (TextView) findViewById(R.id.tv2);
		tv4 = (TextView) findViewById(R.id.tv4);
		SpannableString s = new SpannableString("我们已发送验证码短信到你的手机上");
	    
        Pattern p = Pattern.compile("验证码短信");
        
        
         Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.highlighttext)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        
        tv.setText(s);
        
		
//        StringBuffer sb = new StringBuffer();
//        sb.append("<h1><font color='#ff0000'>Html标签方式:</font></h1>");
//        sb.append("<h6><b><i><font color='#00ff00'><a href='http://www.baidu.com/'>");
//        sb.append("百度一下");
//        sb.append("</a></font></i></b></h6>");
//        tv.setText(Html.fromHtml(sb.toString()));
//        tv.setMovementMethod(LinkMovementMethod.getInstance());// 这句很重要,使超链接<a href>起作用
        
		
//        String s = "我们已发送<font color='#00ff00'>验证码短信</font>到你的手机上";
//        String cen = "<font color='" + getResources().getColor(R.color.highlighttext) + "'>" + getString(R.string.tips_center) + "</font>";
//        String ss = getString(R.string.tips, cen);
//        tv.setText(Html.fromHtml(ss));
        
        
        long t1 = System.currentTimeMillis();
        SpannableString ss = VerifyTools.getHighLightText(
        		"我们已发送验证码短信到你的手机上",
        		"验证码短信",
        		getResources().getColor(R.color.highlighttext));
        tv.setText(ss);
        System.out.println("t1 == " + (System.currentTimeMillis() - t1));
        
        
        
        long t2 = System.currentTimeMillis();
        String htmlStr = VerifyTools.getHtmlHighLightStr(
        		"我们已发送验证码短信到你的手机上",
        		"验证码短信",
        		getResources().getColor(R.color.highlighttext));
        String sf = "我们已发送" + htmlStr + "到你的手机上";
        tv2.setText(Html.fromHtml(sf));
        System.out.println("t2 == " + (System.currentTimeMillis() - t2));
        
        
        tv3 = (HighLightTextView) findViewById(R.id.tv3);
        
        tv3.setText("#2015一生有你#我想对像说@蔡锦升 @康小林 这是一个百度地址：http://www.baidu.com", null, null);
        tv4.setText(VerifyTools.toSBC("#2015一生有你#我想对像说@蔡锦升 @康小林 这是一个百度地址：http://www.baidu.com"));
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
	}
	
}

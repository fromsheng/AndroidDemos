package com.artion.androiddemos.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

/**
 * 字符串相关常用操作类
 * @author jinsheng_cai
 * @since 2014-12-01
 */
public class StringUtils {
	
	public static String trim(String str) {
		if(str == null) {
			return null;
		}
		return str.trim();
	}
	
	public static boolean isEmpty(CharSequence str) {
		return TextUtils.isEmpty(str);
	}
	
	public static boolean isEmptyWithNull(String str) {
		return TextUtils.isEmpty(str) && "null".equalsIgnoreCase(str);
	}
	
	/**
	 * 邮箱验证
	 * @param email 需要验证的邮箱
	 * */
	public static boolean isEmail(String email) {
		return Pattern
				.compile(
						"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")
						.matcher(email).matches();
	}
	
	/**
	 * 验证密码格式, 只支持英文和数字.
	 * @param pwd
	 * @return
	 */
	public static boolean verifyPasswordFormat(String pwd) {
		return Pattern.compile("[a-zA-Z0-9]*").matcher(pwd).matches();
	}
	
	/**
	 * 验证手机号是否正确;
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		// Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		// 增加18号段跟147 145
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	/**
	 * 验证是否是数字串
	 * @param src
	 * @return
	 */
	public static boolean verifyNumber(String src) {
		if(src == null) {
			return false;
		}
		return Pattern.compile("[0-9]+").matcher(src).matches();
	}
	
	/**
	 * 取自然数的str,若不是数字刚返回0
	 * @param str
	 * @return
	 */
	public static String getNaturalNumberString(String str) {
		if(isEmptyWithNull(str) || !verifyNumber(str)) {
			return "0";
		}
		
		int count = Integer.parseInt(str.trim());
		
		if(count < 0) {
			count = 0;
		}
		
		return String.valueOf(count);
	}
	
	/**
	 * 取得需要高亮的字符串
	 * @param source
	 * @param pattern
	 * @param highLightColor
	 * @return
	 */
	public static SpannableString getHighLightText(String source, String pattern, int highLightColor) {
		return getHighLightText(source, pattern, highLightColor, false);
	}
	
	/**
	 * 取得需要高亮的字符串
	 * @param source
	 * @param pattern eg:"\\[(.*?)\\]\\n"
	 * @param highLightColor getResouse().getColor(...)
	 * @param isFirstReturn 是否找到第一个就返回
	 * @return
	 */
	public static SpannableString getHighLightText(String source, String pattern, int highLightColor, boolean isFirstReturn) {
		if(source == null) {
			return null;
		}
		SpannableString s = new SpannableString(source);
	    
        Pattern p = Pattern.compile(pattern);
        
        
         Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(highLightColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            if(isFirstReturn) {
            	break;
            }
        }
        
        return s;
	}
}

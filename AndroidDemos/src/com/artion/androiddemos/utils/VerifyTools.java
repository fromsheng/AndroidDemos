package com.artion.androiddemos.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * @author jinsheng_cai
 * @since 2013-10-25
 * 
 */
public class VerifyTools {
	/**
	 * 针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
	 * @param input
	 * @return 返回全部为全角字符的字符串
	 */
	public static String toSBC(String input) { 
        char c[] = input.toCharArray(); 
        for (int i = 0; i < c.length; i++) { 
            if (c[i] == ' ') { 
                c[i] = '\u3000'; 
            } else if (c[i] < '\177') { 
                c[i] = (char) (c[i] + 65248); 
            } 
        } 
        return new String(c); 
    } 
	
	public static String getHtmlHighLightStr(String source, String pattern, int highLightColor) {
      String cen = "<font color='" + highLightColor + "'>" + pattern + "</font>";
      return cen;
	}
	
	/**
	 * 取得需要高亮的字符串
	 * @param source
	 * @param pattern
	 * @param highLightColor
	 * @return
	 */
	public static SpannableString getHighLightText(String source, String pattern, int highLightColor) {
		SpannableString s = new SpannableString(source);
	    
        Pattern p = Pattern.compile(pattern);
        
        
         Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(highLightColor), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        
        return s;
	}
	
	/**
	 * 判断jsonArray字符串是否为空
	 * @param json
	 * @return
	 */
	public static boolean isJsonArrayStrNull(String json) {
		if(isEmpty(json)) {
			return true;
		}
		
		try {
			JSONArray ja = new JSONArray(json);
			if(ja != null && ja.length() > 0) {
				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * 判断个数是否为空
	 * @param count
	 * @return
	 */
	public static boolean isCountNull(String count) {
		if(isEmpty(count)){
			return true;
		}
		
		if("0".equals(count.trim())) {
			return true;
		}
		
		return false;
	}
	
	public static String getCountZero(String count) {
		if(isCountNull(count)) {
			return "0";
		}
		
		return count;
	}
	
	/**
	 * 判断字符串是否为空.
	 * 
	 * @param src
	 * @return
	 */
	public static boolean isEmpty(String src) {
		return src == null || "".equals(src.trim()) || "null".equalsIgnoreCase(src);
	}

	/**
	 * 邮箱验证
	 * 
	 * @param email
	 *            需要验证的邮箱
	 * */
	public static boolean isEmail(String email) {

		return Pattern
				.compile(
						"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")
				.matcher(email).matches();
	}

	/**
	 * 验证密码格式, 只支持英文和数字.
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean verifyPasswordFormat(String pwd) {
		return Pattern.compile("[a-zA-Z0-9]*").matcher(pwd).matches();
	}

	/**
	 * 验证手机号是否正确;
	 * 
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
		return Pattern.compile("[0-9]+").matcher(src).matches();
	}

	/**
	 * 返回文件大小，带单位B,KB,MB等
	 * @param fileSize
	 * @return
	 */
	public static String getStringBySize(long fileSize){
		String result="";
		if((fileSize/1024)>=1){
			fileSize/=1024;
			if(fileSize/1024>=1){
				//解决当最后一位为0时，未显示出来的问题
				double value= fileSize*1.0/1024;
				DecimalFormat df = new DecimalFormat("#.00");
				result= df.format(value)+" MB";
//				result=new BigDecimal(fileSize*1.0/1024).setScale(2,BigDecimal.ROUND_HALF_UP).floatValue()+" MB";
			}else{
				result=fileSize+" KB";
			}
		}else{
			result=fileSize+" B";
		}
		return result;
	}
}

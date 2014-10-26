package com.artion.androiddemos.adapter;

import java.io.Serializable;

/**
 * gridview菜单item对应的资源类
 * @author jinsheng_cai
 * @since 2014-05-29
 */
public class GridMenuItem implements Serializable {
	private static final long serialVersionUID = -2892452855011365543L;
	
	/**icon默认图片id*/
	public int iconNormalRid;
	/**icon选中图片id*/
	public int iconDownRid;
	/**item对应字符串*/
	public int itemStrRid;
	/**item是否在选中状态*/
	public boolean itemSelected;
	/**item的未读数*/
	public int unReadCount;
}

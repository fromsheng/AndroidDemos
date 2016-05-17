package com.artion.androiddemos.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.artion.androiddemos.R;
import com.artion.androiddemos.baseview.impl.SessionItemCommonImageView;
import com.artion.androiddemos.baseview.impl.SessionItemCommonShareView;
import com.artion.androiddemos.baseview.impl.SessionItemCommonTextView;
import com.artion.androiddemos.domain.SessionItem;
import com.artion.androiddemos.utils.DebugTool;

public class SessionAdapter extends BaseAdapter {
	
	private List<SessionItem> mSessionList;
	private Context mContext;
	private LayoutInflater mInflate;
	
	public SessionAdapter(Context context, List<SessionItem> list) {
		
		this.mContext = context;
		this.mInflate = LayoutInflater.from(context);
		this.mSessionList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSessionList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSessionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		SessionItem item = mSessionList.get(position);
		
		convertView = initConvertView(convertView, item.sessionType, "Artion".equals(item.userName));
		switch (item.sessionType) {
		case SessionItem.SESSION_TYPE_TEXT:
			SessionItemCommonTextView.getInstance(convertView, R.layout.biz_session_item_common_text).bindView(item);
			convertView.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					switch (event.getAction()) {
					case MotionEvent.ACTION_CANCEL:
						DebugTool.info("MotionEvent", "ACTION_CANCEL");
						v.getParent().requestDisallowInterceptTouchEvent(false);
						break;
					case MotionEvent.ACTION_UP:
						DebugTool.info("MotionEvent", "ACTION_UP");
						v.getParent().requestDisallowInterceptTouchEvent(false);
						break;
					case MotionEvent.ACTION_DOWN:
						DebugTool.info("MotionEvent", "ACTION_DOWN");
						v.getParent().requestDisallowInterceptTouchEvent(true);
						break;
					case MotionEvent.ACTION_MOVE:
						DebugTool.info("MotionEvent", "ACTION_MOVE");
						break;
					case MotionEvent.ACTION_MASK:
						DebugTool.info("MotionEvent", "ACTION_MASK");
						break;

					default:
						break;
					}
					
					return true;
				}
			});
			break;
		case SessionItem.SESSION_TYPE_SHARE:
			SessionItemCommonShareView.getInstance(convertView, R.layout.biz_session_item_common_share).bindView(item);
			break;
		case SessionItem.SESSION_TYPE_NEWS:
			SessionItemCommonImageView.getInstance(convertView, R.layout.biz_session_item_common_image).bindView(item);
		}
			
		
		/*switch (item.sessionType) {
		case SessionItem.SESSION_TYPE_TEXT:
//			if("Artion".equals(item.userName)) {
//				if(convertView == null || convertView.getId() != R.id.session_item_common_right
//						|| SessionItem.SESSION_TYPE_TEXT != (Integer)convertView.getTag()) {
//					convertView = mInflate.inflate(R.layout.biz_session_item_common_right, null);
//				}
//			} else {
//				if(convertView == null || convertView.getId() != R.id.session_item_common_left
//						|| SessionItem.SESSION_TYPE_TEXT != (Integer)convertView.getTag()) {
//					convertView = mInflate.inflate(R.layout.biz_session_item_common_left, null);
//				}
//			}
			
			if("Artion".equals(item.userName)) {
				if(ifViewNeedResetByType(convertView, SessionItem.SESSION_TYPE_TEXT, false)) {
					convertView = mInflate.inflate(R.layout.biz_session_item_common_right, null);
				}
			} else {
				if(ifViewNeedResetByType(convertView, SessionItem.SESSION_TYPE_TEXT, true)) {
					convertView = mInflate.inflate(R.layout.biz_session_item_common_left, null);
				}
			}
			
			
			convertView.setTag(SessionItem.SESSION_TYPE_TEXT);
			SessionItemCommonTextView.getInstance(convertView, R.layout.biz_session_item_common_text).bindView(item);
			
			break;
		case SessionItem.SESSION_TYPE_SHARE:
//			if("Artion".equals(item.userName)) {
//				if(convertView == null || convertView.getId() != R.id.session_item_common_right
//						|| SessionItem.SESSION_TYPE_SHARE != (Integer)convertView.getTag()) {
//					convertView = mInflate.inflate(R.layout.biz_session_item_common_right, null);
//				}
//			} else {
//				if(convertView == null || convertView.getId() != R.id.session_item_common_left
//						|| SessionItem.SESSION_TYPE_SHARE != (Integer)convertView.getTag()) {
//					convertView = mInflate.inflate(R.layout.biz_session_item_common_left, null);
//				}
//			}
			
			if("Artion".equals(item.userName)) {
				if(ifViewNeedResetByType(convertView, SessionItem.SESSION_TYPE_SHARE, false)) {
					convertView = mInflate.inflate(R.layout.biz_session_item_common_right, null);
				}
			} else {
				if(ifViewNeedResetByType(convertView, SessionItem.SESSION_TYPE_SHARE, true)) {
					convertView = mInflate.inflate(R.layout.biz_session_item_common_left, null);
				}
			}
			
			convertView.setTag(SessionItem.SESSION_TYPE_SHARE);
			SessionItemCommonShareView.getInstance(convertView, R.layout.biz_session_item_common_share).bindView(item);
			
			break;
		default:
			break;
		}*/
		
		return convertView;
	}
	
	private boolean ifViewNeedResetByType(View convertView, int sessionType, boolean isLeft) {
		int layoutRootId = isLeft ? R.id.session_item_common_left : R.id.session_item_common_right;
		if(convertView == null || convertView.getId() != layoutRootId
				|| sessionType != (Integer)convertView.getTag()) {
			return true;
		}
		return false;
	}
	
	private View initConvertView(View convertView, int sessionType, boolean isLeft) {
		
		if(isLeft) {
			if(ifViewNeedResetByType(convertView, sessionType, isLeft)) {
				convertView = mInflate.inflate(R.layout.biz_session_item_common_left, null);
			}
		} else {
			if(ifViewNeedResetByType(convertView, sessionType, isLeft)) {
				convertView = mInflate.inflate(R.layout.biz_session_item_common_right, null);
			}
		}
		
		convertView.setTag(sessionType);
		
		return convertView;
	}

}

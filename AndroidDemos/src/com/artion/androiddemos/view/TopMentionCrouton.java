package com.artion.androiddemos.view;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.crouton.Configuration;
import com.artion.androiddemos.crouton.Crouton;

/**
 * 头部@人提醒 {@link Crouton}
 * @author jinsheng_cai
 * @since 2014-07-30
 */
public class TopMentionCrouton {
	private Activity mAct;
	private View contentView;
	private TextView tvTips;
	private Crouton crouton;
	private Configuration croutonConfiguration;
	
	private String msg;
	private TopMentionListener mListener = null;
	
	
	public TopMentionCrouton(Activity activity) {
		this.mAct = activity;
		croutonConfiguration = new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build();

		contentView = LayoutInflater.from(activity).inflate(
				R.layout.biz_session_mention, null);
		View item = contentView.findViewById(R.id.session_mention_item);
		tvTips = (TextView) contentView.findViewById(R.id.session_mention_tv_title);
		ImageView icon = (ImageView) contentView.findViewById(R.id.session_mention_icon);
		//			setHeight(100);

		item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideMention();

				if(mListener != null) {
					mListener.onItemClick(msg);
				}
			}
		});

		icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideMention();

				if(mListener != null) {
					mListener.onCloseClick();
				}
			}
		});

	}
	
	public void hideMention() {
		if(crouton != null) {
			Crouton.hide(crouton);
		}
	}
	
	public static void hideMention(TopMentionCrouton topMentionCrouton) {
		if(topMentionCrouton != null) {
			topMentionCrouton.hideMention();
		}
	}
	
	public void showMention(final String msg, TopMentionListener listener) {
		this.msg = msg;
		this.mListener = listener;
		
		
		contentView = LayoutInflater.from(mAct).inflate(
				R.layout.biz_session_mention, null);
		View item = contentView.findViewById(R.id.session_mention_item);
		tvTips = (TextView) contentView.findViewById(R.id.session_mention_tv_title);
		ImageView icon = (ImageView) contentView.findViewById(R.id.session_mention_icon);
		//			setHeight(100);

		item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideMention();

				if(mListener != null) {
					mListener.onItemClick(msg);
				}
			}
		});

		icon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideMention();

				if(mListener != null) {
					mListener.onCloseClick();
				}
			}
		});
		tvTips.setText(msg);
		crouton = Crouton.make(mAct, contentView);

		crouton.setConfiguration(croutonConfiguration);
		crouton.show();
	}
	
}

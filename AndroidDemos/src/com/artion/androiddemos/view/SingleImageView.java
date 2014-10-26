package com.artion.androiddemos.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.artion.androiddemos.R;

public class SingleImageView extends RelativeLayout {
	
	private ImageView ivPic;
	private ImageView iconGif, iconVideo;
	private TextView tvVideoSize;
	

	public SingleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.fag_timeline_item_single_image, this);
		
		initLayout();
	}
	
	private void initLayout() {
		ivPic = (ImageView) findViewById(R.id.timeline_item_single_image_pic);
		iconGif = (ImageView) findViewById(R.id.timeline_item_single_image_gif);
		iconVideo = (ImageView) findViewById(R.id.timeline_item_single_image_video);
		tvVideoSize = (TextView) findViewById(R.id.timeline_item_single_image_filesize);
		
		showPicOnly();
	}
	
	public void loadImage(String url) {
//		ImageLoaderUtils.displayImage(url, ivPic, R.drawable.image_default_pic);
	}
	
	public void setLayoutParam(int width, int height) {
//		setLayoutParams(new LinearLayout.LayoutParams(width, height));
		
		ivPic.setLayoutParams(new LayoutParams(width, height));
	}
	
	public ImageView getImageView() {
		return ivPic;
	}

	public ImageView getIconGif() {
		return iconGif;
	}
	
	public ImageView getIconVideo() {
		return iconVideo;
	}
	
	public TextView getVideoSizeTextView() {
		return tvVideoSize;
	}
	
	public void showPicOnly() {
		iconGif.setVisibility(View.GONE);

		iconVideo.setVisibility(View.GONE);
		tvVideoSize.setVisibility(View.GONE);
	}
	
	public void showGif() {
		iconGif.setVisibility(View.VISIBLE);
		
		iconVideo.setVisibility(View.GONE);
		tvVideoSize.setVisibility(View.GONE);
	}
	
	public void showVideo(String videoSize) {
		iconGif.setVisibility(View.GONE);
		
		iconVideo.setVisibility(View.VISIBLE);
		tvVideoSize.setVisibility(View.VISIBLE);
		
		tvVideoSize.setText(videoSize);
	}
}

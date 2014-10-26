package com.artion.androiddemos.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.artion.androiddemos.R;

public class MultiImageView extends RelativeLayout {
	
	private final int MAX_COUNT = 9;
	
	private final int DEFAULT_MULTI_WIDTH = 150;
	private final int DEFAULT_MULTI_HEIGHT = 150;
	
	private final int DEFAULT_SINGLE_WIDTH = 300;
	private final int DEFAULT_SINGLE_HEIGHT = 300;
	
	private final int SINGLE_IMAGEVIEW_IDS[] = {
			R.id.timeline_item_multi_image_0,
			R.id.timeline_item_multi_image_1,
			R.id.timeline_item_multi_image_2,
			R.id.timeline_item_multi_image_3,
			R.id.timeline_item_multi_image_4,
			R.id.timeline_item_multi_image_5,
			R.id.timeline_item_multi_image_6,
			R.id.timeline_item_multi_image_7,
			R.id.timeline_item_multi_image_8
	};
	
	private Context mContext;
	private SingleImageView[] singleImageViews;
	
	private ItemClickLister mItemClickLister = null;

	public MultiImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.mContext = context;
		LayoutInflater.from(context).inflate(R.layout.multi_imageview, this);
		initLayout();
	}
	
	private void initLayout() {
		singleImageViews = new SingleImageView[MAX_COUNT];
		for (int i = 0; i < MAX_COUNT; i++) {
			singleImageViews[i] = (SingleImageView) findViewById(SINGLE_IMAGEVIEW_IDS[i]);
			final int position = i;
			singleImageViews[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(mItemClickLister != null) {
						mItemClickLister.onItemClickLister(position);
					}
				}
			});
		}
		
		setMultiImageSize(MAX_COUNT);
	}
	
	public void setOnItemClickListener(ItemClickLister mItemClickLister) {
		this.mItemClickLister = mItemClickLister;
	}
	
	public interface ItemClickLister {
		public void onItemClickLister(int position);
	}
	
	public void setMultiImageSize(int count) {
		setMultiImageSize(count, DEFAULT_MULTI_WIDTH, DEFAULT_MULTI_HEIGHT);
	}
	
	public void setMultiImageSize(int count,int width, int height) {
		for(int i = 0; i < MAX_COUNT; i++) {
			if(i < count) {
				singleImageViews[i].setVisibility(View.VISIBLE);
				singleImageViews[i].setLayoutParam(width, height);
			}else{
				singleImageViews[i].setVisibility(View.GONE);
			}
		}
	}
	
	public void setSingleImageSize(int width, int height) {
		setMultiImageSize(1, width, height);
	}
	
	public void setSingleImage() {
		setMultiImageSize(1, DEFAULT_SINGLE_WIDTH, DEFAULT_SINGLE_HEIGHT);
	}

	public void loadMultiImages(ArrayList<String> pictures, boolean hasVideo, String videoSize) {
		loadMultiImages(pictures, hasVideo, videoSize, DEFAULT_MULTI_WIDTH, DEFAULT_MULTI_HEIGHT);
		
	}
	
	public void loadMultiImages(ArrayList<String> pictures, boolean hasVideo, String videoSize,
			int width, int height) {
		int count = pictures.size();
		setMultiImageSize(count, width, height);
		
		String item = null;
		for (int i = 0; i < count; i++) {
			item = pictures.get(i);
			singleImageViews[i].loadImage(item);
			
			if(hasVideo) {
				singleImageViews[i].showVideo(videoSize);
			}else{
//				if(item.contentType.equals(KdweiboConstantTypes.IMAGE_GIF)) {
//					singleImageViews[i].showGif();
//				}else{
//					singleImageViews[i].showPicOnly();
//				}
				
				singleImageViews[i].showPicOnly();
			}
		}
		
	}
}

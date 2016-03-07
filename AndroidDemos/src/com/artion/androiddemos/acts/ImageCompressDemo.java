package com.artion.androiddemos.acts;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.R.id;
import com.artion.androiddemos.R.layout;
import com.artion.androiddemos.common.ImageUtils;
import com.artion.androiddemos.utils.ActivityIntentTools;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;

public class ImageCompressDemo extends BaseActivity {
	
	private final int REQ_CODE_TAKE = 1001;
	private final int REQ_CODE_CHOOSE = 1002;
	private final int REQ_CODE_CROP = 1003;
	
	private File tmpFile = null;
	private Uri tmpUri = null;
	
	private ImageView ivPhoto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_image_compress);

		initLayout();
		initListener();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void initLayout() {
		super.initLayout();
		
		ivPhoto = (ImageView) findViewById(R.id.image_compress_photo);
	}

	@Override
	protected void initListener() {
		super.initListener();

		findViewById(R.id.image_compress_btn_choose).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ActivityIntentTools.choosePhoto(mAct, "选照片", REQ_CODE_CHOOSE);
			}
		});

		findViewById(R.id.image_compress_btn_take).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tmpFile = ImageUtils.createFilePhoto();
				tmpUri = Uri.fromFile(tmpFile);
				ActivityIntentTools.takePhoto(mAct, REQ_CODE_TAKE, tmpUri);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(RESULT_OK != resultCode) {
			return;
		}
		
		switch (requestCode) {
		case REQ_CODE_TAKE:
//			rotatePicAndSave(file);
			tmpFile = ImageUtils.createFilePhoto();
			ActivityIntentTools.cropPhoto(mAct, tmpUri, Uri.fromFile(tmpFile), 400, 400, REQ_CODE_CROP);

			break;
		case REQ_CODE_CHOOSE:
			tmpUri = data.getData();
			tmpFile = ImageUtils.createFilePhoto();
			ActivityIntentTools.cropPhoto(mAct, tmpUri, Uri.fromFile(tmpFile), 400, 400, REQ_CODE_CROP);
			break;
			
		case REQ_CODE_CROP:
			ImageLoader.getInstance().displayImage(Scheme.FILE.wrap(String.valueOf(tmpFile.getAbsoluteFile())), ivPhoto);
			break;
		}
		
	}
	
	

}

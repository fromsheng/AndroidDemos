package com.artion.androiddemos.acts;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.frame.FrameActivity;
import com.artion.androiddemos.utils.DebugTool;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShareIntentDemo extends FrameActivity {
	
	private TextView tvTitle, tvUrl;
	private ImageView logo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Get intent, action and MIME type  
		Intent intent = getIntent();
        String action = intent.getAction();  
        String type = intent.getType();  
  
        if (Intent.ACTION_SEND.equals(action) && type != null) {  
            if ("text/plain".equals(type)) {  
                handleSendText(intent); // Handle text being sent  
            } else if (type.startsWith("image/")) {  
                handleSendImage(intent); // Handle single image being sent  
            }  
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {  
            if (type.startsWith("image/")) {  
                handleSendMultipleImages(intent); // Handle multiple images being sent  
            }  
        } else {  
            // Handle other intents, such as being started from the home screen  
        }  
	}

	@Override
	protected void initIntentDatas(Intent intent) {
		super.initIntentDatas(intent);
		
		
	}


	@Override
	public int getLayoutRid() {
		return R.layout.act_share_intent;
	}


	@Override
	public void initLayout() {
		tvTitle = (TextView) findViewById(R.id.share_itent_title);
		tvUrl = (TextView) findViewById(R.id.share_itent_url);
		logo = (ImageView) findViewById(R.id.share_itent_logo);
	}


	@Override
	public void initListener() {
		
	}
	
	void handleSendText(Intent intent) {  
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);  
        String shareSubject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        String file = intent.getStringExtra("file");
        ToastUtils.showMessage(mAct, file);
        DebugTool.info(tag, file);
    	ImageLoader.getInstance().displayImage("file://" + file, logo);
        if (sharedText != null) {  
            // Update UI to reflect text being shared  
        	
        	tvTitle.setText(shareSubject);
        	tvUrl.setText(sharedText);
        }  
    }  
  
    void handleSendImage(Intent intent) {  
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);  
        if (imageUri != null) {  
            // Update UI to reflect image being shared  
        	ToastUtils.showMessage(mAct, imageUri.toString());
        	ImageLoader.getInstance().displayImage(imageUri.toString(), logo);
        }  
    }  
  
    void handleSendMultipleImages(Intent intent) {  
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);  
        if (imageUris != null) {  
            // Update UI to reflect multiple images being shared  
        	for (int i = 0; i < imageUris.size(); i++) {
				DebugTool.info(tag, imageUris.get(i).toString());
			}
        }  
    }  


}

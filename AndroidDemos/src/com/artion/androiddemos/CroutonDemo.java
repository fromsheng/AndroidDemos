package com.artion.androiddemos;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.artion.androiddemos.crouton.Configuration;
import com.artion.androiddemos.crouton.Crouton;
import com.artion.androiddemos.crouton.LifecycleCallback;
import com.artion.androiddemos.crouton.Style;
import com.artion.androiddemos.utils.ToastUtils;
import com.artion.androiddemos.view.TopMentionCrouton;
import com.artion.androiddemos.view.TopMentionListener;

public class CroutonDemo extends BaseActivity {
	private Button btn;
	private int count = 0;
	
	private TopMentionCrouton topCrouton = null;
	
	private void showTopCrouton() {
		if(topCrouton == null) {
			topCrouton = new TopMentionCrouton(mAct);
		}
		topCrouton.hideMention();
		topCrouton.showMention("我晕 ———" + (count ++), new TopMentionListener() {
			
			@Override
			public void onItemClick(String msg) {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(mAct, "onRemoved --> " + msg);
			}
			
			@Override
			public void onCloseClick() {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(mAct, "onCloseClick --> ");
			}
		});
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_button);
		
		initLayout();
		initListener();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if(topCrouton != null) {
			topCrouton.hideMention();
		}
		
		if(crouton != null) {
	    	Crouton.hide(crouton);
	    }
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();
		
		btn = (Button) findViewById(R.id.button1);
	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();
		
		findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTopCrouton();
			}
		});
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showCrouton("嗷...呜..." + (count ++));
			}
		});
	}
	
	private Crouton crouton;
	
	private void showCrouton(String croutonText) {
		int croutonDuration = Configuration.DURATION_INFINITE;
		int bgColor = 0xc0000000;
		Style croutonStyle = new Style.Builder()
				.setBackgroundColorValue(bgColor).setTextShadowDx(0)
				.setTextShadowDy(0).setTextShadowRadius(0)
				.setPaddingInPixels(20).build();

		Configuration croutonConfiguration = new Configuration.Builder()
				.setDuration(croutonDuration).build();

	    if(mAct == null) {
	    	return;
	    }
	    
	    if(crouton != null) {
	    	Crouton.hide(crouton);
	    }
	    
	    crouton = Crouton.makeText(mAct, croutonText, croutonStyle);
	    crouton.setLifecycleCallback(new LifecycleCallback() {
			
			@Override
			public void onRemoved() {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(mAct, "onRemoved");
			}
			
			@Override
			public void onDisplayed() {
				// TODO Auto-generated method stub
				ToastUtils.showMessage(mAct, "onDisplayed");
			}
		});
	    crouton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Crouton.hide(crouton);
			}
		});
	    crouton.setConfiguration(croutonConfiguration).show();
//	    crouton.setOnClickListener(mAct).setConfiguration(croutonConfiguration).show();
	  }

}

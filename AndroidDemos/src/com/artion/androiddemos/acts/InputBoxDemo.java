package com.artion.androiddemos.acts;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.artion.androiddemos.BaseActivity;
import com.artion.androiddemos.R;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.utils.DebugTool;
import com.artion.androiddemos.utils.DeviceTool;

public class InputBoxDemo extends BaseActivity {
	private ListView listView;
	private List<String> actNames;
	private ImageView inputMore;
	private EditText editBox;
	private Animation animIn = null, animOut = null;
	private View llBottom, inputExtra;
	
	private void initActNames() {
		actNames = new ArrayList<String>();
		actNames.add("MainActivity");
		actNames.add("ListDemo");
		actNames.add("ListDownDemo");
		actNames.add("ImageViewSrcDemo");
		actNames.add("GridViewMenuDemo");
		actNames.add("SingleImageViewDemo");
		actNames.add("MultiImageViewDemo");
		actNames.add("OnGestureDemo");
		actNames.add("TextLongDemo");
		actNames.add("TextViewLongDemo");
		actNames.add("TranslateDemo");
		actNames.add("SessionAct");
		actNames.add("PopupWindowDemoActivity");
		actNames.add("SensorTestActivity");
		actNames.add("ViewPopupWindowDemo");
		actNames.add("CroutonDemo");
		actNames.add("TopPopupDemo");
		actNames.add("LoadingDialogDemo");
		actNames.add("MainDemos");
		actNames.add("AnimationDemo");
		actNames.add("StartDoDemo");
		actNames.add("MyDrawViewAct");
		actNames.add("EditTextDemo");
		actNames.add("ImageCompressDemo");
		actNames.add("MoveViewDemo");
		actNames.add("ImagePressedDemo");
		actNames.add("ExtraViewDemo");
		actNames.add("NettyDemo");
		actNames.add("NoticationDemo");
		actNames.add("LuckySettingDemo");
		actNames.add("TabMenuDemo");
		actNames.add("AnimatorDemo");
		actNames.add("SurfaceViewDemo");
		actNames.add("RoundDemo");
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_inputbox);
		
		animIn = AnimationUtils.loadAnimation(mAct, R.anim.down_to_up);
		animOut = AnimationUtils.loadAnimation(mAct, R.anim.up_to_down);
		
		initLayout();
		initListener();
	}

	private void startTranslate(final View view, final int fromY, final int toY) {
		TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
		animation.setDuration(300);
//		animation.setFillAfter(true);//只是将view移动到了目标位置，但是view绑定的点击事件还在原来位置
		animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
				int l = view.getLeft();
				int width = view.getWidth();
				int height = view.getHeight();
				int t = view.getTop() + (toY - fromY);
				view.clearAnimation();
				
				view.layout(l, t, l + width, t + height);
				listView.layout(listView.getLeft(), listView.getTop(), listView.getRight(), listView.getBottom() + (toY - fromY));
//				listView.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
////						listView.setSelection(actNames.size() - 1);
//						listView.scrollTo(0, actNames.size() - 1);
//					}
//				}, 100);
//				listView.scrollBy(0, (actNames.size() - 2) * listView.getChildAt(listView.getFirstVisiblePosition()).getHeight());
//				listView.setSelectionFromTop(0, actNames.size() - 1);
//				listView.smoothScrollToPosition(actNames.size() - 1);
			}
		});
//		view.setAnimation(animation);
		view.startAnimation(animation);
		listView.postDelayed(new Runnable() {
		
		@Override
		public void run() {
//			listView.setSelection(actNames.size() - 1);
			listView.smoothScrollToPosition(actNames.size() - 1);
		}
	}, 400);
	}

	@Override
	protected void initLayout() {
		super.initLayout();
		llBottom = findViewById(R.id.ll_bottom);
		inputExtra = findViewById(R.id.inputextra);
		inputMore = (ImageView) findViewById(R.id.inputmore);
		editBox = (EditText) findViewById(R.id.inputbox);
		
		listView = (ListView) findViewById(R.id.listview);
		initActNames();
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, actNames));
	}

	@Override
	protected void initListener() {
		super.initListener();
		
		findViewById(R.id.inputextra_btn).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ToastUtils.showMessage(mAct, "按钮来了");				
			}
		});
		findViewById(R.id.inputextra_tv).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ToastUtils.showMessage(mAct, "标题来了");				
			}
		});
		
//		editBox.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				if(llBottom.getBottom() > 1920) {
//					startTranslate(llBottom, 0, -inputExtra.getHeight());
//				}
//				return false;
//			}
//		});
		
		editBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(llBottom.getBottom() > 1920) {
					startTranslate(llBottom, 0, -inputExtra.getHeight());
				}
			}
		});
		
//		editBox.setOnFocusChangeListener(new OnFocusChangeListener() {
//			
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				inputExtra.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
//			}
//		});
		
		inputMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				if(inputExtra.getVisibility() == View.VISIBLE) {
//					inputExtra.setVisibility(View.GONE);
//					inputExtra.startAnimation(animOut);
//				} else {
//					inputExtra.setVisibility(View.VISIBLE);
//					inputExtra.startAnimation(animIn);
//				}
				
				if(llBottom.getBottom() <= 1920) {
					startTranslate(llBottom, 0, inputExtra.getHeight());
				} else {
					startTranslate(llBottom, 0, -inputExtra.getHeight());
				}
				DebugTool.info(tag, "llBottom.getBottom() = " + llBottom.getBottom());
				if(DeviceTool.isInputMethodActive(mAct)) {
					DebugTool.info(tag, "需要隐藏键盘");
					DeviceTool.hideInputManager(mAct);
				} else {
					DebugTool.info(tag, "需要打开键盘");
					DeviceTool.showInputManager(mAct, editBox);
				}
			}
		});
	}

}

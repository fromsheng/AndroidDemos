package com.artion.androiddemos;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.Button;
import android.widget.TextView;

import com.artion.androiddemos.utils.DebugTool;

public class TextViewLongDemo extends BaseActivity {

	private TextView tvLong;
	private Button btn, btn2;
	private boolean isChanged = false;

	private boolean isExpanded = true;

	private static final int MAX = 5;// 初始maxLine大小
	private static final int TIME = 20;// 间隔时间
	private int maxLines;
	private TextView textView;
	private boolean hasMesure = false;
	private Thread thread;
	
	private int currentLine = MAX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_textview_long);

		initLayout();
//		initView();
		initListener();
	}

	@Override
	protected void initLayout() {
		// TODO Auto-generated method stub
		super.initLayout();

		tvLong = (TextView) findViewById(R.id.tv_long);
		btn = (Button) findViewById(R.id.btn);
		btn2 = (Button) findViewById(R.id.btn2);

	}

	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		super.initListener();

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (isExpanded) {
//					tvLong.setMaxLines(5);
					tvLong.setFilters( new  InputFilter[]{ new  InputFilter.LengthFilter( 100 )});
				} else {
//					tvLong.setMaxLines(100);
					tvLong.setFilters( new  InputFilter[]{ new  InputFilter.LengthFilter( 1000 )});
				}
				isExpanded = !isExpanded;
				
				
				
//				toggle();
			}
		});
		
		
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isChanged) {
					textView.setText("对于易于本地增加分支，图中Git本地和服务器端结构都很灵活，所有版本都存储在一个目录中，你只需要进行分支的切换即可达到在某个分支工作的效果。 而SVN则完全不同，如果你需要在本地试验一些自己的代码，只能本地维护多个不同的拷贝，每个拷贝对应一个SVN服务器地址。举一个实际的例子，以前我所 在的小组使用SVN作为版本控制工具，当我正在试图增强一个模块，工作做到一半，由于会改变原模块的行为导致代码服务器上许多测试的失败，所以并没有提交 代码。这时候上级对我说，现在有一个很紧急的Bug需要处理， 必须在两个小时内完成。我只好将本地的所有修改diff，并输出成为一个patch文件，然后回滚有关当前任务的所有代码，再开始修改Bug的任务，等到 修改好后，在将patch应用回来。前前后后要完成多个繁琐的步骤，这还不计中间代码发生冲突所要进行的工作量。可是如果使用Git， 我们只需要开一个分支或者转回到主分支上，就可以随时开始Bug修改的任务，完成之后，只要切换到原来的分支就可以优雅的继续以前的任务。只要你愿意，每 一个新的任务都可以开一个分支，完成后，再将它合并到主分支上，轻松而优雅。");
				}else {
					textView.setText("简短不割简短不割简短不割简短不割简短不割简短不割简短不割简短不割");
				}
				isChanged = !isChanged;
				hasMesure = false;
				DebugTool.info(tag, "onClick maxLines == " + maxLines);
//				if(maxLines >= MAX) {
//					btn.setVisibility(View.GONE);
//				}else {
//					btn.setVisibility(View.VISIBLE);
//				}
			}
		});
	}

	private void initView() {

		// 获取ViewTreeObserver
		// View观察者，并注册一个监听事件，这个时间是在View还未绘制的时候执行的，也就是在onDraw之前
		// textView默认是没有maxLine限制的，这样我就可以计算到完全显示的maxLine
		textView = (TextView) findViewById(R.id.tv_long);
		ViewTreeObserver viewTreeObserver = textView.getViewTreeObserver();
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {

			@Override
			public boolean onPreDraw() {
				// 只需要获取一次就可以了
				if (!hasMesure) {
					// 这里获取到完全展示的maxLine
					maxLines = textView.getLineCount();
					// 设置maxLine的默认值，这样用户看到View就是限制了maxLine的TextView
					textView.setMaxLines(MAX);
					hasMesure = true;
					
					DebugTool.info(tag, "maxLines == " + maxLines);
					
					if(maxLines <= MAX) {
						btn.setVisibility(View.GONE);
					}else {
						btn.setVisibility(View.VISIBLE);
					}
				}

				return true;
			}
		});

		// textView.setOnClickListener(this);
	}

	/**
	 * 打开TextView
	 */
	@SuppressLint("HandlerLeak")
	private void toggle() {

		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int lines = msg.what;
				// 这里接受到消息，让后更新TextView设置他的maxLine就行了
				textView.setMaxLines(lines);
				textView.postInvalidate();
			}
		};
		if (thread != null)
			handler.removeCallbacks(thread);

		thread = new Thread() {
			@Override
			public void run() {
//				int count = MAX;
//				while (count++ <= maxLines) {
//					// 每隔20mms发送消息
//					Message message = new Message();
//					message.what = count;
//					handler.sendMessage(message);
//
//					try {
//						Thread.sleep(TIME);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
				
				
				if(currentLine == maxLines) {
					int count1 = currentLine;
					while (count1 -- >= MAX) {
						// 每隔20mms发送消息
						Message message = new Message();
						message.what = count1;
						handler.sendMessage(message);

						try {
							Thread.sleep(TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					currentLine = MAX;
				}else {
					int count2 = currentLine;
					while (count2 ++ <= maxLines) {
						// 每隔20mms发送消息
						Message message = new Message();
						message.what = count2;
						handler.sendMessage(message);

						try {
							Thread.sleep(TIME);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					
					currentLine = maxLines;
				}
				
				
				super.run();
			}
		};
		thread.start();
	}
}

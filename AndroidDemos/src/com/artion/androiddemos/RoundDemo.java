package com.artion.androiddemos;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class RoundDemo extends BaseActivity {
	
	ImageView round = null;
	Animation animation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.act_round);
		animation = AnimationUtils.loadAnimation(this, R.anim.round_scale);
		round = (ImageView) findViewById(R.id.round_img);
		round.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				round.clearAnimation();
				round.startAnimation(animation);
			}
		});
	}


}

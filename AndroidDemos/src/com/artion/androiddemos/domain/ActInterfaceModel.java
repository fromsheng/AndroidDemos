package com.artion.androiddemos.domain;

import com.artion.androiddemos.BaseApplication;
import com.artion.androiddemos.common.ToastUtils;
import com.artion.androiddemos.impl.ActInterface;

public class ActInterfaceModel implements ActInterface {

	@Override
	public void onClick(String text) {
		ToastUtils.showMessage(BaseApplication.getInstance(), text);
	}

}

package com.artion.androiddemos.utils;

import ch.boye.httpclientandroidlib.util.ExceptionUtils;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

/**
 * Loading加载框管理类，已做单例
 * 
 * @author jinsheng_cai
 * @since 2013-10-30
 */
public class LoadingDialog {

	private ProgressDialog progressDialog;

	private static LoadingDialog instance = null;
	
	private LoadingDialog() {
	}

	/**
	 * 取得加载框管理类单例实例
	 * 
	 * @param context
	 * @return
	 */
	public static LoadingDialog getInstance() {
		if (instance == null) {
			instance = new LoadingDialog();
		}

		return instance;
	}

	/**
	 * 显示Loading，默认不可取消
	 * 
	 * @param title
	 */
	public void showLoading(Context context, String title) {

		showLoading(context, title, false, false);

	}
	
	public void showLoading(Context context, String title,
			boolean isCanceledOnTouchOutside, ProgressListener listener) {
		showLoading(context, title, true, isCanceledOnTouchOutside, listener);
	}
	
	private void showLoading(Context context, String title,
			boolean isCancelable, boolean isCanceledOnTouchOutside, final ProgressListener listener) {
		
		initProgressDialog(context, title);

		progressDialog.setCancelable(isCancelable);
		progressDialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside);
		
		if (isCancelable) {
			progressDialog.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					if (listener != null) {
						listener.onCancel(dialog);
					}
				}
			});
		}

		progressDialog.show();
	}

	/**
	 * 显示Loading，可控制是否按返回键取消及是否按框外取消
	 * 
	 * @param title
	 * @param isCancelable
	 *            是否按返回键取消
	 * @param isCanceledOnTouchOutside
	 *            是否按框外取消
	 */
	public void showLoading(Context context, String title,
			boolean isCancelable, boolean isCanceledOnTouchOutside) {

		showLoading(context, title, isCancelable, isCanceledOnTouchOutside, null);

	}

	/**
	 * 显示Loading，默认不可取消
	 * 
	 * @param titleRes
	 */
	public void showLoading(Context context, int titleRes) {

		showLoading(context, context.getString(titleRes));

	}

	/**
	 * 显示Loading，可控制是否按返回键取消及是否按框外取消
	 * 
	 * @param titleRes
	 * @param isCancelable
	 *            是否按返回键取消
	 * @param isCanceledOnTouchOutside
	 *            是否按框外取消
	 */
	public void showLoading(Context context, int titleRes,
			boolean isCancelable, boolean isCanceledOnTouchOutside) {

		showLoading(context, context.getString(titleRes), isCancelable,
				isCanceledOnTouchOutside);

	}

	private void initProgressDialog(Context context, String msg) {

		if (progressDialog != null && progressDialog.isShowing()) {
			dismissLoading();
		}

		progressDialog = new ProgressDialog(context);

		progressDialog.setMessage(msg);
		// progressDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

		progressDialog.setCancelable(false);//默认不可取消
		progressDialog.setCanceledOnTouchOutside(false);
		
	}

	/**
	 * 隐藏Loading框
	 */
	public void dismissLoading() {
		try {
			if (null != progressDialog && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		} catch (Exception e) {
			
		} finally {
			progressDialog = null;
		}
	}
	
	public interface ProgressListener {
		public void onCancel(DialogInterface dialog);
	}
}

package com.artion.androiddemos.task;

import android.content.Context;

import com.luciofm.asynctaskcompat.AsyncTaskCompat;

public class BaseTask extends AsyncTaskCompat<Object, Long, Long>
{

	
	private final BaseTaskPacket<?> mBaseTaskPacket;

	private final Context mContext;

	/**
	 * 任务唯一id,
	 */
	protected int mId;
	
	private int iPriority = BaseTaskManager.GJHTTPTASK_PRIORITY_DEFAULT;
	private AbsException mException;
	private BaseTaskManager mTaskManager;

	public BaseTask(int id, BaseTaskPacket<?> packet,BaseTaskManager manager, Context context)
	{
		mTaskManager = manager;
		mBaseTaskPacket = packet;
		mContext = context;
		mId = id;
	}
	
	
	public BaseTask(int id, BaseTaskPacket<?> packet, Context context)
	{
		this(id, packet, null, context);
	}	
	
	public Context getContext(){
		return mContext;
	}
	
	
	public void startRequest(boolean asynchronous){
		if(asynchronous)
			this.execute();
		else {
			synchronousRequest(mBaseTaskPacket);
		}
	}
	
	
	public int getPriority()
	{
		return iPriority;
	}
	
	public void setPriority(int priority)
	{
		iPriority = priority;
	}
	

	
	private int synchronousRequest(BaseTaskPacket<?> packet){
		BaseTaskPacket<?> targetPacket = packet;
		BaseTaskPacket<?> tempPacket = null;
		do{
			try
			{
				targetPacket.run(mContext);
			} 
			catch (Exception e) {
				mException = targetPacket.getException(NetUtil.printStackTraceString(e), 0);
			} 

			
			tempPacket = targetPacket.getNextBaseTask();
			if(tempPacket!= null ){
				if(mException == null)
					tempPacket.onSuccessPreTask();
				else {
					tempPacket.onFailPreTask(mException);
					if(!tempPacket.getContinueLink()){
						tempPacket = null;
						mException = targetPacket.getException("Cancel LinkPacket Request", 0);
					}
				}
			}
			
			targetPacket = tempPacket;
			
		}while(targetPacket != null);

		return 0;
	}
	/**
	 * 获取当前任务ID
	 * 
	 * @return
	 */
	public int getId()
	{
		return mId;
	}
	
	public Exception getException(){
		return mException;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	protected void onCancelled()
	{
		super.onCancelled();
			BaseTaskPacket basePacket = mBaseTaskPacket;
			if(basePacket.getNextBaseTask()!=null){
				basePacket = basePacket.getTailTask();
			}
			basePacket.onFail(mId, basePacket.getException("task cancel",0));
		if(mTaskManager!=null)
			mTaskManager.onRunningFinish(this, false);
		mBaseTaskPacket.clearLink();
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void onPostExecute(Long result)
	{
		super.onPostExecute(result);
		if(mException == null){
			BaseTaskPacket basePacket = mBaseTaskPacket;
			if(basePacket.getNextBaseTask()!=null){
				basePacket = basePacket.getTailTask();
			}
			basePacket.onSuccess(mId);
			if(mTaskManager!=null)
				mTaskManager.onRunningFinish(this,true);
		}
		else {
			BaseTaskPacket basePacket = mBaseTaskPacket;
				if(basePacket.getNextBaseTask()!=null){
					basePacket = basePacket.getTailTask();
				}
				mBaseTaskPacket.onFail(mId,mException);
			if(mTaskManager!=null)
				mTaskManager.onRunningFinish(this,false);
		}
		mBaseTaskPacket.clearLink();
	}

	@Override
	protected Long doInBackground(Object... params)
	{
		return Long.valueOf(synchronousRequest(mBaseTaskPacket));
	}

	
}

package com.artion.androiddemos.task;

import java.util.LinkedList;
import java.util.concurrent.Executors;

import android.content.Context;

public class SerialTaskManager extends BaseTaskManager
{

	/**
	 * 最大并发任务数
	 * @param maxTaskNum
	 */
	public SerialTaskManager()
	{
		mExecutorService = Executors.newFixedThreadPool(1);
		mRunningTaskLists = new LinkedList<BaseTask>();
	}


	/**
	 * 获取对应ID的任务对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public BaseTask getHttpEng(int id)
	{
		synchronized (mRunningTaskLists) {
			for(BaseTask t:mRunningTaskLists){
				if(t.mId == id)
					return t;
			}
		}
		return null;	
	}

	

	/**
	 * 
	 @param mayInterruptIfRunning
	 *            true if the thread executing this task should be interrupted;
	 *            otherwise, in-progress tasks are allowed to complete.
	 * 
	 * @param id
	 *            需要取消的网络请求的id
	 * 
	 * @return false if the task could not be cancelled, typically because it
	 *         has already completed normally; true otherwise
	 * 
	 */
	@Override
	public boolean cancelById(int id, boolean mayInterruptIfRunning)
	{
		BaseTask engine = getHttpEng(id);
		if (engine != null)
		{
			removeFromRunningList(engine);
			return engine.cancel(mayInterruptIfRunning);
		}
		return Boolean.FALSE;
	}
	
	public void cancelByContext(Context context, boolean mayInterruptIfRunning)
	{
		BaseTask localTask;
		synchronized (mRunningTaskLists) {
			for(int i=mRunningTaskLists.size() - 1;i>=0;i--){
				localTask = mRunningTaskLists.get(i);
				if(localTask.getContext().hashCode() == context.hashCode()){
					localTask.cancel(mayInterruptIfRunning);
					mRunningTaskLists.remove(i);
				}
			}
		}
	}
	
	/**
	 * 任务执行完成回调
	 * @param engine
	 * @param result 是否成功完成
	 */
	@Override
	public void onRunningFinish(BaseTask engine,boolean result){
		removeFromRunningList(engine);
		if(!result && bCancelIfRequestFail){
			cancelAll();
		}
	}
	
	/**
	 * 任务结束时会调用此方法
	 * 
	 * @param id
	 */
	@Override
	protected boolean removeFromRunningList(BaseTask engine)
	{
		return mRunningTaskLists.remove(engine);
	}
	/**
	 * 创建并执行任务
	 * 
	 * @param out
	 * @param in
	 * @param context
	 * @param priority
	 * @return
	 */
	@SuppressWarnings({ "rawtypes"})
	@Override
	public int putHttpEngine(BaseTaskPacket packet, Context context,int priority)
	{

		BaseTask engine = new BaseTask(getTaskAtomicID(), 
				packet,this, context);
		runTask(engine);
		return engine.getId();
	}

	/**
	 * 执行任务
	 * @param engine
	 */
	@Override
	protected void runTask(BaseTask engine)
	{
		mRunningTaskLists.add(engine);
		engine.executeOnExecutor(mExecutorService);
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		cancelAll();
		super.finalize();
	}



}

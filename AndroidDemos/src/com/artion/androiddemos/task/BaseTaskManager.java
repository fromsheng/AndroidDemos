package com.artion.androiddemos.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;

public abstract class BaseTaskManager
{	
	protected boolean bCancelIfRequestFail = false;
	protected ExecutorService mExecutorService;
	protected LinkedList<BaseTask> mRunningTaskLists;
	
	/**
	 * 最低优先级为0，最高优先级为3.任务默认优先级为GJHTTPTASK_PRIORITY_DEFAULT
	 */
	public static final int GJHTTPTASK_PRIORITY_LOW = 0;								//最低优先级
	public static final int GJHTTPTASK_PRIORITY_DEFAULT = GJHTTPTASK_PRIORITY_LOW+1;	//默认优先级
	public static final int GJHTTPTASK_PRIORITY_HIGH = GJHTTPTASK_PRIORITY_DEFAULT+1;	//高优先级
	public static final int GJHTTPTASK_PRIORITY_MAX = GJHTTPTASK_PRIORITY_HIGH+1;		//最高优先级
	
	private static AtomicInteger mAuthAlgorithm = new  AtomicInteger();
	
	/**
	 * 获取全局自增长唯一的ID值
	 * @return
	 */
	public static int getTaskAtomicID(){
		return mAuthAlgorithm.getAndIncrement();
	}

	/**
	 * 获取并发任务管理队列
	 * @param maxConcurrentNum 最大并发请求个数,上限为15个
	 * @return
	 */
	public static ConcurrentTaskManager getConcurrentHttpEngineManager(int maxConcurrentNum){
		return new ConcurrentTaskManager(maxConcurrentNum);
	}
	/**
	 * 获取串行任务管理队列
	 * @return
	 */
	public static SerialTaskManager getSerialHttpEngineManager(){
		return new SerialTaskManager();
	}

	
	/**
	 * 当请求错误后，等待请求队列是否全部取消
	 * @param cancel
	 */
	public void setCancelIfRequestFail(boolean cancel){
		bCancelIfRequestFail = cancel;
	}
	
	/**
	 * 最大并发任务数
	 * @param maxTaskNum
	 */
	protected BaseTaskManager()
	{
	}
	
	/**
	 * 取消所有网络请求
	 */
	public void cancelAll() {
		synchronized (mExecutorService) {
			mExecutorService.shutdownNow();
		}
		synchronized (mRunningTaskLists) {
			for(int i=0;i<mRunningTaskLists.size();i++){
				mRunningTaskLists.get(i).cancel(true);
			}
			mRunningTaskLists.clear();
		}

	}

	/**
	 * 获取对应ID的网络请求对象
	 * 
	 * @param id
	 * @return
	 */
	public abstract BaseTask getHttpEng(int id);


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
	public abstract boolean cancelById(int id, boolean mayInterruptIfRunning);
	
	/**
	 * 
	 @param mayInterruptIfRunning
	 *            true if the thread executing this task should be interrupted;
	 *            otherwise, in-progress tasks are allowed to complete.
	 * 
	 * @param context
	 *            需要取消的网络请求的context
	 * 
	 * @return false if the task could not be cancelled, typically because it
	 *         has already completed normally; true otherwise
	 * 
	 */
	public abstract void cancelByContext(Context context, boolean mayInterruptIfRunning);
	
	/**
	 * 任务执行完成回调
	 * @param engine
	 * @param result 是否成功完成
	 */
	public abstract void onRunningFinish(BaseTask task,boolean result);
	
	/**
	 * 任务结束时会调用此方法
	 * 
	 * @param id
	 */
	protected abstract boolean removeFromRunningList(BaseTask task);
	/**
	 * 创建并执行任务
	 * (串行队列忽略优先级priority)
	 * @see ConcurrentTaskManager
	 * @param out 上传进度回调
	 * @param in 下载进度回调
	 * @param context 上下文
	 * @param priority 任务优先级
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract int putHttpEngine(BaseTaskPacket packet, Context context,
			int priority);
	
	/**
	 * 创建并执行任务
	 * 
	 * @param out
	 * @param in
	 * @param context
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int putHttpEngine(BaseTaskPacket packet, Context context)
	{
		return putHttpEngine(packet,context,ConcurrentTaskManager.GJHTTPTASK_PRIORITY_DEFAULT);
	}

	
	@Override
	protected void finalize() throws Throwable
	{
		cancelAll();
		super.finalize();
	}
	
	protected abstract void runTask(BaseTask engine);
}

package com.artion.androiddemos.task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Executors;

import android.content.Context;

public class ConcurrentTaskManager extends BaseTaskManager
{
	public static final int MAXCORE = 30;					//最大并发量峰值
	
	/**
	 * 最低优先级为0，最高优先级为3.任务默认优先级为GJHTTPTASK_PRIORITY_DEFAULT
	 */
	public static final int GJHTTPTASK_PRIORITY_LOW = 0;								//最低优先级
	public static final int GJHTTPTASK_PRIORITY_DEFAULT = GJHTTPTASK_PRIORITY_LOW+1;	//默认优先级
	public static final int GJHTTPTASK_PRIORITY_HIGH = GJHTTPTASK_PRIORITY_DEFAULT+1;	//高优先级
	public static final int GJHTTPTASK_PRIORITY_MAX = GJHTTPTASK_PRIORITY_HIGH+1;		//最高优先级

	private LinkedList<ArrayList<BaseTask>> mPreTaskArrayLists;					//优先级请求队列列表
	private int iCoreTask = 0;															//并发量
	
	/**
	 * 最大并发网络请求任务数
	 * @param maxTaskNum
	 */
	public ConcurrentTaskManager(int maxTaskNum)
	{

		if(maxTaskNum>MAXCORE || maxTaskNum<=0){
			throw new RuntimeException(String.format(
					"maxTaskNum exception,The maxTaskNum of value should be in 0 and %d.",
					MAXCORE));
		}

		iCoreTask = maxTaskNum;
		mExecutorService = Executors.newFixedThreadPool(iCoreTask);
		mRunningTaskLists = new LinkedList<BaseTask>();
		mPreTaskArrayLists = new LinkedList<ArrayList<BaseTask>>();
		for(int i=0;i<GJHTTPTASK_PRIORITY_MAX+1;i++)
			mPreTaskArrayLists.add(new ArrayList<BaseTask>());
	}

	
	/**
	 * 取消所有网络请求
	 */
	@Override
	public void cancelAll()
	{
		super.cancelAll();

		ArrayList<BaseTask> list;
		for(int i=0;i<GJHTTPTASK_PRIORITY_MAX+1;i++){
			list = mPreTaskArrayLists.get(i);
			for(int j=0;j<list.size();j++)
				list.get(j).cancel(true);
			list.clear();
		}

	}

	/**
	 * 获取对应ID的网络请求对象
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public BaseTask getHttpEng(int id)
	{
		BaseTask engine = getRunningHttpEng(id);
		if(engine == null){
			engine = getPreHttpEng(id);
		}
		return engine;
	}
	
	/**
	 * 获取正在运行的任务
	 * @param id
	 * @return
	 */
	private BaseTask getRunningHttpEng(int id)
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
	 * 获取在优先级队列等待的任务
	 * @param id
	 * @return
	 */
	private BaseTask getPreHttpEng(int id)
	{
		BaseTask engine = null;
		synchronized (mPreTaskArrayLists) {
			ArrayList<BaseTask> list;
			for(int i=0;i<GJHTTPTASK_PRIORITY_MAX+1;i++){
				list = mPreTaskArrayLists.get(i);
				for(BaseTask t:list){
					if(t.mId == id)
						return t;
				}
			}
		}		
		return engine;	
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
		BaseTask engine = getRunningHttpEng(id);
		if (engine != null)
		{
			removeFromRunningList(engine);
			return engine.cancel(mayInterruptIfRunning);
		}
		engine = getPreHttpEng(id);
		if (engine != null){
			removeFromPreList(engine);
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
		synchronized (mPreTaskArrayLists) {
			ArrayList<BaseTask> list;
			for(int i=0;i<GJHTTPTASK_PRIORITY_MAX+1;i++){
				list = mPreTaskArrayLists.get(i);
				for(int j=list.size() - 1;j>=0;j--){
					localTask = list.get(j);
					if(localTask.getContext().hashCode() == context.hashCode()){
						localTask.cancel(mayInterruptIfRunning);
						list.remove(j);
					}
				}
			}
		}	
	}
	
	private BaseTask getNextEngine()
	{
		synchronized (mPreTaskArrayLists) {
			ArrayList<BaseTask> list;
			for(int i=GJHTTPTASK_PRIORITY_MAX;i>=0;i--){
				list = mPreTaskArrayLists.get(i);
				if(list.size()>0){
					return list.remove(0);
				}
			}
		}
		return null;
	}
	/**
	 * 创建并执行网络请求
	 * 
	 * @param out
	 * @param in
	 * @param context
	 * @param priority
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int putHttpEngine(BaseTaskPacket packet, Context context,
			int priority)
	{

		BaseTask engine = new BaseTask(getTaskAtomicID(), 
				packet,this, context);
		engine.setPriority(Math.max(GJHTTPTASK_PRIORITY_LOW, 
				Math.min(priority, GJHTTPTASK_PRIORITY_MAX)));
		if(mRunningTaskLists.size()<iCoreTask && isShouldRun(engine.getPriority()))
		{
			runTask(engine);
//			GJLog.e("runTask", engine.getPriority()+"");
		}
		else {
			addToPreList(engine);
//			GJLog.e("addToPreList", engine.getPriority()+"");
		}
		return engine.getId();
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
		else {
			BaseTask httpEngine = getNextEngine();
			if(httpEngine!=null){
			//	GJLog.e("onRunningFinish runTask", httpEngine.getPriority()+"");
				runTask(httpEngine);
			}
		}
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
	/**
	 * 从正在执行的队列中移除对象
	 * @param id
	 */
	@Override
	protected boolean removeFromRunningList(BaseTask engine)
	{
		return mRunningTaskLists.remove(engine);
	}
	/**
	 * 从正在等待的优先级队列中移除对象
	 * @param engine
	 * @return
	 */
	protected boolean removeFromPreList(BaseTask engine) {
		return mPreTaskArrayLists.get(engine.getPriority()).remove(engine);
	}
	/**
	 * 添加任务到优先级任务队列中
	 * @param engine
	 */
	private void addToPreList(BaseTask engine)
	{
		mPreTaskArrayLists.get(engine.getPriority()).add(engine);
	}
	/**
	 * 判断是否存在比该优先级还要高优先级的任务存在
	 * @param priority
	 * @return
	 */
	private boolean isShouldRun(int priority)
	{
		for(int i=priority;i<=GJHTTPTASK_PRIORITY_MAX;i++){
			if(mPreTaskArrayLists.get(i).size()>0)
				return false;
		}
		return true;
	}	
	@Override
	protected void finalize() throws Throwable
	{
		cancelAll();
		super.finalize();
	}

}

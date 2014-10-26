package com.artion.androiddemos.task;

import android.content.Context;


public abstract class BaseTaskPacket<T>
{

	private boolean bIsContinueIfFail = false;//是否继续链式操作
	@SuppressWarnings("rawtypes")
	private BaseTaskPacket mPreBaseTask;
	@SuppressWarnings("rawtypes")
	private BaseTaskPacket mNextBaseTask;
	private T mData;
	
	public BaseTaskPacket(T d) {
		mData = d;
	}

	final public void run(Context context)throws AbsException{
		doTask(mData,context);
	}
	
	final public void onSuccess(int Id){
		onSuccess(Id, mData);
	}
	
	final public void onFail(int Id,AbsException exception){
		onFail(Id, mData, exception);
	}

	final public void onSuccessPreTask()
	{
		onSuccessPreTask(this);
	}	

	final public void onFailPreTask(AbsException e)
	{
		onFailPreTask(this, e);
	}
	
	
	/**
	 * 根据返回码和错误流返回异常
	 * @param msg
	 * @param responseCode
	 * @return
	 */
	public AbsException getException(String msg,int responseCode)
	{
		return new AbsException(msg,responseCode);
	}
	
	/**
	 * 
	 * @param Id 任务ID
	 * @param packet	数据回调对象(如果是链式操作，packet的T类型为最后一个包对象类型)
	 * @return
	 */
	public abstract void onSuccess(int Id,T data);
	
	/**
	 * 
	 * @param Id 任务ID
	 * @param packet	数据回调对象(如果是链式操作，packet的T类型为最后一个包对象类型)
	 * @param exception 异常对象
	 * @return
	 */
	public abstract void onFail(int Id,T data,AbsException exception);
	
	
	public abstract void doTask(T data,Context context)throws AbsException;
	
	
	/**
	 * 上一个包任务执行完成后回调
	 * @param preBaseTask
	 * @return 返回值决定是否继续链式操作，true继续，false停止
	 */
	public void onSuccessPreTask(BaseTaskPacket<T> preBaseTask)
	{

	}	
	
	/**
	 * 上一个包任务执行完成后回调
	 * @param preBaseTask
	 * @return 返回值决定是否继续链式操作，true继续，false停止
	 */
	public void onFailPreTask(BaseTaskPacket<T> preBaseTask,AbsException e)
	{

	}
	
	/**
	 * 设置链式操作失败之后是否继续
	 * @param con
	 */
	public void setContinueLink(boolean con){
		bIsContinueIfFail = con;
	}
	/**
	 * 是否继续链式操作
	 * @return
	 */
	public boolean getContinueLink(){
		return bIsContinueIfFail;
	}
	
	/**
	 * 链式操作任务包
	 * 需要注意的是：最后网络回调返回的Task为链式操作的最后一个包
	 * @param packet
	 * @param con
	 * @return
	 */
	public BaseTaskPacket<T> next(BaseTaskPacket<T> task,boolean con)
	{
		this.mNextBaseTask = task;
		task.setPreBaseTask(this);
		task.setContinueLink(con);
		return task;
	}
	/**
	 * 链式操作任务包
	 * 需要注意的是：最后网络回调返回的Task为链式操作的最后一个包
	 * @param packet
	 * @return
	 */
	public BaseTaskPacket<T> next(BaseTaskPacket<T> packet)
	{
		this.mNextBaseTask = packet;
		packet.setPreBaseTask(this);
		packet.setContinueLink(false);
		return packet;
	}
	/**
	 * 获取链式操作首包
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseTaskPacket getHeadTask()
	{
		BaseTaskPacket<T> headTask = this;
		while(headTask.getPreBaseTask()!=null){
			headTask = headTask.getPreBaseTask();
		}
		return headTask;
	}
	/**
	 * 获取链式操作尾包
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BaseTaskPacket getTailTask()
	{
		BaseTaskPacket<T> tailTask = this;
		while(tailTask.getNextBaseTask()!=null){
			tailTask = tailTask.getNextBaseTask();
		}
		return tailTask;		
	}
	/**
	 * 清除链式包队列
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void clearLink()
	{
		BaseTaskPacket headTask = this;
		while(headTask.getPreBaseTask()!=null){
			headTask = headTask.getPreBaseTask();
		}
		BaseTaskPacket tempTask;
		do {
			 tempTask = headTask.getNextBaseTask();
			headTask.setNextBaseTask(null);
			headTask = tempTask;
		} while (headTask!=null);
	}
	/**
	 * 设置下一个链式操作任务包
	 * @param packet
	 */
	protected void setNextBaseTask(BaseTaskPacket<T> packet)
	{
		this.mNextBaseTask = packet;
	}
	/**
	 * 设置上一个链式操作任务包
	 * @param packet
	 */
	protected void setPreBaseTask(BaseTaskPacket<T> packet)
	{
		this.mPreBaseTask = packet;
	}
	/**
	 * 获取上一个链式操作任务包
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public BaseTaskPacket getNextBaseTask()
	{
		return mNextBaseTask;
	}
	/**
	 * 获取下一个链式操作任务包
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public BaseTaskPacket getPreBaseTask()
	{
		return mPreBaseTask;
	}
	
}

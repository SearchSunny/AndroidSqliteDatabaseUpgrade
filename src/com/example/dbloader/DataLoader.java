package com.example.dbloader;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * 2/可加载任意类型数据，并能帮助子类简化使用AsyncTaskLoader
 * 为更好地处理任意数据的加载，需要一个更通用的loader
 * DataLoader类可处理一些任何AsyncTaskLoader子类能够处理和简单任务，而仅把loadinBackground()方法的实现任务留给自己的子类
 * @author miaowei
 * 使用D泛型类存储加载的数据实例
 * @param <D>
 */
@SuppressLint("NewApi")
public abstract class DataLoader<D> extends AsyncTaskLoader<D> {

	private D mData;
	public DataLoader(Context context) {
		super(context);
	}
	
	/**
	 * 检查数据是否已加载，如已加载则立即发送，否则就调用超类forceLoad方法去获取数据
	 */
	@Override
	protected void onStartLoading() {
		if (mData != null) {
			
			deliverResult(mData);
		}else {
			forceLoad();
		}
	}
	/**
	 * 先将新数据对象存储起来
	 * 如果loader启动(表示数据可以发送)
	 * 调用超类版本的方法将数据发送出去
	 */
	@Override
	public void deliverResult(D data) {
		//先将新数据对象存储起来
		mData = data;
		//1、如果loader启动(表示数据可以发送)
		if (isStarted()) {
			//调用超类版本的方法将数据发送出去
			super.deliverResult(data);
		}
		
	}

}

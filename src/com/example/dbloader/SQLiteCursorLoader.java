package com.example.dbloader;

import android.annotation.SuppressLint;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

/**
 *1/ Cursor框架类的简化版本，可与来自任何数据源的Cursor协同工作
 * 后台线程上使用Loader 尽可能避免在主线程上进行数据库操作
 * Loader
 * AsyncTaskLoader 是一个抽象loader
 * CursorLoader
 * @author miaowei
 *
 */
@SuppressLint("NewApi")
public abstract class SQLiteCursorLoader extends AsyncTaskLoader<Cursor> {

	private Cursor mCursor;
	
	public SQLiteCursorLoader(Context context) {
		super(context);
	}

	/**
	 * 
	 * @return
	 */
	protected abstract Cursor loadCursor();
	
	/**
	 * 以保证数据在发送给主线程之前已加载到内存中
	 */
	@Override
	public Cursor loadInBackground() {
		Cursor cursor = loadCursor();
		if (cursor != null && cursor.getCount() > 0) {
			
			cursor.getCount();
		}
		return cursor;
	}
	
	/**
	 * 负责处理两件事情
	 * 
	 * 先将新数据对象存储起来
	 * 如果loader启动(表示数据可以发送)
	 * 调用超类版本的方法将数据发送出去
	 */
	@Override
	public void deliverResult(Cursor data) {
		
		Cursor oldCursor = mCursor;
		mCursor = data;
		//1、如果loader启动(表示数据可以发送)
		if (isStarted()) {
			
			super.deliverResult(data);
		}
		//2、如果旧的cursor不再需要，它会被关闭以释放资源
		if (oldCursor != null && oldCursor != data && !oldCursor.isClosed()) {
			
			oldCursor.close();
		}
		
	}
	/**
	 * 检查数据是否已加载，如已加载则立即发送，否则就调用超类forceLoad方法去获取数据
	 */
	@Override
	protected void onStartLoading() {
		if (mCursor != null) {
			
			deliverResult(mCursor);
		}
		if (takeContentChanged() || mCursor == null) {
			
			forceLoad();
		}
		
	}
	
	@Override
	protected void onStopLoading() {
		
		cancelLoad();
	}
	
	@Override
	public void onCanceled(Cursor data) {
		if (data != null && !data.isClosed()) {
			
			data.close();
		}

	}
	
	@Override
	protected void onReset() {
		super.onReset();
		
		onStopLoading();
		if (mCursor != null && !mCursor.isClosed()) {
			
			mCursor.close();
		}
		
		mCursor = null;
	}
}

package com.example.androidsqlitedatabaseupgrade;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.db.ImageBean;
import com.example.db.DataBaseHelper.RunCursor;
import com.example.dbloader.RunCursorAdapeter;
import com.example.dbloader.RunListCursorLoader;

@SuppressLint("NewApi")
public class ListViewActivity extends ListActivity implements LoaderCallbacks<Cursor>{

	private static final int REQUEST_NEW_RU= 0;
	private ArrayAdapter arrayAdapter;
	private ArrayList<String> names;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		names = new ArrayList<String>();
		arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,names);
		this.setListAdapter(arrayAdapter);
		
		//通知loadmanager开始工作
		//初始化
		getLoaderManager().initLoader(0, null, this);
	}

	/**
	 * 1、需要创建loader时，LoaderManager会调用onCreateLoader()方法
	 * 如果有多个相同类型的loader,可使用id参数区分它们
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		return new RunListCursorLoader(this);
	}

	/**
	 *2、 数据在后台一完成加载,onLoadFinished()方法就会在主线程上被调用
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		RunCursor runCursor = (RunCursor)cursor;
		ArrayList<ImageBean> list =  runCursor.getRuns();
		for (int i = 0; i <list.size(); i++) {
			
			names.add(list.get(i).getImageName());
			
		}
		arrayAdapter.notifyDataSetChanged();
		/*RunCursorAdapeter adapeter = new RunCursorAdapeter(this, (RunCursor)cursor);
		this.setListAdapter(adapeter);*/
		
	}

	/**
	 * 3、在没有加载数据时，onLoaderReset()方法会被调用 ，安全起见，我们设置列表adapter值为空，以停止使用cursor
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
		this.setListAdapter(null);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (REQUEST_NEW_RU == requestCode) {
			//在明确知道或怀疑数据比较陈旧时，通常会使用该方法重新加载最新数据
			//强制重启现有loader
			getLoaderManager().restartLoader(0, null, this);
		}
	}
}

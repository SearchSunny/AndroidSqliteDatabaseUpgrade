package com.example.androidsqlitedatabaseupgrade;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.db.DBManager;
import com.example.db.DataBaseHelper.RunCursor;
import com.example.db.ImageBean;
import com.example.dbloader.ImageLoader;
import com.example.dbloader.RunListCursorLoader;
/**
 *android数据库升级
 * @author miaowei
 *
 */
@SuppressLint("NewApi")
public class MainActivity extends Activity implements LoaderCallbacks<Cursor> {
	/**
	 * 添加数据
	 */
	private Button btnAdd;
	/**
	 * 查询数据
	 */
	private Button btnQuery;
	
	/**
	 * 
	 */
	private Button btnNext;
	
	private int id;
	
	private ArrayList<String> names;
	
	private ListView listView;
	
	private ArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		names = new ArrayList<String>();
		btnAdd = (Button)findViewById(R.id.btnAdd);
		btnQuery = (Button)findViewById(R.id.btnQuery);
		btnNext = (Button)findViewById(R.id.btnNext);
		listView = (ListView)findViewById(R.id.listView);
		DBManager.getInstance(MainActivity.this);
		adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,names);
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(onItemClickListener);
		
		btnAdd.setOnClickListener(onClickListener);
		btnQuery.setOnClickListener(onClickListener);
		btnNext.setOnClickListener(onClickListener);
		
		
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			
			Log.i("console", "arg2==="+arg2);
			Intent intent = new Intent(MainActivity.this,ImageBeanActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("Id", String.valueOf(arg2+1));
			intent.putExtras(bundle);
			startActivity(intent);
		}
		
		
	};
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.btnAdd:
				ImageBean bean = new ImageBean();
				bean.setImageId("id"+id);
				bean.setImageName("imageName"+id);
				DBManager.getInstance(MainActivity.this).addImageName(bean);
				id++;
				break;

			case R.id.btnQuery:
				
				//普通方式实现数据加载
				/*ArrayList<ImageBean> list = DBManager.getInstance(MainActivity.this).getAllImageBeans();
				for (int i = 0; i <list.size(); i++) {
					
					names.add(list.get(i).getImageName());
					
				}
				adapter.notifyDataSetChanged();*/
				
				
				
				//使用loader方式实现异步加载数据
				//通知loadmanager开始工作
				//初始化
				MainActivity.this.getLoaderManager().initLoader(0, null, MainActivity.this);
				break;
			case R.id.btnNext:
				
				startActivity(new Intent(MainActivity.this,ListViewActivity.class));
				
				break;
			default:
				break;
			}
		}
	};

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
		adapter.notifyDataSetChanged();
	}

	/**
	 * 3、在没有加载数据时，onLoaderReset()方法会被调用 ，安全起见，我们设置列表adapter值为空，以停止使用cursor
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		
		listView.setAdapter(null);
		
	}
	
	

}

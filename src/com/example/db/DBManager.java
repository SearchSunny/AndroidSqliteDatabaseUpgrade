package com.example.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.db.DataBaseHelper.RunCursor;

/**
 * 提供数据库增、删、改、查
 * @author miaowei
 *
 */
public class DBManager {
	
	/**
	 * 提供数据库增、删、改、查 API
	 */
	private static SQLiteDatabase db;
	/**
	 * 提供创建数据库操作
	 */
	private DataBaseHelper dataBaseHelper;
	private static DBManager dManager;
	private static Context mContext;
	
	private DBManager() {
		dataBaseHelper = new DataBaseHelper(mContext);
		//getWritableDatabase 适用增、删、改
		//db = dataBaseHelper.getWritableDatabase();
		//getReadableDatabase 适用查询
		//db= dataBaseHelper.getReadableDatabase();
	}

	public static DBManager getInstance(Context context) {
		mContext = context;
		if (dManager == null)
			dManager = new DBManager();
		return dManager;
	}
	
	/**
	 * 添加bean到数据库
	 * @param bean
	 * @return
	 */
	public boolean addImageName(ImageBean bean) {
		long i = 0;
		try {
			db = dataBaseHelper.getWritableDatabase();
			if (db != null) {
				
				ContentValues contentValues = new ContentValues();
				//此处put的key要于创建表的列表相同
				contentValues.put("IMGNAME", bean.getImageName());
				contentValues.put("IMAGEID", bean.getImageId());

				i = db.insert(DataBaseHelper.TABNAME_IMG, null, contentValues);
				Log.i("console", "i="+i);
				db.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			db.close();
		}
		return i != -1;

	}
	/**
	 * 获取bean数据
	 * @return
	 */
	public ArrayList<ImageBean> getAllImageBeans(){
		ArrayList<ImageBean> list = null;
		ImageBean bean;
		try {
			db= dataBaseHelper.getReadableDatabase();
			if (db != null) {
				// 第一个参数String：表名  
				// 第二个参数String[]:要查询的列名  
				// 第三个参数String：查询条件  
				// 第四个参数String[]：查询条件的参数  
				// 第五个参数String:对查询的结果进行分组  
				// 第六个参数String：对分组的结果进行限制  
				// 第七个参数String：对查询的结果进行排序
				Cursor cursor = db.query(DataBaseHelper.TABNAME_IMG,null,null,null,null,null,null,null);
				if (cursor.getCount() > 0) {
					list = new ArrayList<ImageBean>();
					while (cursor.moveToNext()) {
						
						bean = new ImageBean();
						String imageId = cursor.getString(cursor.getColumnIndex("IMAGEID"));
						String imageName = cursor.getString(cursor.getColumnIndex("IMGNAME"));
						
						bean.setImageId(imageId);
						bean.setImageName(imageName);
						
						list.add(bean);
					}
				}
				
				db.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			
			db.close();
		}
		
		return list;
	}
	
	/**
	 * 获取bean数据
	 * @return
	 */
	public  Cursor getAllImageBeansCursor(){
		ArrayList<ImageBean> list = null;
		ImageBean bean;
		Cursor cursor = null;
		try {
			db= dataBaseHelper.getReadableDatabase();
			if (db != null) {
				// 第一个参数String：表名  
				// 第二个参数String[]:要查询的列名  
				// 第三个参数String：查询条件  
				// 第四个参数String[]：查询条件的参数  
				// 第五个参数String:对查询的结果进行分组  
				// 第六个参数String：对分组的结果进行限制  
				// 第七个参数String：对查询的结果进行排序
				cursor = db.query(DataBaseHelper.TABNAME_IMG,null,null,null,null,null,null,null);
				if (cursor.getCount() > 0) {
					list = new ArrayList<ImageBean>();
					while (cursor.moveToNext()) {
						
						bean = new ImageBean();
						String imageId = cursor.getString(cursor.getColumnIndex("IMAGEID"));
						String imageName = cursor.getString(cursor.getColumnIndex("IMGNAME"));
						
						bean.setImageId(imageId);
						bean.setImageName(imageName);
						
						list.add(bean);
					}
				}
				
				db.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			
			db.close();
		}
		
		return cursor;
	}
	
	
	/**
	 * 根据主键iD获取bean数据
	 * @return
	 */
	public ImageBean getImageBeanById(long Id){
		ImageBean bean = null;
		try {
			db= dataBaseHelper.getReadableDatabase();
			if (db != null) {
				// 第一个参数String：表名  
				// 第二个参数String[]:要查询的列名  
				// 第三个参数String：查询条件  
				// 第四个参数String[]：查询条件的参数  
				// 第五个参数String:对查询的结果进行分组  
				// 第六个参数String：对分组的结果进行限制  
				// 第七个参数String：对查询的结果进行排序
				Cursor cursor = db.query(DataBaseHelper.TABNAME_IMG,new String[]{"ID","IMGNAME","IMAGEID"},"ID = ?",new String[]{""+Id},null,null,null,null);
				if (cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						
						bean = new ImageBean();
						String imageId = cursor.getString(cursor.getColumnIndex("IMAGEID"));
						String imageName = cursor.getString(cursor.getColumnIndex("IMGNAME"));
						
						bean.setImageId(imageId);
						bean.setImageName(imageName);
						
					}
				}
				
				db.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally{
			
			db.close();
		}
		return bean;
	}

	/**
	 * 
	 * @return
	 */
	public RunCursor queryRuns(){
		
		return dataBaseHelper.queryRuns();
		
	}
	
	/**
	 * 获取单个对象
	 * @param id
	 * @return
	 */
	public ImageBean queryImageById(long id){

		return this.getImageBeanById(id);
	}

}

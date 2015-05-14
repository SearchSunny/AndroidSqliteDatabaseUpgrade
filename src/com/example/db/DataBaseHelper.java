package com.example.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
/**
 * 创建数据库
 * @author miaowei
 *
 */
public class DataBaseHelper extends SQLiteOpenHelper {
	/**
	 * 数据库名称
	 */
	public static final String DBNAME = "test.db";
	/**
	 * 数据库版本
	 */
	public final static int DB_VERSION = 2;
	/**
	 * 表名
	 */
	public static final String TABNAME_IMG = "img_name";
	
	
	
	//升级数据库步骤
	//1、将表A重命名，改为A_temp临时表
	//2、创建新表A
	//3、将临时表的数据添加到表A
	//4、将临时表删除

	/**
	 * 
	 * @param db 执行SQL语句
	 * @param tableName 表名
	 * @param columns 添加的列名
	 */
	private void upgradeTables(SQLiteDatabase db,String tableName,String columns){
		
		try {
			db.beginTransaction();
			//1 将表A重命名，改为A_temp临时表
			String tempTableName = tableName + "_temp";
			String sql = "alter table "+ tableName + " RENAME TO "+ tempTableName;
			db.execSQL(sql);
			
		
			//2 创建新表A
			onCreate(db);
			
			//3 将临时表的数据添加到表A
			sql =   "INSERT INTO " + tableName +  
	                " (" + columns + ") " +  
	                " SELECT " + columns + " FROM " + tempTableName;  
			db.execSQL(sql);
			
			
			//4 将临时表删除
			db.execSQL("drop table " + tempTableName);
			
			db.setTransactionSuccessful(); 
		}catch(SQLException e){
			
			e.printStackTrace();
		}
		catch (Exception e) {
			
			e.printStackTrace();
			
		}finally{
			
			db.endTransaction();
		}
		
	}
	public DataBaseHelper(Context context) {
		super(context, DBNAME, null, DB_VERSION);
	}



	/**
	 * 创建新的数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("console", "onCreate==");
		//新建数据库版本为1时使用
		//String sql_img_name = "CREATE TABLE IF NOT EXISTS " + TABNAME_IMG + "("
		//		+ "ID INTEGER PRIMARY KEY, IMGNAME TEXT,IMAGEID TEXT" + ")";
		
		//新建数据库版本为2时使用
		String sql_img_name = "CREATE TABLE IF NOT EXISTS " + TABNAME_IMG + "("
				+ "UID INTEGER PRIMARY KEY autoincrement,ID INTEGER, IMGNAME TEXT,IMAGEID TEXT" + ")";
		
		db.execSQL(sql_img_name);
	}

	/**
	 * 数据库版本升级调用
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i("console", "onUpgrade==");
		Log.i("console", "oldVersion=="+oldVersion);
		Log.i("console", "newVersion=="+newVersion);
		//主要是考虑到夸版本升级，比如有的用户一直不升级版本，
		//数据库版本号一直是1，而客户端最新版本其实对应的数据库版本已经是4了，
		//那么我中途可能对数据库做了很多修改，通过这个for循环，可以迭代升级，不会发生错误。
		for (int i = oldVersion; i <= newVersion; i++) {
			
			switch (i) {
			case 2:
				//添加列的sql语句
				/*String sql = "alter table ["+TABNAME_IMG+"] add [UID] INTEGER PRIMARY KEY autoincrement";
				db.execSQL(sql);*/
				upgradeTables(db, TABNAME_IMG, "ID, IMGNAME,IMAGEID");
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public RunCursor queryRuns(){
		
		Cursor wrapped = getReadableDatabase().query(TABNAME_IMG, null, null, null, null, null, null,null);
		
		return new RunCursor(wrapped);
	}

	/**
	 * 
	 * @author miaowei
	 *
	 */
	public static class RunCursor extends CursorWrapper{

		public RunCursor(Cursor cursor) {
			super(cursor);
		}
		
		public ImageBean getRun(){
			
			if (isBeforeFirst() || isAfterLast()) {
				
				return null;
			}
			ImageBean bean = new ImageBean();
			String imageId = getString(getColumnIndex("IMAGEID"));
			String imageName = getString(getColumnIndex("IMGNAME"));
			
			bean.setImageId(imageId);
			bean.setImageName(imageName);
			
			return bean;
		}
		
		/**
		 * 
		 * @return
		 */
		public ArrayList<ImageBean> getRuns(){
			ArrayList<ImageBean> imageBeans = new ArrayList<ImageBean>();
				while (this.moveToNext()) {
					
					ImageBean bean = new ImageBean();
					String imageId = getString(getColumnIndex("IMAGEID"));
					String imageName = getString(getColumnIndex("IMGNAME"));
					
					bean.setImageId(imageId);
					bean.setImageName(imageName);
					imageBeans.add(bean);
					
				}
				
			return imageBeans;
		}
		
	}
}

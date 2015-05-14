package com.example.dbloader;

import com.example.db.DBManager;
import com.example.db.ImageBean;

import android.content.Context;
import android.database.Cursor;

/**
 * 加载单个实体的loader
 * @author miaowei
 *
 */
public class ImageLoader extends DataLoader<ImageBean>{

	private long imageId;
	public ImageLoader(Context context,long imageId) {
		super(context);
		this.imageId = imageId;
	}

	/**
	 * 返回具体对象
	 */
	@Override
	public ImageBean loadInBackground() {
		//根据imageId查询具体对象并返回
		return DBManager.getInstance(getContext()).queryImageById(imageId);
	}


}

package com.example.androidsqlitedatabaseupgrade;

import com.example.db.ImageBean;
import com.example.dbloader.ImageLoader;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
/**
 * 单个对象
 * @author miaowei
 *
 */
@SuppressLint("NewApi")
public class ImageBeanActivity extends Activity implements LoaderCallbacks<ImageBean>{

	private TextView imageId;
	private TextView imageName;
	private TextView imageIndex;
	private ImageBean mImageBean;
	
	private String imageIndexString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		View view = View.inflate(this, R.layout.layout_imagebean, null);
		setContentView(view);
		imageId = (TextView)view.findViewById(R.id.imageId);
		imageName = (TextView)view.findViewById(R.id.imageName);
		imageIndex = (TextView)view.findViewById(R.id.imageIndex);
		Bundle bundle = getIntent().getExtras();
		
		getLoaderManager().initLoader(0, bundle, this);
		
	}
	
	
	@Override
	public Loader<ImageBean> onCreateLoader(int id, Bundle args) {
		
		Bundle bundle = args;
		imageIndexString = bundle.getString("Id");
		
		return new ImageLoader(getApplicationContext(), Long.parseLong(args.getString("Id")));
	}
	@Override
	public void onLoadFinished(Loader<ImageBean> arg0, ImageBean imageBean) {
		
		mImageBean = imageBean;
		imageIndex.setText(imageIndexString);
		
		imageId.setText(mImageBean.getImageId());
		imageName.setText(mImageBean.getImageName());
	}
	@Override
	public void onLoaderReset(Loader<ImageBean> arg0) {
		
		
	}
}

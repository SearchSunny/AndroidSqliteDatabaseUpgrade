package com.example.dbloader;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.example.androidsqlitedatabaseupgrade.R;
import com.example.db.DataBaseHelper.RunCursor;
import com.example.db.ImageBean;

/**
 * 
 * @author miaowei
 *
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class RunCursorAdapeter extends CursorAdapter {
	
	private RunCursor mRunCursor;
	
	
	public RunCursorAdapeter(Context context, Cursor cursor) {
		super(context, cursor, 0);
		mRunCursor = (RunCursor)cursor;
	}
	
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
	
		ImageBean imageBean = mRunCursor.getRun();
		TextView startTextView = (TextView)view;
		String cellText = context.getString(R.string.cell_text,imageBean.getImageName());
				
		startTextView.setText(cellText);
		
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		return inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		
	}

	
	

}

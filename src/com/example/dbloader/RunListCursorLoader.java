package com.example.dbloader;

import com.example.db.DBManager;

import android.content.Context;
import android.database.Cursor;

/**
 * 
 * @author miaowei
 *
 */
public class RunListCursorLoader extends SQLiteCursorLoader {

	public RunListCursorLoader(Context context) {
		super(context);
	}

	@Override
	protected Cursor loadCursor() {
		
		return DBManager.getInstance(getContext()).queryRuns();
	}

}

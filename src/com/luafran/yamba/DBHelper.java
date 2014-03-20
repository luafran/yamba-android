package com.luafran.yamba;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		super(context, TweetsContract.DB_NAME, null, TweetsContract.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = String.format("CREATE TABLE %s (%s int primary key, %s text, %s text, %s text)",
				TweetsContract.TABLE,
				TweetsContract.Columns.ID,
				TweetsContract.Columns.USER,
				TweetsContract.Columns.MESSAGE,
				TweetsContract.Columns.DATE);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}

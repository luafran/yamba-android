package com.luafran.yamba;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class TweetsProvider extends ContentProvider {
	
	private static final String TAG = "TweetsProvider";

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		DBHelper dbHelper = new DBHelper(getContext());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		// returns id of new record
		long id = db.insertWithOnConflict(TweetsContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		
		Log.d(TAG, "Inserted " + values.getAsLong(TweetsContract.Columns.ID));
		return Uri.parse(TweetsContract.CONTENT_URI + "/" + Long.toString(id));
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		DBHelper dbHelper = new DBHelper(getContext());
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Cursor cursor = db.query(TweetsContract.TABLE,
				projection,
				selection,
				selectionArgs,
				null,
				null,
				sortOrder==null? TweetsContract.ORDER_DEFAULT: sortOrder);
		
		return cursor;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}

package com.luafran.yamba;

import android.net.Uri;
import android.provider.BaseColumns;

// Un contrato por tabla
public class TweetsContract {
	public static final String DB_NAME = "yamba";
	public static final int DB_VERSION = 1;
	public static final String TABLE = "tweet";
	public static final String ORDER_DEFAULT = Columns.ID + " DESC";
	
	public static final String AUTHORITY = "com.luafran.yamba.provider";
	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
	
	class Columns {
		public final static String ID = BaseColumns._ID;
		public final static String USER = "user";
		public final static String MESSAGE = "message";
		public final static String DATE = "date";
	}
}
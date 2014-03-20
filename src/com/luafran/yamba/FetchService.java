package com.luafran.yamba;

import java.util.List;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClient.Status;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class FetchService extends IntentService {

	private static final String TAG = "FetchService";
	
	public FetchService() {
		super("FetchService");
	}
	
	@Override
	public void onCreate() {
		Log.d(TAG, "onCreate");
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
		super.onDestroy();
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d(TAG, "onHandleIntent");
		
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		YambaClient client = new YambaClient(preferences.getString("username", "student"), preferences.getString("password", "password"));
		
		try {
			List<Status> list = client.getTimeline(20);
			for (Status status : list) {
				Log.d(TAG, status.getUser() + ": " + status.getMessage());
				ContentValues values = new ContentValues();
				values.put(TweetsContract.Columns.ID, status.getId());
				values.put(TweetsContract.Columns.USER, status.getUser());
				values.put(TweetsContract.Columns.MESSAGE, status.getMessage());
				values.put(TweetsContract.Columns.DATE, status.getCreatedAt().toString());
				getContentResolver().insert(TweetsContract.CONTENT_URI, values);
			}
		}
		catch (YambaClientException e) {
			Log.e(TAG, e.toString());
		}
	}
}
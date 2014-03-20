package com.luafran.yamba;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// Si queremos compatibilidad con Android 2 hay que usar Fragment de la support library
public class TimelineFragment extends ListFragment{
	
	private static final String TAG = "TimelineFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView");
		
		inflater.inflate(R.layout.fragment_timeline, container);
		
		Cursor cursor = getActivity().getContentResolver().query(TweetsContract.CONTENT_URI,
				null, null, null, null);
		
		// Mapeamos los campos de la fuente a los id de los textView destino
		String [] from = {TweetsContract.Columns.USER,
				          TweetsContract.Columns.MESSAGE,
				          TweetsContract.Columns.DATE};
		
		int[] to = {R.id.lblTweetUser, R.id.lblTweetMessage, R.id.lblTweetDate};
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.list_timeline_item,
				cursor,	from, to, 0);
		
		adapter.setViewBinder(new ViewBinder() {
			
			// Se llama por cada registro
			// Si devuelve true hace el matching si devuelve false usa el default 
			@Override
			public boolean setViewValue(View view, Cursor cursor, int index) {
				
				if (view.getId() == R.id.lblTweetDate) {
					String dateStr = cursor.getString(cursor.getColumnIndex(TweetsContract.Columns.DATE));
					
					//Log.d(TAG, dateStr);
					SimpleDateFormat dateFormat = new SimpleDateFormat(
                            "EEE MMM dd HH:mm:ss Z yyyy", Locale.US);

					try {
						String result = (String) DateUtils.getRelativeTimeSpanString(dateFormat.parse(dateStr).getTime());
						((TextView) view).setText(result);
					} catch (ParseException e) {
						Log.e(TAG, e.toString());
					}
					
					return true;
				}
				else {
					return false;
				}
			}
		});
		
		Log.d(TAG, "Setting adapter");
		setListAdapter(adapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
}

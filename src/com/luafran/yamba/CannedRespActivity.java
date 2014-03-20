package com.luafran.yamba;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CannedRespActivity extends ListActivity {
	
	private static final String TAG = "CannedRespActivity";
	private String[] cannedResponses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		List<String> responses = new ArrayList<String>();
		responses.add("Si Querida");
		responses.add("No puedo");
		responses.add("Llego en 5");
		*/
		
		// Para tener los valores en un xml de recursos
		cannedResponses = getResources().getStringArray(R.array.canned_responses);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cannedResponses);
		setListAdapter(adapter);
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Log.d(TAG, "Click on " + cannedResponses[position]);
		// Aqui intent se usa para transferir datos
		Intent data = new Intent();
		data.putExtra("response", cannedResponses[position]);
		setResult(RESULT_OK, data);
		finish();
		super.onListItemClick(l, v, position, id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.canned_resp, menu);
		return true;
	}

}

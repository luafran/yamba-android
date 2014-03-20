package com.luafran.yamba;

import com.marakana.android.yamba.clientlib.YambaClient;
import com.marakana.android.yamba.clientlib.YambaClientException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewTweetActivity extends Activity {
	
	private static final String TAG = "StatusActivity";
	private static final int REQUEST_CANNED_RESPONSE = 1;
	
	private TextView lblCharCount;
	private Button btnTweet;
	private EditText editTweet;
	private int maxTweetLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        
        editTweet = (EditText) findViewById(R.id.editTweet);
        
        lblCharCount = (TextView) findViewById(R.id.lblCharCount);
        maxTweetLength = getResources().getInteger(R.integer.max_tweet_length);
        lblCharCount.setText(Integer.toString(maxTweetLength));
        
        editTweet.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				int count = s.toString().length();
				lblCharCount.setText(Integer.toString(maxTweetLength-count));
				if (count > maxTweetLength) {
					lblCharCount.setTextColor(Color.RED);
				}
				else
					lblCharCount.setTextColor(Color.BLACK);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub	
			}
		});
        
        btnTweet = (Button) findViewById(R.id.btnTweet);
        btnTweet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new TweetPostTask().execute(editTweet.getText().toString());
			}
		});
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return super.onCreateOptionsMenu(menu);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menuCannedResp:
				startActivityForResult(new Intent(this, CannedRespActivity.class), REQUEST_CANNED_RESPONSE);
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	switch(requestCode) {
    	case REQUEST_CANNED_RESPONSE:
    		
    		if (resultCode == RESULT_OK) {
    			Log.d(TAG, "Got " + data.getStringExtra("response"));
    			editTweet.setText(data.getStringExtra("response"));
    		}
    		break;
    	}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    // Llamado cuando es necesario guardar algun estado porque la activity va a ser terminada
    // Por ej cuando el dispositivo se rota. La activity en curso es terminada y lanzada nuevamente
    // para que se lean los nuevos recursos
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	outState.putInt("aux", 27);
    	Log.d(TAG, "onSaveInstanceState");
    	super.onSaveInstanceState(outState);
    }
    
    // Llamado cuando la aplicacion es restaurada luego de un cambio de estado.
    // Se ejecuta antes del onCreate!!!
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	int aux = savedInstanceState.getInt("aux");
    	Log.d(TAG, "onRestoreInstanceState, aux = " + aux);
    	super.onRestoreInstanceState(savedInstanceState);
    }
    
    class TweetPostTask extends AsyncTask<String, Void, String> {
    	
    	private static final String TAG = "TweetPostTask";
    	private ProgressDialog dialog;
    	
    	@Override
    	protected void onPreExecute() {
    		dialog = ProgressDialog.show(NewTweetActivity.this, "Sending", "Please wait");
    	}
    	
    	// Executed in new thread
		@Override
		protected String doInBackground(String... args) {
			String textToSend = args[0];
			
			
			int textLen = textToSend.length();
			Log.d(TAG, "Sending tweet [" + textToSend + "]. len = " + textLen);
			if (textLen < maxTweetLength) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(NewTweetActivity.this);
				YambaClient client = new YambaClient(preferences.getString("username", "student"), preferences.getString("password", "password"));
				try {
					client.postStatus(textToSend);
				} catch (YambaClientException e) {
					Log.e(TAG,e.toString());
					return getString(R.string.msg_error_in_communication);
				}
			}
			else
			{
				return getString(R.string.msg_max_length_exceeded);
			}
			
			return getString(R.string.msg_tweet_published);
		}
		
		// Executed in UI thread
		// received message argument is what doInBackground returns, should match type of AsyncTask 
		@Override
		protected void onPostExecute(String message) {
			dialog.cancel();
			Toast.makeText(NewTweetActivity.this, message, Toast.LENGTH_LONG).show();
			NewTweetActivity.this.editTweet.setText("");
			NewTweetActivity.this.finish();
		}
    }
}
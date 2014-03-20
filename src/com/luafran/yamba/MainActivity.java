package com.luafran.yamba;

import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

// Usamos FragmentAcitivity solo para tener compatibilidad con Android 2
public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	// Recibe un item de los que estan en menu. 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.menuNewTweet:
				startActivity(new Intent(this, NewTweetActivity.class));
				break;
			case R.id.menuFetch:
				startService(new Intent(this, FetchService.class));
				break;
			case R.id.menuAbout:
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com")));
				break;
			case R.id.menuSettings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}

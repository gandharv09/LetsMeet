package com.hackers.letsmeet;

import java.util.ArrayList;

import android.R.anim;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class FriendList extends Activity {
	ListView itcItems;
	private EditText StatusView;
	ArrayList<String> Name = new ArrayList<String>();
	ArrayList<String> Status = new ArrayList<String>();
	ArrayList<String> Lat = new ArrayList<String>();
	ArrayList<String> Lng = new ArrayList<String>();
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_listview);
		StatusView = (EditText) findViewById(R.id.status);
		itcItems = (ListView) findViewById(R.id.streamList);
		Bundle extras = getIntent().getExtras();
		
		Name = extras.getStringArrayList("name");
		Status = extras.getStringArrayList("status");
		Lat = extras.getStringArrayList("lat");
		Lng = extras.getStringArrayList("lng");

		    
		    ListAdapter adapter = new ListAdapter(this,Name,Status,Lat,Lng);
		    itcItems.setAdapter(adapter);
		   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend_list, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.meet:
            // search action
        	Meet();
            return true;
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	public void Meet()
	{
		Bundle item = new Bundle();
        item.putStringArrayList("name", (ArrayList<String>) Name);
        item.putStringArrayList("status", (ArrayList<String>) Status);
        item.putStringArrayList("lat", (ArrayList<String>) Lat);
        item.putStringArrayList("lng", (ArrayList<String>) Lng);
        Intent j = new Intent();
        j.putExtras(item);
        j.setClass(this,Map.class);
        startActivity(j);
	}
}

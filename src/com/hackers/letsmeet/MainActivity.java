package com.hackers.letsmeet;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Context context;
    static boolean value = false;
    String  accountNumber;
	public static final String PREFS_NAME = "MyPrefsFile";
	private static final int PICK_ACCOUNT_REQUEST = 0;
	SharedPreferences settings;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Button loginButton = (Button) findViewById(R.id.loginButton);
        settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
    	SharedPreferences.Editor editor = settings.edit();
    	editor.commit();
        value=false;
        context = this;
           
           
                	
    	//SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
    	//Get "hasLoggedIn" value. If the value doesn't exist yet false is returned
    	//boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

    	if(settings.contains("hasLoggedIn"))
    	{
    		
    	    //Go directly to main activity
    	loginButton.setVisibility(View.INVISIBLE);
    	 new Handler().postDelayed(new Runnable() {
             public void run() {
    	Intent j = new Intent();
        j.setClass(context, FriendList.class);
        startActivity(j);
        finish();
             }
         }, 2500);
    	}else{
    		
    		loginButton.setVisibility(View.VISIBLE);
    		
    	}
    }
          
    public void login(View v) {
    	/*
    	* If account name already stored in shared preferences, then use that.
    	* Else show account picker.
    	*/
    	
    	PickerNumber();
    	
    	}  
    public static boolean isOnline(Context c) {
		
	      ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	      NetworkInfo netInfo = conManager.getActiveNetworkInfo();
	      return ( netInfo != null && netInfo.isConnected() );
	}
            
    private void PickerNumber() {
    	Intent j = new Intent();
        j.setClass(context, Register.class);
        startActivity(j);
}}

/*
 * takes selected account data, stores email id in shared preferences and
 * calls method for taking token
 */

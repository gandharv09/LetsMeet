package com.hackers.letsmeet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {

	String accountNumber;
	SharedPreferences settings;
	Context context;
	EditText text;
	String str;
	String status;
	String lat;
	String lng;
	JSONObject js;
	ArrayList<String> name=new ArrayList<String>();
	ArrayList<String> stats=new ArrayList<String>();;
	ArrayList<String> lati=new ArrayList<String>();;
	ArrayList<String> lngi=new ArrayList<String>();;
	ArrayList<String> phone=new ArrayList<String>();;
	ContentResolver cr;
	
	public static final String PREFS_NAME = "MyPrefsFile";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		text = (EditText) findViewById(R.id.Phone);
        Button loginButton = (Button) findViewById(R.id.Submit);
        GetLocation();
        cr = this.getContentResolver();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}
	public void GetLocation()
	{
		GPSTracker gps = new GPSTracker(this);
		if(gps.canGetLocation()){
            
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            lat= String.valueOf(latitude);
            lng = String.valueOf(longitude);
            Log.d("latlong",latitude+" "+longitude);
             
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();   
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
	}
	
	public void Send(View v)
	{
		
		if(isOnline(this)){
			accountNumber = text.getText().toString();
			
			Log.d("accountnumber",accountNumber);
			 settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
            
            //editor.putString("accountName", accountNumber);
            //editor.putString("hasLoggedIn", "LoggedIn");
           
            Thread thread = new Thread()
    		{
    			@Override
    			public void run()
    			{
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.48.42/register");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("phone", accountNumber));
			//httppost.setHeader("X-FINDAWAY-KEY", "35f1c80573140143e1d338aef233e1f2");
			//JSONArray jsonAraay = new JSONArray(nameValuePairs);
			JSONObject obj = new JSONObject();
			try {
            obj.put("name", "Gandharv Kapoor");
            obj.put("phone", accountNumber);
            obj.put("status", "Hey i am using lets meet");
			
    
            GetContacts contacts = new GetContacts();
            ArrayList<String> number = contacts.getNumber(cr);
            
            JSONArray list = new JSONArray();
            for (int i = 0; i < number.size(); i++) {
            if (number.get(i).contains(",")) {
            String[] splitNumber = number.get(i).split(",");
            int j = 0;
            while (j < splitNumber.length ) {
            list.put(splitNumber[j++].toString());
            System.out.println(splitNumber[j-1].toString());
            }
            }
            else
            list.put(number.get(i));
            System.out.println("8888888888888"+number.get(i));
            }


//            JSONArray list = new JSONArray();
//            list.put("9717465621");
//            list.put("9910513758");
//            list.put("9717573277");
            
    
            obj.put("contacts", list);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				StringEntity se= new StringEntity(("JSON" + obj.toString()));
				  se.setContentEncoding((Header) new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

			        httppost.setEntity(se);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            
            HttpResponse resp;
			try {
				resp = httpclient.execute(httppost);
				HttpEntity responseEntity = resp.getEntity();
		        String s = EntityUtils.toString(responseEntity);
		        //JSONObject jo = new JSONObject(s);
		        //JSONArray jr = jo.getJSONArray("data");
		        Log.d("List response", s);
		       // s= "[{\"status\": \"I am free all the time\", \"lat\": \"28.6617\", \"name\": \"Ankur Sial\", \"long\": \"77.2108\", \"phone\": \"971\"}, {\"status\": \"I don't think\", \"lat\": \"28.6560\", \"name\": \"Gandkarv Kapoor\", \"long\": \"77.2310\", \"phone\": \"9814\"}]";
		        JSONArray array = new JSONArray(s);
	               for(int i=0;i<array.length();i++)
	               {
	                      js = (JSONObject) array.get(i);
	                      //Log.d("name", js.toString());
	                      //Log.d("name", ">......"+js.get("lat"));
	                      //Log.d("name2", js.toString());
	                      //Log.d("name",(String) js.get("name"));
	                      
	                      name.add((String) js.get("name"));
	                      stats.add((String) js.get("status"));
	                      lati.add((String) js.get("lat"));
	                      lngi.add((String) js.get("long"));
	                      phone.add((String) js.get("phone"));
	                      
//	                      System.out.println(name.toString());
//	                      System.out.println(stats.toString());
//	                      System.out.println(lati.toString());
//	                      System.out.println(lngi.toString());
//	                      System.out.println(phone.toString());
	               }
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    			}


    		};
    		thread.start();
    		try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		//final ArrayList<String> name = new ArrayList<String>(); 
          
            editor.commit();
            Bundle item = new Bundle();
            item.putStringArrayList("name", (ArrayList<String>) name);
            item.putStringArrayList("status", (ArrayList<String>) stats);
            item.putStringArrayList("lat", (ArrayList<String>) lati);
            item.putStringArrayList("lng", (ArrayList<String>) lngi);
            Intent j = new Intent();
            j.putExtras(item);
            j.setClass(this,FriendList.class);
            startActivity(j);
            finish();}
	    	else{
	    		Toast.makeText(this, "No Network Connection", Toast.LENGTH_SHORT);
	    	}
	    	}  
	    public static boolean isOnline(Context c) {
			
		      ConnectivityManager conManager = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
		      NetworkInfo netInfo = conManager.getActiveNetworkInfo();
		      return ( netInfo != null && netInfo.isConnected() );
		}

}

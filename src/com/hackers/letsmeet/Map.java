package com.hackers.letsmeet;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

public class Map extends Activity {

	ArrayList<String> Name = new ArrayList<String>();
	ArrayList<String> Status = new ArrayList<String>();
	ArrayList<String> Lat = new ArrayList<String>();
	ArrayList<String> Lng = new ArrayList<String>();
	private String[] places;

	private LocationManager locationManager;
	private Location loc;
	double latitude;
	double longitude;
	GoogleMap map ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);
		Bundle extras = getIntent().getExtras();
		Name = extras.getStringArrayList("name");
		Status = extras.getStringArrayList("status");
		Lat = extras.getStringArrayList("lat");
		Lng = extras.getStringArrayList("lng");
		try {
	        // Loading map
	        initilizeMap(Name,Status,Lat,Lng);
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		}
		
	private void initilizeMap(ArrayList<String> name2,
			ArrayList<String> status2, ArrayList<String> lat2,
			ArrayList<String> lng2) {
		// TODO Auto-generated method stub
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
		        .getMap();
		int i=0;
		double lati=0.0;
		double longi=0.0;
		for(i=0 ; i<name2.size();i++)
		{
		//String loc = cord.get(i);
		//String s[] = loc.split(",");
		lati = lati + Double.parseDouble(lat2.get(i));
		longi = longi + Double.parseDouble(lng2.get(i));
		LatLng mylocation=new LatLng(Double.parseDouble(lat2.get(i)),Double.parseDouble(lng2.get(i)));
		//Log.d("Location","Double.parseDouble(s[0]),Double.parseDouble(s[1])");
		
		Marker m = map.addMarker(new MarkerOptions().position(mylocation)
		        .title(name2.get(i)));
		m.showInfoWindow();
		}
		map.setMyLocationEnabled(true);
		GPSTracker gps = new GPSTracker(this);
	 latitude = gps.getLatitude();
     longitude = gps.getLongitude();
        LatLng me=new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(me).title("You`").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
		
        //Log.d("showing","outlet");
		
		lati = (lati+latitude)/(name2.size()+1);
		longi = (longi+longitude)/(name2.size()+1);
		latitude=lati;
		longitude=longi;
		Log.d("meet",lati+" "+longi);
		LatLng meet=new LatLng(lati,longi);
		map.addMarker(new MarkerOptions().position(meet).title("Meet Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(meet, 10));
		//places = "Resturants";
		try{
			getDataInAsyncTask();
			
			}catch(Exception e){
				Toast.makeText(this, "Slow or No Internet Connection",
                        Toast.LENGTH_LONG).show();
			}
	}

	void getDataInAsyncTask(){
		final ProgressDialog pd = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);

        pd.setTitle("Loading ...");

       pd.setCancelable(false);

        pd.setIndeterminate(true);

        pd.setMessage("Please wait while we load nearby restaurants");
			AsyncTask<Void, Void ,ArrayList<Place>> task = new AsyncTask<Void, Void, ArrayList<Place>>() {

				
                @Override
                protected void onPreExecute() {

                    pd.show();
                        
                }
                protected ArrayList<Place> doInBackground(Void... arg0) {
                		   PlacesService service = new PlacesService(
                		     "AIzaSyDcXFSC2I6ZqxQeAbUMFvJKMrA98217H9U");
                		   ArrayList<Place> findPlaces = service.findPlaces(latitude, // 28.632808
                		     longitude, "food"); // 77.218276
                		 
                		 
                		   for (int i = 0; i < findPlaces.size(); i++) {
                		 
                	    Place placeDetail = findPlaces.get(i);
                			Log.e("Places got", "places : " + placeDetail.getName());
                		   }
                		   return findPlaces;
                		  }
                
               
                public void onPostExecute(ArrayList<Place> result) {
                	
				pd.dismiss();
				Log.d("size",result.size()+"");
				for (int i = 0; i < result.size(); i++) {
					    map.addMarker(new MarkerOptions()
						      .title(result.get(i).getName())
						      .position(
						        new LatLng(result.get(i).getLatitude(), result
						          .get(i).getLongitude()))
						      .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
						      .snippet(result.get(i).getVicinity()));
						   }
						                        //CameraPosition cameraPosition = new CameraPosition.Builder()
						     //.target(new LatLng(result.get(0).getLatitude(), result
						       //.get(0).getLongitude())) // Sets the center of the map to
						           // Mountain View
						     //.zoom(14) // Sets the zoom
						     //.tilt(30) // Sets the tilt of the camera to 30 degrees
						     //.build(); // Creates a CameraPosition from the builder
						   //mMap.animateCamera(CameraUpdateFactory
						     //.newCameraPosition(cameraPosition));	
			}

			
		};
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

}

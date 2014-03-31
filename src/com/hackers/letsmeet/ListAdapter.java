package com.hackers.letsmeet;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class ListAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final ArrayList<String> Name;
	private final ArrayList<String> Status;
	private final ArrayList<String> Lat;
	private final ArrayList<String> Lng;
	
	public ListAdapter(Context context, ArrayList<String> name,ArrayList<String> status,ArrayList<String> lat,ArrayList<String> lng) {
	    super(context, R.layout.activity_friend_list, name);
	    this.context = context;
	    this.Name = name;
	    this.Status=status;
	    this.Lat=lat;
	    this.Lng=lng;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View rowView = inflater.inflate(R.layout.activity_friend_list, parent, false);
	    TextView textView1 = (TextView) rowView.findViewById(R.id.name);
	    TextView textView2 = (TextView) rowView.findViewById(R.id.status);
	    textView1.setText(Name.get(position));
	   
	    textView2.setText(Status.get(position));
	    // change the icon for Windows and iPhone
	    //String s = values[position];
	   

	    return rowView;
	  }
}
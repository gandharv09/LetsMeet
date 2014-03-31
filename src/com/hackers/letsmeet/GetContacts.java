package com.hackers.letsmeet;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;

public class GetContacts {
	private ArrayList<String> contactList;
	
	public ArrayList<String> getNumber(ContentResolver cr) {
		Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
		contactList = new ArrayList<String>();
		
		while (phones.moveToNext()) {
			String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			contactList.add(phoneNumber);
		}
		return contactList;
	}
}

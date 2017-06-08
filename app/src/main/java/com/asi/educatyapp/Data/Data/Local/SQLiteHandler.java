/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.asi.educatyapp.Data.Data.Local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 3;

	// Database Name
	private static final String DATABASE_NAME = "EducatyApp";

	// Login table name
	private static final String TABLE_USER = "user";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_UID = "uid";
	private static final String KEY_PATH = "path";
	private static final String KEY_TYPE = "type";
	private static final String KEY_SID="sid";
	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"
				+ KEY_UID + " TEXT,"
				+ KEY_PATH + " TEXT,"
				+ KEY_TYPE + " TEXT,"
				+ KEY_SID + " TEXT" + ")";
		db.execSQL(CREATE_LOGIN_TABLE);


	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addUser(String name, String email, String uid, String path,String type,String sid) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email); // Email
		values.put(KEY_UID, uid); // Email
		values.put(KEY_PATH, path); // Created At
		values.put(KEY_TYPE, type); // Created At
		values.put(KEY_SID, sid); // Created At

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection


	}

	/**
	 * Getting user data from database
	 * */
	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("uid", cursor.getString(3));
			user.put("path", cursor.getString(4));
			user.put("type", cursor.getString(5));
			user.put("sid", cursor.getString(6));
		}
		cursor.close();
		db.close();
		// return user


		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();


	}

}

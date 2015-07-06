package com.minahatami.customerlogin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	//Table Information
	public static final String TABLE_CUSTOMERS = "CustomerTable";
	public static final String COLUMN_ENTRY_ID = "customerId";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_DATEOFBIRTH = "dateOfBirth";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_IMAGE = "image";

	//Database Information
	 static final String DATABASE_NAME = "CustomersList";
	 static final int DATABASE_VERSION = 2;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + TABLE_CUSTOMERS + " (" + COLUMN_ENTRY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
				+ " TEXT NOT NULL, " + COLUMN_DATEOFBIRTH + " TEXT, "
				+ COLUMN_ADDRESS + " TEXT, " + COLUMN_IMAGE
				+ " TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMERS);
		onCreate(db);

	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}

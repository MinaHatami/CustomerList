package com.minahatami.customerlogin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public final class SQLController {

	private final String TAG = "SQLController";
	private DBHelper dbHelper;
	private final Context context;
	private SQLiteDatabase database;

	public SQLController(Context c) {
		context = c;
	}

	public SQLiteDatabase getDatabase() {
		return database;
	}

	public SQLController open() {
		dbHelper = new DBHelper(context);
		database = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public void insertData(String name, String dateOfBirth, String address,
			String image) {
		// TODO Auto-generated method stub
		ContentValues cv = new ContentValues();

		cv.put(DBHelper.COLUMN_NAME, name);
		cv.put(DBHelper.COLUMN_DATEOFBIRTH, dateOfBirth);
		cv.put(DBHelper.COLUMN_ADDRESS, address);
		cv.put(DBHelper.COLUMN_IMAGE, image);

		database.insert(DBHelper.TABLE_CUSTOMERS, null, cv);

	}

	public List<Customer> getCustomers() {
		List<Customer> customers = new ArrayList<Customer>();

		Cursor cursor = readData();
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				Customer c = new Customer(cursor.getInt(0),
						cursor.getString(1), cursor.getString(2),
						cursor.getString(3), cursor.getString(4));

				customers.add(c);
			}

			cursor.close();
		}

		return customers;
	}

	public Cursor readData() {
		Log.v(TAG, "database is null: " + (database == null));

		String[] allColumns = new String[] { DBHelper.COLUMN_ENTRY_ID,
				DBHelper.COLUMN_NAME, DBHelper.COLUMN_DATEOFBIRTH,
				DBHelper.COLUMN_ADDRESS, DBHelper.COLUMN_IMAGE };

		this.open();

		Log.v(TAG, "database is null: " + (database == null));

		Cursor c = database.query(DBHelper.TABLE_CUSTOMERS, allColumns, null,
				null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public int updateData(long memberID, String address, String imagePath) {
		ContentValues cvUpdate = new ContentValues();

		cvUpdate.put(DBHelper.COLUMN_ADDRESS, address);
		cvUpdate.put(DBHelper.COLUMN_IMAGE, imagePath);
		int i = database.update(DBHelper.TABLE_CUSTOMERS, cvUpdate,
				DBHelper.COLUMN_ENTRY_ID + " = " + memberID, null);
		return i;
	}

	public void deleteData(long memberID) {
		database.delete(DBHelper.TABLE_CUSTOMERS, DBHelper.COLUMN_ENTRY_ID
				+ "=" + memberID, null);
	}

	public boolean verification(String name, String dateOfBirth)
			throws SQLException {
		int count = -1;
		Cursor c = null;
		try {
			String query = "SELECT COUNT(*) FROM " + DBHelper.TABLE_CUSTOMERS
					+ " WHERE " + DBHelper.COLUMN_NAME + " = ? AND "
					+ DBHelper.COLUMN_DATEOFBIRTH + " = ?";
			c = database.rawQuery(query, new String[] { name, dateOfBirth });
			if (c.moveToFirst()) {
				count = c.getInt(0);
			}
			return count > 0;
		} finally {
			if (c != null) {
				c.close();
			}
		}
	}
}

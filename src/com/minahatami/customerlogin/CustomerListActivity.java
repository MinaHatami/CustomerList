package com.minahatami.customerlogin;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CustomerListActivity extends Activity {
	ListView customerList;
	private List<Customer> customers;
	ArrayAdapter<Customer> adapter;
	SQLController sqlController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer_list);

		sqlController = new SQLController(this);
		customers = sqlController.getCustomers();
		customerList = (ListView) findViewById(R.id.listView1);
		adapter = new MyArrayAdapter(this, customers);

		/*
		 * // When clicked on an item view
		 * customerList.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) {
		 * 
		 * Intent intent = new Intent(parent.getContext(),
		 * PictureViewActivity.class); // putExtra adds extended data to the
		 * intent. // "path" is a key which will be used by PictureViewActivity
		 * // class to get the path of the file intent.putExtra("path",
		 * customers.get(position).getImage()); startActivity(intent);
		 * 
		 * } });
		 */

		// When long-clicked on an item view
		customerList.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.d("Long Click", "USED!");
				return false;
			}
		});

		customerList.setAdapter(adapter);
		registerForContextMenu(customerList);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		Log.d("Menu", "Menu");
		menu.setHeaderTitle("Choose Action!");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.view:
			Log.d("getItemID", "view");

			Intent i = new Intent(this, PictureViewActivity.class);
			// ???? Revise this
			i.putExtra("name", adapter.getItem(info.position).getName());
			i.putExtra("dateOfBirth", adapter.getItem(info.position)
					.getDateOfBirth());
			i.putExtra("path", adapter.getItem(info.position).getImage());
			i.putExtra("address", adapter.getItem(info.position).getAddress());
			startActivity(i);

			return true;

		case R.id.edit:
			Log.d("getItemID", "edit");

			Intent intent = new Intent(this, EditCustomerActivity.class);
			intent.putExtra("memberID", adapter.getItem(info.position).getId());
			startActivity(intent);
			
			// ?????? Update adapter after update SQLite-----> onContentChanged()
			adapter.notifyDataSetChanged();

			return true;

		case R.id.delete:
			Log.d("getItemID", "delete");
			
			int customerId = adapter.getItem(info.position).getId();
			sqlController.deleteData(customerId);
			// sqlController.close();

			adapter.remove(adapter.getItem(info.position));
			adapter.notifyDataSetChanged();

			return true;

		default:
			return super.onContextItemSelected(item);
		}

	}

}

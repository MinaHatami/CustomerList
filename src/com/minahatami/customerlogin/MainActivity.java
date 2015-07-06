package com.minahatami.customerlogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	private String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void buttonClick(View view) {

		Log.v(TAG, "Id: " + view.getId());

		// get the id of the view clicked. (in this case button)
		switch (view.getId()) {
		case R.id.btAddCustomer:
			Intent intent = new Intent(this, AddCustomerActivity.class);
			startActivity(intent);
			break;

		case R.id.btCustomerList:
			Intent intent1 = new Intent(this, CustomerListActivity.class);
			startActivity(intent1);
			break;

		case R.id.btChangePass:
			Intent intent2 = new Intent(this, ChangePasswordActivity.class);
			startActivity(intent2);
			break;
			
		default:
			return;
		}
	}
}

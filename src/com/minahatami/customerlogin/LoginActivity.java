package com.minahatami.customerlogin;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	//private static final String KEY_NAME = "name";
	//private static final String KEY_PASSWORD = "password";
	EditText etUsername, etPassword;

	private final String TAG = "LOGIN";
	private String MY_PREFS_NAME = "MyPrefsFile";
	SharedPreferenceHelper sharedPrefsHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);

		// creating an shared Preference file for the information to be stored
		// first argument is the name of file and second is the mode, 0 is
		// private mode
		sharedPrefsHelper = new SharedPreferenceHelper(this, MY_PREFS_NAME);

	}

	public void createAccountClick(View v) {
		// Get username, password from EditText
		String username = etUsername.getEditableText().toString();
		String password = etPassword.getEditableText().toString();
		

		boolean result = validateInput(username, password);
		Log.d("Result", "Result is : " + result);

		if (result) {

			HashMap<String, String> map = new HashMap<String, String>();
			map.put(username, password);
			//map.put(KEY_PASSWORD, password);
			sharedPrefsHelper.save(map);

			Toast.makeText(this, "User Created!", Toast.LENGTH_SHORT).show();
			startMainActivity();
		}
	}

	public void loginButtonClick(View view) {
		// Get username, password from EditText
		String username = etUsername.getEditableText().toString();
		String password = etPassword.getEditableText().toString();

		// Validate if username, password is filled
		if (username.trim().length() > 0 && password.trim().length() > 0) {
			String uName = null;
			String uPass = null;

			// If sharedPrefs contains username:
			
			uPass = sharedPrefsHelper.getString(username);
				//uPass = sharedPrefsHelper.getString(KEY_PASSWORD);
			
			// Check if username and password matches
			if (password.equals(uPass)) {

				// Starting MainActivity
				Intent i = new Intent(getApplicationContext(),
						MainActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				// Add new Flag to start new Activity
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);

				//TODO: Should we call finish()????
				finish();

			} else {

				// username / password doesn't match&
				Toast.makeText(getApplicationContext(),
						"Username/Password is incorrect", Toast.LENGTH_LONG)
						.show();
			}
		} else {

			// user didn't entered username or password
			Toast.makeText(getApplicationContext(),
					"Please enter username and password", Toast.LENGTH_LONG)
					.show();
		}
	}

	private boolean validateInput(String uName, String pass) {
		if (uName.length() < 3) {
			Log.d(TAG, "username length: " + uName.length());
			Log.d(TAG, "Username is : " + uName);
			Toast.makeText(this,
					"Username is too short! Enter at least 3 letters.",
					Toast.LENGTH_LONG).show();
			return false;
		} else if (pass.length() < 4 || !pass.matches("^[a-z0-9]*$")) {
			Log.d(TAG, "password length" + pass.length());
			Toast.makeText(
					this,
					"Password is too short! It also must contain letters and numbers.",
					Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	private void startMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}
}

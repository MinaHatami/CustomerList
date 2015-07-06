package com.minahatami.customerlogin;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity {
	// private static final String KEY_NAME = "name";
	// private static final String KEY_PASSWORD = "password";
	private String MY_PREFS_NAME = "MyPrefsFile";
	EditText etUsernamRequired,etOldPass, etChangePass1, etChangePass2;
	SharedPreferenceHelper sharedPrefshelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);

		etUsernamRequired = (EditText) findViewById(R.id.etUsernamRequired);
		etOldPass = (EditText) findViewById(R.id.etOldPass);
		etChangePass1 = (EditText) findViewById(R.id.etChangePass1);
		etChangePass2 = (EditText) findViewById(R.id.etChangePass2);

		sharedPrefshelper = new SharedPreferenceHelper(this, MY_PREFS_NAME);
	}

	public void btChangePassClick(View v) {
		String username = etUsernamRequired.getEditableText().toString();
		String oldPass = etOldPass.getEditableText().toString();
		String pass1 = etChangePass1.getEditableText().toString();
		String pass2 = etChangePass2.getEditableText().toString();
		
		
		if (username.length() == 0 || oldPass.length() == 0
				|| pass1.length() == 0) {
			Toast.makeText(this, "Username and password cannot be empty!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (pass1.length() < 4 || !pass1.matches("^[a-z0-9]*$")) {
			Log.d("passwordValidation", "password length" + pass1.length());
			Toast.makeText(
					this,
					"Password is too short! It also must contain letters and numbers.",
					Toast.LENGTH_LONG).show();
			return;
		}
		
		if (!pass1.equals(pass2)) {
			Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT)
					.show();
			return;
		}

		String uPass = sharedPrefshelper.getString(username);

		if (oldPass.equals(uPass)) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(username, pass1);
			sharedPrefshelper.save(map);
			
			Log.v("oldPass", "Old pass is: " + uPass + " or " + oldPass);
			Log.v("newPass", "New pass is: " + pass1);
			
			Toast.makeText(this, "Password Successfully Changed!",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Wrong credentials!", Toast.LENGTH_SHORT)
					.show();
		}
	}

}

package com.minahatami.customerlogin;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
	SharedPreferences sharedPrefs;
	private Context mContext;

	public SharedPreferenceHelper(Context context, String prefFileName) {
		mContext = context;
		sharedPrefs = context.getSharedPreferences(prefFileName,
				Context.MODE_PRIVATE);
	}

	public String getString(String key) {
		return sharedPrefs.getString(key, "");
	}

	public void save(HashMap<String, String> map) {
		SharedPreferences.Editor editor = sharedPrefs.edit();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			editor.putString(key, value);
		}
		editor.commit();
	}
}

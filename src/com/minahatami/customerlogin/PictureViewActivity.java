package com.minahatami.customerlogin;

import java.io.File;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureViewActivity extends Activity {
	TextView viewName, viewBirthday, viewAddress;
	ImageView imgViewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_view);

		viewName = (TextView) findViewById(R.id.viewName);
		viewBirthday = (TextView) findViewById(R.id.viewBirthday);
		viewAddress = (TextView) findViewById(R.id.viewAddress);
		imgViewItem = (ImageView) findViewById(R.id.imgViewItem);

		// "path" is a key from putExtra() method
		String name = this.getIntent().getStringExtra("name");
		String dateOfBirth = this.getIntent().getStringExtra("dateOfBirth");
		String address = this.getIntent().getStringExtra("address");
		String path = this.getIntent().getStringExtra("path");

		viewName.setText(name);
		viewBirthday.setText(dateOfBirth);
		viewAddress.setText(address);

		File imgFile = new File(path);

		if (imgFile.exists()) {

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());

			imgViewItem.setImageBitmap(myBitmap);

		}

	}
}

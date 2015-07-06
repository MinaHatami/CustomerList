package com.minahatami.customerlogin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditCustomerActivity extends Activity {

	private final int SELECT_PHOTO = 1;

	ImageView newImageView;
	EditText etNewAddress;
	String newAddress, newImage;
	private String imagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_customer);

		newImageView = (ImageView) findViewById(R.id.newImage);
		etNewAddress = (EditText) findViewById(R.id.etNewAddress);
	}

	public void newImagePickerClick(View view) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, SELECT_PHOTO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK
				&& data != null) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			imagePath = cursor.getString(columnIndex);
			cursor.close();

			// here you can call createImageThumbnail method passing
			// (picturePath,480,800)
			// and set the received bitmap imageview directly instead of storing
			// in bitmap.
			// eg. imageView.setImageBitmap(createImageThumbnail( picturePath,
			// 480, 800));
			// imageView.setImageBitmap(BitmapFactory.decodeFile(imagePath));

			Bitmap bitmap = BitmapFactory.decodeFile(imagePath,
					new BitmapFactory.Options());
			newImageView.setImageBitmap(bitmap);
		}
	}
/*
	Bitmap createImageThumbnail(String imagePath, int width, int height) {
		Bitmap bitmap = null;
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		bmpFactoryOptions.inJustDecodeBounds = true;
		int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight
				/ (float) height);
		int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth
				/ (float) width);

		if (heightRatio > 1 || widthRatio > 1) {
			if (heightRatio > widthRatio) {
				bmpFactoryOptions.inSampleSize = heightRatio;
			} else {
				bmpFactoryOptions.inSampleSize = widthRatio;
			}
		}
		bmpFactoryOptions.inJustDecodeBounds = false;

		bitmap = BitmapFactory.decodeFile(imagePath, bmpFactoryOptions);
		return bitmap;
	}*/

	public void editCustomerClick(View view) {
		newAddress = etNewAddress.getEditableText().toString();

		int memberID = getIntent().getExtras().getInt("memberID");
		Log.d("MainActivity", "memberID is : " + memberID);

		SQLController db = new SQLController(this);
		db.open();
		if (db.updateData(memberID, newAddress, imagePath) > 0) {
			Toast.makeText(this, "Successfully Edited!", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(this, "Unsuccessful!", Toast.LENGTH_SHORT).show();
		}
		db.close();
	}

}

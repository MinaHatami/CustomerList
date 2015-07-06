package com.minahatami.customerlogin;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<Customer> {
	private static final String TAG = "MyArrayAdapter";
	private Context mContext;
	private List<Customer> customers;
	private final int THUMBSIZE = 50;

	public MyArrayAdapter(Context context, List<Customer> customers) {
		super(context, R.layout.activity_customerlist_items, customers);

		this.mContext = context;
		this.customers = customers;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		View view = convertView;

		if (view == null) {

			// inflate the layout
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			view = inflater.inflate(R.layout.activity_customerlist_items,
					parent, false);

			// well set up the ViewHolder
			viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view.findViewById(R.id.tvName);
			viewHolder.dateOfBirth = (TextView) view
					.findViewById(R.id.tvDateOfBirth);
			viewHolder.address = (TextView) view.findViewById(R.id.tvAddress);
			viewHolder.image = (ImageView) view.findViewById(R.id.imgViewItem);

			// store the holder with the view.
			view.setTag(viewHolder);

		} else {
			// we've just avoided calling findViewById() on resource every time
			// just use the viewHolder
			viewHolder = (ViewHolder) view.getTag();
		}

		Customer currentCustomer = customers.get(position);

		/*
		 * Log.v(TAG, "view is null: " + (view == null)); Log.v(TAG,
		 * "tvName is null: " + (view.findViewById(R.id.tvName) == null));
		 */

		viewHolder.name.setText(currentCustomer.getName());
		viewHolder.dateOfBirth.setText(currentCustomer.getDateOfBirth());
		viewHolder.address.setText(currentCustomer.getAddress());
		
		// Log.v(TAG, "getName: " + currentCustomer.getName());
		// Log.v(TAG, "getDateOfBirth: " + currentCustomer.getDateOfBirth());
		// Log.v(TAG, "getImage: " + currentCustomer.getImage());

		Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(
				getBitmap(currentCustomer.getImage()), THUMBSIZE, THUMBSIZE);
		viewHolder.image.setImageBitmap(ThumbImage);
		// setPic( , viewHolder.image);

		return view;
	}
	
	// Avoid running out of memory exception.
		public void setPic(String mCurrentPhotoPath, ImageView mImageView) {
			// Get the dimensions of the View
			int targetW = mImageView.getWidth();
			int targetH = mImageView.getHeight();

			// Get the dimensions of the bitmap
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			int photoW = bmOptions.outWidth;
			int photoH = bmOptions.outHeight;

			// Determine how much to scale down the image
			int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

			// Decode the image file into a Bitmap sized to fill the View
			bmOptions.inJustDecodeBounds = false;
			bmOptions.inSampleSize = scaleFactor;
			bmOptions.inPurgeable = true;

			Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
			mImageView.setImageBitmap(bitmap);
		}

		

	private Bitmap getBitmap(String name) {
		// TODO Auto-generated method stub
		return BitmapFactory.decodeFile(name);
	}

	private static class ViewHolder {
		TextView name, dateOfBirth, address, imagePath;
		ImageView image;
	}
}

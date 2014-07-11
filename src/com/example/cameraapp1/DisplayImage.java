package com.example.cameraapp1;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ImageView;

public class DisplayImage extends ActionBarActivity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_image);
		
		byte[] imageByte = getIntent().getExtras().getByteArray("image_f");
		Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
		
		ImageView imageDisplay = (ImageView) findViewById(R.id.cameraImage);
		imageDisplay.setImageBitmap(imageBitmap); 	
		Log.d("DisplayImage","Got image with size: "+ imageBitmap.getByteCount());

	}

}

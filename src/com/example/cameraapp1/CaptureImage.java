package com.example.cameraapp1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CaptureImage extends ActionBarActivity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
	private static final String TAG = "CaptureImage";
	String currentPhotoPath;
	Button clickBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capture_image);

		clickBtn = (Button) findViewById(R.id.takepic);

		OnClickListener clickListner = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (v.getId() == R.id.takepic)
					takePic();
			}
		};
		clickBtn.setOnClickListener(clickListner);

	}

	private void takePic() {
		// TODO Auto-generated method stub
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (cameraIntent.resolveActivity(getPackageManager()) != null) {
			File pathToSave = null;
			try {
				pathToSave = createImageFile();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			if (pathToSave != null)
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(pathToSave));
			startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_CANCELED)
			return;

		switch (requestCode) {
		case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
			galleryAddPic();
//			Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
//			if (cameraImage == null)
//				Log.d("TafterImage","no camera image");
//			Intent startDisplayActivity = new Intent(this, DisplayImage.class);
//
//			ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
//			cameraImage.compress(Bitmap.CompressFormat.PNG, 100, imageStream);
//			byte[] imageByte = imageStream.toByteArray();
//
//			startDisplayActivity.putExtra("image_f", imageByte);
//
//			startActivity(startDisplayActivity);
			break;
		default:

		}

	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("ddMMyy_HHmmss")
				.format(new Date());
		String imageFileName = "JPG_" + timeStamp + "_";
		File storageDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, ".jpg", storageDir);

		currentPhotoPath = "file:" + image.getAbsolutePath();
		Log.d("createFile", "file path of photo is:" + currentPhotoPath);
		return image;

	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File f = new File(currentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);

	}

}

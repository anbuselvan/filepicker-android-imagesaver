package com.example.imagesaver;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.filepicker.FilePicker;
import io.filepicker.FilePickerAPI;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private Uri myUrl = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		FilePickerAPI.setKey("ATS9mIwS_QfqEWNXOosSkz");
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*").addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, 21);
			}
		});
        
        button2.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(FilePicker.SAVE_CONTENT, myUrl, MainActivity.this, FilePicker.class);
				intent.putExtra("extension", ".jpg");
				startActivityForResult(intent, 22);
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 21 && resultCode == RESULT_OK) {
			myUrl = data.getData();
			ImageView imgv = (ImageView) findViewById(R.id.imageView1);
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), myUrl);
				int MAXSIZE = 2048;
				if (bitmap.getHeight() > MAXSIZE || bitmap.getWidth() > MAXSIZE) {
					//scale
					int h = bitmap.getHeight();
					int w = bitmap.getWidth();
					h = h * Math.min(w, MAXSIZE) / w;
					w = Math.min(w, MAXSIZE);
					w = w * Math.min(h, MAXSIZE) / h;
					h = Math.min(h, MAXSIZE);
					bitmap = Bitmap.createScaledBitmap(bitmap, w, h, true);
				}
				imgv.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (requestCode == 22 && resultCode == RESULT_OK) {
			Toast.makeText(this, "Success!", 500).show();
		}
	}
}

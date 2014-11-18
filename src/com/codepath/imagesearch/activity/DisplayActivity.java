package com.codepath.imagesearch.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.model.GoogleImage;
import com.squareup.picasso.Picasso;

public class DisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_display);
		this.getActionBar().hide();
		GoogleImage image = (GoogleImage) this.getIntent()
				.getSerializableExtra("entity");
		ImageView ivDisplay = (ImageView) this.findViewById(R.id.ivDisplay);
		Picasso.with(this).load(image.getFullURL()).into(ivDisplay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.display, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

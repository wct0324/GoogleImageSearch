package com.codepath.imagesearch.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.codepath.imagesearch.R;

public class ConfigActivity extends Activity {
	private SharedPreferences spConfig;
	private EditText etFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_config);

		String key = this.getResources().getString(R.string.config_key);
		this.spConfig = this.getSharedPreferences(key, MODE_PRIVATE);

		this.attachAdapterToSpinner(R.id.spSize, R.array.size_array,
				this.spConfig.getInt("size", 0));
		this.attachAdapterToSpinner(R.id.spColor, R.array.color_array,
				this.spConfig.getInt("color", 0));
		this.attachAdapterToSpinner(R.id.spType, R.array.type_array,
				this.spConfig.getInt("type", 0));

		this.etFilter = (EditText) this.findViewById(R.id.etFilter);
		this.etFilter.setText(this.spConfig.getString("filter", ""));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.config, menu);
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

	public void onSaveConfig(View v) {
		String key = this.getResources().getString(R.string.config_key);

		this.spConfig = this.getSharedPreferences(key, MODE_PRIVATE);
		this.etFilter = (EditText) this.findViewById(R.id.etFilter);

		Editor editor = this.spConfig.edit();
		editor.putInt("size", this.getSelectedIndex(R.id.spSize));
		editor.putInt("color", this.getSelectedIndex(R.id.spColor));
		editor.putInt("type", this.getSelectedIndex(R.id.spType));
		editor.putString("filter", this.etFilter.getText().toString());
		editor.commit();

		Intent i = new Intent(ConfigActivity.this, SearchActivity.class);
		this.startActivity(i);
	}

	private void attachAdapterToSpinner(int spinnerID, int resourceID, int index) {
		Spinner spinner = (Spinner) this.findViewById(spinnerID);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, resourceID, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(index);
	}

	private int getSelectedIndex(int spinnerID) {
		Spinner spinner = (Spinner) this.findViewById(spinnerID);
		return spinner.getSelectedItemPosition();
	}
}

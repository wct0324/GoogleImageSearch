package com.codepath.imagesearch.activity;

import java.util.ArrayList;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.adapter.ImageAdapter;
import com.codepath.imagesearch.listener.EndlessScrollListener;
import com.codepath.imagesearch.model.GoogleImage;
import com.codepath.imagesearch.utility.URLFormat;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	private ArrayList<GoogleImage> imageList;
	private EditText etQuery;
	private GridView gvResults;
	private ImageAdapter imgAdapter;

	private String apiVersion = "1.0";
	private String apiQueryString = "";
	private String apiCount = "8";
	private String apiSize = "";
	private String apiColor = "";
	private String apiType = "";
	private String apiFilter = "";
	private String apiStart = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_search);
		this.setUpView();
		this.imageList = new ArrayList<GoogleImage>();
		this.imgAdapter = new ImageAdapter(this, this.imageList);
		this.gvResults.setAdapter(this.imgAdapter);

		this.gvResults.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				customLoadMoreDataFromApi(page);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		this.getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.menuConfig:
			Intent i = new Intent(SearchActivity.this, ConfigActivity.class);
			this.startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	public void onImageSearch(View v) {
		this.search(0);
	}

	private void setUpView() {
		this.etQuery = (EditText) this.findViewById(R.id.etQuery);
		this.gvResults = (GridView) this.findViewById(R.id.gvResults);

		this.gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(SearchActivity.this,
						DisplayActivity.class);
				GoogleImage image = imageList.get(position);
				i.putExtra("entity", image);
				startActivity(i);
			}
		});
	}

	private void search(int page) {
		this.apiQueryString = this.etQuery.getText().toString();
		this.apiStart = String.valueOf(page * 8);
		this.getParams();

		String queryURL = URLFormat.getURL(this.apiVersion,
				this.apiQueryString, this.apiCount, this.apiSize,
				this.apiColor, this.apiType, this.apiFilter, this.apiStart);

		AsyncHttpClient client = new AsyncHttpClient();
		client.get(queryURL, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				JSONArray jArray = null;

				try {
					jArray = response.getJSONObject("responseData")
							.getJSONArray("results");

					if (Integer.valueOf(apiStart) == 0) {
						imageList.clear();
					}

					imgAdapter.addAll(GoogleImage.fromJSONArray(jArray));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				super.onFailure(statusCode, headers, responseString, throwable);
			}
		});
	}

	private void getParams() {
		String key = this.getResources().getString(R.string.config_key);
		String[] array;
		SharedPreferences spConfig = this.getSharedPreferences(key,
				MODE_PRIVATE);

		if (spConfig.getInt("size", 0) != 0) {
			array = this.getResources().getStringArray(R.array.size_array);
			this.apiSize = array[spConfig.getInt("size", 0)];
		}

		if (spConfig.getInt("color", 0) != 0) {
			array = this.getResources().getStringArray(R.array.color_array);
			this.apiColor = array[spConfig.getInt("color", 0)];
		}

		if (spConfig.getInt("type", 0) != 0) {
			array = this.getResources().getStringArray(R.array.type_array);
			this.apiType = array[spConfig.getInt("type", 0)];
		}

		if (!spConfig.getString("filter", "").equals("")) {
			this.apiFilter = spConfig.getString("filter", "");
		}
	}

	private void customLoadMoreDataFromApi(int page) {
		this.search(page);
	}
}

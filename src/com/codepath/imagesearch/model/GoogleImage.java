package com.codepath.imagesearch.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleImage implements Serializable {

	private static final long serialVersionUID = 4435069406715605571L;

	private int height;
	private int width;

	private String fullURL;
	private String thumbURL;
	private String title;

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getFullURL() {
		return fullURL;
	}

	public String getThumbURL() {
		return thumbURL;
	}

	public String getTitle() {
		return title;
	}

	public static ArrayList<GoogleImage> fromJSONArray(JSONArray jArray) {
		ArrayList<GoogleImage> imageList = new ArrayList<GoogleImage>();

		for (int i = 0; i < jArray.length(); i++) {
			try {
				imageList.add(new GoogleImage(jArray.getJSONObject(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return imageList;
	}

	public GoogleImage(JSONObject jObject) {
		try {
			this.height = jObject.getInt("height");
			this.width = jObject.getInt("width");
			this.fullURL = jObject.getString("url");
			this.thumbURL = jObject.getString("tbUrl");
			this.title = jObject.getString("title");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

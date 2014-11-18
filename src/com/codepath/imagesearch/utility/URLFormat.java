package com.codepath.imagesearch.utility;

public class URLFormat {

	private static final String apiURL = "http://ajax.googleapis.com/ajax/services/search/images?";

	public static String getURL(String version, String queryString,
			String count, String size, String color, String type,
			String filter, String start) {
		StringBuffer sb = new StringBuffer(apiURL);
		sb.append("v=" + version);
		sb.append("&q=" + queryString);
		sb.append("&rsz=" + count);
		sb.append("&start=" + start);

		if (size.length() > 0) {
			sb.append("&imgsz=" + size);
		}

		if (color.length() > 0) {
			sb.append("&imgcolor=" + color);
		}

		if (type.length() > 0) {
			sb.append("&imgtype=" + type);
		}

		if (filter.length() > 0) {
			sb.append("&as_sitesearch=" + filter);
		}

		return sb.toString();
	}

}

package com.codepath.imagesearch.adapter;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.imagesearch.R;
import com.codepath.imagesearch.model.GoogleImage;
import com.squareup.picasso.Picasso;

public class ImageAdapter extends ArrayAdapter<GoogleImage> {

	public ImageAdapter(Context context, List<GoogleImage> imageList) {
		super(context, R.layout.image_grid, imageList);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		GoogleImage image = this.getItem(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(
					R.layout.image_grid, parent, false);
		}

		ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
		ivImage.setImageResource(0);
		Picasso.with(getContext()).load(image.getFullURL()).into(ivImage);

		TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
		tvTitle.setText(Html.fromHtml(image.getTitle()));

		return convertView;
	}
}

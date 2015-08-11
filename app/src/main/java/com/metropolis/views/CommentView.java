package com.metropolis.views;

import com.metropolis.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommentView extends LinearLayout {

	TextView name;
	TextView timestamp;
	TextView comment;

	public CommentView(Context context) {
		super(context);
		inflateLayout(context);
	}

	public void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.comment_view, this);
		this.name = (TextView) view.findViewById(R.id.commentName);
		this.timestamp = (TextView) view.findViewById(R.id.CommentTimestamp);
		this.comment = (TextView) view.findViewById(R.id.txtComment);
	}
	
	public void setData(String name, String timestamp, String message) {
		this.name.setText(name);
		this.timestamp.setText(timestamp);
		this.comment.setText(message);
	}
}

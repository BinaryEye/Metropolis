package com.metropolis.views;

import com.metropolis.R;
import com.metropolis.sideMenu.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FollowerView extends LinearLayout {

	TextView username;
	LinearLayout followerViewLayout;

	public FollowerView(Context context) {
		super(context);
		inflateLayout(context);

	}

	public void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.follower_view, this);
		this.username = (TextView) view.findViewById(R.id.followerName);
		followerViewLayout = (LinearLayout) view.findViewById(R.id.followerViewLayout);
		followerViewLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getContext(),MainActivity.class);
				intent.putExtra("username", username.getText().toString());
				getContext().startActivity(intent);
			}
		});
	}

	public void setData(String username) {
		this.username.setText(username);
	}
}

package com.metropolis.views;

import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.PostRequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostView extends LinearLayout {

	TextView name;
	TextView timestamp;
	TextView message;
	Button reportButton;
	int postId= 0;

	public PostView(Context context) {
		super(context);
		inflateLayout(context);
	}

	public void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.post_view, this);
		this.name = (TextView) view.findViewById(R.id.name);
		this.timestamp = (TextView) view.findViewById(R.id.timestamp);
		this.message = (TextView) view.findViewById(R.id.txtStatusMsg);
		this.reportButton = (Button) view.findViewById(R.id.reportButton);
		reportButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				PostRequest post = new PostRequest(config.LOCAL_HOST_URL + "/api/user_posts/report"){
					protected void onPostExecute(String result) {
						if(getStatusCode() == 200){
							Toast.makeText(getContext(), "Reported!", Toast.LENGTH_LONG).show();
						}
						else
							Toast.makeText(getContext(), "Failed!", Toast.LENGTH_LONG).show();
					};
				};
				JSONObject json = new JSONObject();
				try {
					json.put("id", postId);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				post.setBody(json);
				post.execute();
			}
		});
	}

	public void setData(String name, String timestamp, String message, int postId) {
		this.name.setText(name);
		this.timestamp.setText(timestamp);
		this.message.setText(message);
		this.postId = postId;
	}
}

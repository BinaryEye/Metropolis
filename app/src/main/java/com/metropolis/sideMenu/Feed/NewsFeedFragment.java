package com.metropolis.sideMenu.Feed;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.GetRequest;
import com.metropolis.requestClasses.PostRequest;
import com.metropolis.views.PostView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class NewsFeedFragment extends Fragment {

	View rootView;
	LinearLayout layout;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_news_feed, container,
				false);
		layout = (LinearLayout) rootView.findViewById(R.id.newsFeedContainer);
		// getDataFromServer();
		return rootView;
	}

	public void getDataFromServer() {
		PostRequest postDataRequest = new PostRequest(config.LOCAL_HOST_URL
				+ "/api/user_posts/index") {
			protected void onPostExecute(String response) {
				if (this.getStatusCode() == 200) {
					setData(response);
				} else {
					Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT)
							.show();
				}
			}
		};
		JSONObject json = new JSONObject();
		try {
			json.put("user_id",
					getActivity().getPreferences(getActivity().MODE_PRIVATE)
							.getInt("userId", 0));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postDataRequest.setBody(json);
		postDataRequest.execute();
	}

	public void setData(String response) {
		try {
			JSONObject json = new JSONObject(response);
			JSONArray posts = json.getJSONArray("posts");
			for (int i = 0; i < posts.length(); i++) {
				JSONObject post = posts.getJSONObject(i);
				PostView view = new PostView(getActivity());
				view.setData(
						getActivity()
								.getPreferences(getActivity().MODE_PRIVATE)
								.getString("username", null), post
								.getString("created_at"), post
								.getString("text"), post.getInt("id"));
				layout.addView(view);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		layout.removeAllViewsInLayout();
		getDataFromServer();
	}
}
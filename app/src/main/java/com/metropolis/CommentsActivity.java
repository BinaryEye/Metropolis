package com.metropolis;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.metropolis.config.config;
import com.metropolis.requestClasses.GetRequest;
import com.metropolis.views.CommentView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CommentsActivity extends Activity {

	LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comments);
		layout = (LinearLayout) findViewById(R.id.commentsContainer);
		getDataFromServer();
	}

	private void getDataFromServer() {
		GetRequest postDataRequest = new GetRequest(config.APIRARY_URL
				+ "/metropolis/123/123/comments") {
			protected void onPostExecute(String response) {
				if (this.getStatusCode() == 200) {
					setData(response);
				} else {
					Toast.makeText(CommentsActivity.this,
							"Check Your Connection", Toast.LENGTH_SHORT).show();
				}
			}
		};
		postDataRequest.execute();
	}

	public void setData(String reponse) {
		try {
			JSONObject json = new JSONObject(reponse);
			JSONArray post = json.getJSONArray("post");
			JSONArray comments = json.getJSONArray("comments");
			TextView posterName = (TextView) findViewById(R.id.name);
			TextView postTimestamp = (TextView) findViewById(R.id.timestamp);
			TextView postMessage = (TextView) findViewById(R.id.txtStatusMsg);
			posterName.setText(post.getJSONObject(0).getString("username"));
			postTimestamp.setText(post.getJSONObject(0).getString("date"));
			postMessage.setText(post.getJSONObject(0).getString("message"));

			for (int i = 0; i < comments.length(); i++) {
				JSONObject comment = comments.getJSONObject(i);
				CommentView view = new CommentView(this);
				view.setData(comment.getString("username"),
						comment.getString("timestamp"),
						comment.getString("comment"));
				layout.addView(view);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}

package com.metropolis;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.PostRequest;
import com.metropolis.sideMenu.MainActivity;

public class AuthActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auth);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		LoginFragment lf = new LoginFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction().add(R.id.authContainer, lf);
		transaction.commit();
	}

	public void switchFragments(View v) {
		Button b = (Button) v;
		if (b.getId() == R.id.loginRegister) {
			RegisterFragment rf = new RegisterFragment();
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction().replace(R.id.authContainer, rf);
			transaction.commit();
		} else {
			LoginFragment lf = new LoginFragment();
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction().replace(R.id.authContainer, lf);
			transaction.commit();
		}

	}

	public void login(View v) {
		EditText uname = (EditText) findViewById(R.id.loginUsername);
		EditText pass = (EditText) findViewById(R.id.loginPassword);
		String username = uname.getText().toString();
		String password = pass.getText().toString();
		PostRequest loginRequest = new PostRequest(config.LOCAL_HOST_URL
				+ "/api/user/login") {
			protected void onPostExecute(String response) {
				if (this.getStatusCode() == 200) {
					Toast.makeText(getBaseContext(), "Login Successful",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(getBaseContext(),MainActivity.class);
					intent.putExtra("response", response);
					intent.putExtra("type",1);
					startActivity(intent);
				} else {
					if (this.getStatusCode() == 404) {
						Toast.makeText(getBaseContext(), "Wrong Credentials",
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getBaseContext(), "Connection Error",
								Toast.LENGTH_LONG).show();
					}
				}
			}
		};
		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginRequest.setBody(json);
		loginRequest.execute();
	}

	public void register(View v) {
		String username = ((EditText) findViewById(R.id.registerUsername))
				.getText().toString();
		String password = ((EditText) findViewById(R.id.registerPassword))
				.getText().toString();
		String email = ((EditText) findViewById(R.id.registerEmail)).getText()
				.toString();
		PostRequest registerRequest = new PostRequest(config.LOCAL_HOST_URL
				+ "/api/user/create") {
			@Override
			protected void onPostExecute(String response) {
				if (getStatusCode() == 200)
					Toast.makeText(getBaseContext(),
							"Registered, Please Login !", Toast.LENGTH_LONG)
							.show();
				else
					Toast.makeText(getBaseContext(), "Error, Try Again Later",
							Toast.LENGTH_LONG).show();
			}
		};
		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("password", password);
			json.put("email", email);
			json.put("avatar", "N/A");
			json.put("date_of_birth", "1/1/2001");
			json.put("location", "N/A");
			json.put("gender", "N/A");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		registerRequest.setBody(json);
		registerRequest.execute();
	}

}

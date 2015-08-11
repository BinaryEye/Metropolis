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
		if(username.equals("user") && password.equals("pass")){
			Toast.makeText(getBaseContext(), "Login Successful",
					Toast.LENGTH_LONG).show();
			Intent intent = new Intent(getBaseContext(),MainActivity.class);
			startActivity(intent);
		}
	}

	public void register(View v) {
		String username = ((EditText) findViewById(R.id.registerUsername))
				.getText().toString();
		String password = ((EditText) findViewById(R.id.registerPassword))
				.getText().toString();
		String email = ((EditText) findViewById(R.id.registerEmail)).getText()
				.toString();
		Toast.makeText(getBaseContext(), "Registration Successful",
				Toast.LENGTH_LONG).show();
	}

}

package com.metropolis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends Fragment {
	View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// if (Session.getActiveSession() != null) {
		// Session.getActiveSession().closeAndClearTokenInformation();
		// }
		//
		// Session.setActiveSession(null);
		view = inflater.inflate(R.layout.fragment_login, container, false);
		return view;
	}
}

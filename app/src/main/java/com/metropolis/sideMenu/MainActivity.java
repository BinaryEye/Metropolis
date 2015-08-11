package com.metropolis.sideMenu;

import com.metropolis.AuthActivity;
import com.metropolis.CommentsActivity;
import com.metropolis.R;
import com.metropolis.config.config;
import com.metropolis.requestClasses.PostRequest;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	private SharedPreferences data;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
		// try {
		// ViewConfiguration config = ViewConfiguration.get(this);
		// Field menuKeyField = ViewConfiguration.class
		// .getDeclaredField("sHasPermanentMenuKey");
		// if (menuKeyField != null) {
		// menuKeyField.setAccessible(true);
		// menuKeyField.setBoolean(config, false);
		// }
		// } catch (Exception ex) {
		// // Ignore
		// }
		data = getPreferences(MODE_PRIVATE);
		if (getIntent().getStringExtra("response") != null)
			createUser(getIntent().getStringExtra("response"));
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	public void createUser(String response) {
		try {
			if (getIntent().getIntExtra("type", 0) == 1) {
				JSONObject json = new JSONObject(response);
				String token = json.getString("token");
				int userId = Integer.parseInt(json.getString("user_id"));
				data.edit().putInt("userId", userId).apply();
				data.edit().putString("token", token).apply();
				data.edit().putString("username", json.getString("username"))
						.apply();
			} else {
				JSONObject user = new JSONObject(response);
				JSONObject rawdata = user.getJSONObject("raw_attributes");
				String userId = (String) rawdata.get("id");
				String name = (String) rawdata.get("name");
				String gender = (String) rawdata.get("gender");
				data.edit().putString("userId", userId).apply();
				data.edit().putString("gender", gender).apply();
				data.edit().putString("name", name).apply();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_logout:
			logout();
			break;
		case R.id.action_create:
			create();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	public void create() {
		if (getSupportFragmentManager().findFragmentByTag("feed") != null) {
			final FeedFragment fragment = (FeedFragment) getSupportFragmentManager()
					.findFragmentByTag("feed");
			if (fragment.getPosition() == 0) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
				final EditText post = new EditText(this);
				post.setHint("Your Post Here");
				// set title
				alertDialogBuilder.setTitle("New Post");

				// set dialog message
				alertDialogBuilder
						.setView(post)
						.setCancelable(false)
						.setPositiveButton("Create",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										PostRequest newPostRequest = new PostRequest(
												config.LOCAL_HOST_URL
														+ "/api/user_posts/create") {
											@Override
											protected void onPostExecute(
													String result) {
												if (getStatusCode() == 201)
													Toast.makeText(
															getBaseContext(),
															"Created",
															Toast.LENGTH_SHORT)
															.show();
												else
													Toast.makeText(
															getBaseContext(),
															"Failed",
															Toast.LENGTH_SHORT)
															.show();

											}
										};
										JSONObject json = new JSONObject();
										try {
											json.put("text", post.getText()
													.toString());
											json.put(
													"user_id",
													getPreferences(MODE_PRIVATE)
															.getInt("userId", 0));
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										newPostRequest.setBody(json);
										newPostRequest.execute();
										FragmentTransaction ft = getSupportFragmentManager()
												.beginTransaction();
										ft.detach(fragment);
										ft.attach(fragment);
										ft.commit();
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
										FragmentTransaction ft = getSupportFragmentManager()
												.beginTransaction();
										ft.detach(fragment);
										ft.attach(fragment);
										ft.commit();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();
			}

			if (fragment.getPosition() == 1) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						this);
				final EditText post = new EditText(this);
				post.setHint("Your Post Here");
				// set title
				alertDialogBuilder.setTitle("New Post");

				// set dialog message
				alertDialogBuilder
						.setView(post)
						.setCancelable(false)
						.setPositiveButton("Create",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										PostRequest newPostRequest = new PostRequest(
												config.LOCAL_HOST_URL
														+ "/api/user_posts/create") {
											@Override
											protected void onPostExecute(
													String result) {
												if (getStatusCode() == 201)
													Toast.makeText(
															getBaseContext(),
															"Created",
															Toast.LENGTH_SHORT)
															.show();
												else
													Toast.makeText(
															getBaseContext(),
															"Failed",
															Toast.LENGTH_SHORT)
															.show();

											}
										};
										JSONObject json = new JSONObject();
										try {
											json.put("text", post.getText()
													.toString());
											json.put(
													"user_id",
													getPreferences(MODE_PRIVATE)
															.getInt("userId", 0));
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										newPostRequest.setBody(json);
										newPostRequest.execute();
										FragmentTransaction ft = getSupportFragmentManager()
												.beginTransaction();
										ft.detach(fragment);
										ft.attach(fragment);
										ft.commit();
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// if this button is clicked, just close
										// the dialog box and do nothing
										dialog.cancel();
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		}

	}

	public void logout() {
		Intent intent = new Intent(this, AuthActivity.class);
		startActivity(intent);
		finish();

	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		String tag = "";
		switch (position) {
		case 0:
			fragment = new FeedFragment();
			tag = "feed";
			break;
		case 1:
			fragment = new AuctionHouseFragment();
			tag = "auctionhouse";
			break;
		case 2:
			fragment = new ProfileFragment();
			tag = "profile";
			break;

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment, tag).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void showComments(View v) {
		startActivity(new Intent(this, CommentsActivity.class));
	}

}

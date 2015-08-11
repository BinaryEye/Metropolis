package com.metropolis.views;

import com.metropolis.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ItemView extends LinearLayout {

	TextView itemName;
	LinearLayout statsLinearLayout;
	Button itemButton;

	public ItemView(Context context) {
		super(context);
		inflateLayout(context);
	}

	public void inflateLayout(Context context) {
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.item_view, this);
		itemName = (TextView) view.findViewById(R.id.itemName);
		statsLinearLayout = (LinearLayout) view
				.findViewById(R.id.statsContainer);
		itemButton = (Button) view.findViewById(R.id.itemButton);
		itemButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (itemButton.getText().toString().equals("Buy Item")) {
					Toast.makeText(getContext(), "You have bought it",
							Toast.LENGTH_SHORT).show();
				}
				if (itemButton.getText().toString().equals("Sell Item")) {
					Toast.makeText(getContext(), "You have sold it",
							Toast.LENGTH_SHORT).show();

				}
			}
		});
	}

	public void setData(String itemName, String stats,
			String buttonText) {
		this.itemName.setText(itemName);
		TextView itemPrice = new TextView(getContext());
		itemButton.setText(buttonText);
		statsLinearLayout.addView(itemPrice);
			TextView stat = new TextView(getContext());
			stat.setText(stats);
			statsLinearLayout.addView(stat);
		}
	}


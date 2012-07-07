package com.khoinguyen.bibotaxi.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khoinguyen.bibotaxi.R;
import com.khoinguyen.bibotaxi.model.Taxi;

public class TaxiListViewAdapter extends ArrayAdapter<Taxi> {
	private int layoutResourceId;
	private Context context;
	private Taxi[] data;

	public TaxiListViewAdapter(Context context, int layoutResourceId,
			Taxi[] data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		TaxiHolder taxiHolder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			taxiHolder = new TaxiHolder();
			taxiHolder.txtTitle = (TextView) row.findViewById(R.id.txtTitle);
			taxiHolder.txtNumber = (TextView) row.findViewById(R.id.txtNumber);
			row.setTag(taxiHolder);
		} else {
			taxiHolder = (TaxiHolder) row.getTag();
		}
		Taxi taxi = data[position];
		taxiHolder.txtTitle.setText(taxi.getTaxiName());
		taxiHolder.txtNumber.setText(taxi.getNumber());
		return row;
	}

	static class TaxiHolder {
		TextView txtTitle;
		TextView txtNumber;
	}

}

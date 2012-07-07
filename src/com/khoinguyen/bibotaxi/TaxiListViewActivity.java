package com.khoinguyen.bibotaxi;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.khoinguyen.bibotaxi.dao.BiboTaxiDB;
import com.khoinguyen.bibotaxi.model.Taxi;
import com.khoinguyen.bibotaxi.widget.TaxiListViewAdapter;

public class TaxiListViewActivity extends Activity {
	private ListView listView;
	private List<Taxi> listTaxi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		BiboTaxiDB db = BiboTaxiDB.getInstance(this);
		Intent intent = getIntent();
		String selectedCity = intent.getStringExtra("city");

		listTaxi = db.getAllTaxiByCity(selectedCity);
		int i = 0;
		String[] taxies = new String[listTaxi.size()];
		for (Taxi taxi : listTaxi) {
			taxies[i++] = taxi.getTaxiId();
		}

		Taxi[] data = (Taxi[]) listTaxi.toArray(new Taxi[listTaxi.size()]);

		listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new TaxiListViewAdapter(this, R.layout.taxi_item,
				data));
		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Taxi selectedTaxi = listTaxi.get(position);
				buildDialog(selectedTaxi);
			}
		});
	}

	private void buildDialog(final Taxi selectedTaxi) {
		Resources res = getResources();
		Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(String.format(res.getString(R.string.ask_for_calling), selectedTaxi.getTaxiName()));
		builder.setPositiveButton(res.getString(R.string.yes), new OnClickListener() {

			public void onClick(DialogInterface arg0, int arg1) {
				callTaxi(selectedTaxi);
			}
		});

		builder.setNegativeButton(res.getString(R.string.no), new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();
	}

	private void callTaxi(Taxi taxi) {
		String phonenumber = taxi.getNumber();
		String uriString = "tel:" + phonenumber;
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(uriString));
		startActivity(intent);
	}
}

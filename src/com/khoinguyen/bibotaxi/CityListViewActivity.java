package com.khoinguyen.bibotaxi;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.khoinguyen.bibotaxi.dao.BiboTaxiDB;
import com.khoinguyen.bibotaxi.model.City;

public class CityListViewActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BiboTaxiDB dbAdapter = BiboTaxiDB.getInstance(this);
		final List<City> listCities = dbAdapter.getAllCity();

		String[] cities;
		if (listCities.size() == 0) {
			cities = new String[] { "THERE ARE NO DATA" };
		} else {
			int i = 0;
			cities = new String[listCities.size()];
			for (City city : listCities) {
				cities[i++] = city.getName();
			}
		}

		this.setListAdapter(new ArrayAdapter<String>(this, R.layout.city_item,
				R.id.label, cities));
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String product = ((TextView) view).getText().toString();
				Intent i = new Intent(getApplicationContext(),
						TaxiListViewActivity.class);
				i.putExtra("product", product);
				i.putExtra("position", position);
				City selectedCity = listCities.get(position);
				i.putExtra("city", selectedCity.getId());

				startActivity(i);

			}

		});
	}
}

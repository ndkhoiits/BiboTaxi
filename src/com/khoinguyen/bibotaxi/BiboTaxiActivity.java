package com.khoinguyen.bibotaxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class BiboTaxiActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
        setContentView(R.layout.city_items_list_view);
        TextView txtProduct = (TextView) findViewById(R.id.product_label);
        Intent i = getIntent();
        String product = i.getStringExtra("product");
        int position = i.getIntExtra("position", 1);
        String text = product + "   " + String.valueOf(position);
        txtProduct.setText(String.valueOf(text));
    }
}
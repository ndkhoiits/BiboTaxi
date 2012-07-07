package com.khoinguyen.bibotaxi.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.khoinguyen.bibotaxi.model.City;
import com.khoinguyen.bibotaxi.model.Taxi;

public class BiboTaxiDB {
	public static final String KEY_ID = "id";
	public static final String KEY_NAME = "name";
	private static BiboTaxiDB instance;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;

	private static final String DATABASE_TABLE = "cities";
	private Context mContext;

	private static final String DATABASE_NAME = "BiboTaxi.db";

	private BiboTaxiDB(Context context) {
		this.mContext = context;
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, 1);
		mDbHelper.createDatabase(DATABASE_NAME);
		mDB = mDbHelper.getWritableDatabase();
	}

	public static BiboTaxiDB getInstance(Context context) {
		if (instance == null) {
			instance = new BiboTaxiDB(context);
		}

		return instance;
	}

	public long createCity(String name) {
		ContentValues initialValue = new ContentValues();
		initialValue.put(KEY_NAME, name);
		return mDB.insert(DATABASE_TABLE, null, initialValue);
	}

	public List<City> getAllCity() {
		List<City> cities = new ArrayList<City>();
		String selectQuery = "SELECT * FROM " + DATABASE_TABLE;
		Cursor cursor = mDB.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String cityId = cursor.getString(0);
				String cityName = cursor.getString(1);
				City city = new City(cityId, cityName);
				cities.add(city);
			} while (cursor.moveToNext());
		}

		return cities;
	}

	public List<Taxi> getAllTaxiByCity(String selectedCity) {
		List<Taxi> listTaxi = new ArrayList<Taxi>();
		String query = "SELECT taxi_id, city_id, phone_number, name as taxi_name FROM taxies, taxi_branch WHERE city_id LIKE '"
				+ selectedCity + "' AND taxies.taxi_id LIKE taxi_branch.id";
		Cursor cursor = mDB.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			do {
				String taxi_id = cursor.getString(cursor.getColumnIndex("taxi_id"));
				String city_id = cursor.getString(cursor.getColumnIndex("city_id"));
				String taxi_name = cursor.getString(cursor.getColumnIndex("taxi_name"));
				String number = cursor.getString(cursor.getColumnIndex("phone_number"));
				Taxi taxi = new Taxi(taxi_id, taxi_name, city_id, null, number);
				listTaxi.add(taxi);
			} while (cursor.moveToNext());
		}
		return listTaxi;
	}

	public void upgrade() {
		mDbHelper.onUpgrade(mDB, 0, 0);
	}

	public void close() {
		mDbHelper.close();
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		private Context context;
		private static final String DB_PATH = "/data/data/com.khoinguyen.bibotaxi/databases/";

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			this.context = context;
		}

		public void createDatabase(String dbName) {
			boolean dbExist = checkDataBase();
			if (dbExist) {
				// Do nothing
			} else {
				File f = new File(DB_PATH);
				if (!f.exists()) {
					f.mkdir();
				}
				super.getReadableDatabase();
				this.close();
				try {
					copy();
				} catch (Exception e) {
					throw new RuntimeException("Error copy database", e);
				}
			}
		}

		private boolean checkDataBase() {
			String dbPath = DB_PATH + DATABASE_NAME;
			SQLiteDatabase checkDb = null;
			try {
				checkDb = SQLiteDatabase.openDatabase(dbPath, null,
						SQLiteDatabase.OPEN_READONLY);
			} catch (SQLiteException e) {

			}

			if (checkDb != null) {
				checkDb.close();
			}

			return checkDb != null;
		}

		private void copy() {
			try {
				File dbFile = new File(DB_PATH + DATABASE_NAME);
				InputStream in = context.getApplicationContext().getAssets()
						.open(DATABASE_NAME);
				OutputStream os = new FileOutputStream(dbFile);
				dbFile.createNewFile();
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) != -1) {
					os.write(buf, 0, len);
				}

				os.flush();
				os.close();
				in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}
	}

}

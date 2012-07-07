package com.khoinguyen.bibotaxi.model;

public class Taxi {
	private String taxiId;
	private String cityId;
	private String taxiName;
	private String description;
	private String number;

	public Taxi(String taxiId, String taxi_name, String cityId, String description, String number) {
		this.taxiId = taxiId;
		this.taxiName = taxi_name;
		this.cityId = cityId;
		this.description = description;
		this.number = number;
	}

	public String getTaxiId() {
		return taxiId;
	}

	public void setTaxiId(String taxiId) {
		this.taxiId = taxiId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getTaxiName() {
		return taxiName;
	}

	public void setTaxiName(String taxiName) {
		this.taxiName = taxiName;
	}
}

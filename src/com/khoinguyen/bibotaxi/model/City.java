package com.khoinguyen.bibotaxi.model;

public class City {
	private String id;
	private String name;

	public City(String cityId, String name) {
		this.id = cityId;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

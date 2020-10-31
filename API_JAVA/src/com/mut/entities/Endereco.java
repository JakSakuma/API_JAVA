package com.mut.entities;

import org.json.JSONObject;

public class Endereco {

	private Integer idPessoa;
	private String street;
	private String suite;
	private String city;
	private String zipcode;
	private String lat;
	private String lng;

	public Endereco() {

	}
	
	public Endereco(Integer idPessoa, String street, String suite, String city, String zipcode, String lat, String lng) {

		this.idPessoa = idPessoa;
		this.street = street;
		this.suite = suite;
		this.city = city;
		this.zipcode = zipcode;
		this.lat = lat;
		this.lng = lng;

	}

	@Override
	public String toString() {
		return String.format("idPessoa:" + idPessoa + "|street:" + street + "|suite:" + suite + "|city:" + city + 
				"|zipcode:" + zipcode + "|address_lat:" + lat + "|address_lng:" + lng);
	}

	public JSONObject getObject() {
		JSONObject dados = new JSONObject();
		dados.put("idPessoa", idPessoa);
		dados.put("street", street);
		dados.put("suite", suite);
		dados.put("city", city);
		dados.put("zipcode", zipcode);
		dados.put("lat", lat);
		dados.put("lng", lng);

		return dados;
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSuite() {
		return suite;
	}

	public void setSuite(String suite) {
		this.suite = suite;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

}

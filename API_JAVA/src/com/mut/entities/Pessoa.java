package com.mut.entities;

import org.json.JSONObject;

public class Pessoa {

	private Integer id;
	private String name;
	private String username;
	private String email;

	public Pessoa() {

	}

	public Pessoa(Integer id, String name, String username, String email) {

		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("id:" + id + "|name:" + name + "|username:" + username + "|email:" + email);
	}

	public JSONObject getObject() {
		JSONObject dados = new JSONObject();
		dados.put("id", id);
		dados.put("name", name);
		dados.put("username", username);
		dados.put("email", email);

		return dados;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

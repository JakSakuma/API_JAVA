package com.mut.entities;

import org.json.JSONObject;

public class Contato {

	private Integer idPessoa;
	private String phone;
	private String website;
	private String company_name;
	private String company_catchPhrase;
	private String company_bs;

	public Contato() {

	}

	public Contato(Integer idPessoa, String phone, String website, String company_name,
			String company_catchPhrase, String company_bs) {

		this.idPessoa = idPessoa;
		this.phone = phone;
		this.website = website;
		this.company_name = company_name;
		this.company_catchPhrase = company_catchPhrase;
		this.company_bs = company_bs;
	}

	@Override
	public String toString() {
		return String.format(
				"idPessoa:" + idPessoa + "|phone:" + phone + "|website:" + website + "|company_name:"
						+ company_name + "|company_catchPhrase:" + company_catchPhrase + "|company_bs:" + company_bs);
	}

	public JSONObject getObject() {
		JSONObject dados = new JSONObject();

		dados.put("idPessoa", idPessoa);
		dados.put("phone", phone);
		dados.put("website", website);
		dados.put("company_name", company_name);
		dados.put("company_catchPhrase", company_catchPhrase);
		dados.put("company_bs", company_bs);
		return dados;
	}

	public Integer getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Integer idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_catchPhrase() {
		return company_catchPhrase;
	}

	public void setCompany_catchPhrase(String company_catchPhrase) {
		this.company_catchPhrase = company_catchPhrase;
	}

	public String getCompany_bs() {
		return company_bs;
	}

	public void setCompany_bs(String company_bs) {
		this.company_bs = company_bs;
	}
}

package com.mut.salvarDados;

import org.json.JSONObject;

public class SalvarDados_Tester {
	
	public static void main(String[] args) {
		postSalvarDados();
	}
	
	public static void postSalvarDados() {

		SalvarDados salvarDados = new SalvarDados();
				
		JSONObject jObject = salvarDados.executePostSalvarDados("8b4d7e23-75a7-46d2-b88b-1ea9ed45754a");
		System.out.println(jObject.toString());	
	}
}

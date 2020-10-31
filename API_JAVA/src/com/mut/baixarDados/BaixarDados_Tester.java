package com.mut.baixarDados;

import org.json.JSONObject;

public class BaixarDados_Tester {
	
	public static void main(String[] args) {
		getBaixarDados();
	}
	
	public static void getBaixarDados() {

		BaixarDados baixarDados = new BaixarDados();
		
		JSONObject jObject = baixarDados.executeGetBaixarDados("8b4d7e23-75a7-46d2-b88b-1ea9ed45754a");
		System.out.println(jObject.toString());
		
	}
}

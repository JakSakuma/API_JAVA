package com.mut.baixarDados;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mut.main.RespostaHttp;

public class BaixarDados {

	// Melhores praticas ** getInstance()- Garante que a classe seja criada uma
	// unica vez
	private static BaixarDados instance = null;

	public static BaixarDados getInstance() {
		if (instance == null) {
			instance = new BaixarDados();
		}
		return instance;
	}

	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(BaixarDados.class);

	public JSONObject executeGetBaixarDados(String uniqueID) {

		JSONObject resultado = null;
		resultado = getBaixarDados (uniqueID);
		logger.debug("{} - Resultado: {}", uniqueID, resultado);

		return resultado;
	}

	public JSONObject getBaixarDados(String uniqueID) {

		RespostaHttp resposta = new RespostaHttp();
		JSONObject result = new JSONObject();

		float tempoResposta = 0;
		int statusCode = 0;

		/* Variáveis utilizadas na chamada */
		String retorno = "";
		JSONArray dados = new JSONArray();
		HttpURLConnection conn = null;

		try {

			URL cURL;
			cURL = new URL("https://jsonplaceholder.typicode.com/users");

			logger.info("{} - URL: {}", uniqueID, cURL);

			/* Abre conexão com a URL informada */
			conn = (HttpURLConnection) cURL.openConnection();

			conn.addRequestProperty("content-type", "application/json");
			conn.setRequestMethod("GET");
			logger.info("{} - Request Method: {}", uniqueID, "GET");

			// tempo inicial da chamada do serviço
			long tempInicial = System.currentTimeMillis();

			/*
			 * Trata retorno HTTP para saber se houve um erro na requisição ou se retornou o
			 * esperado.
			 */
			JSONObject retornoHttp = resposta.respostaHttp(conn);
			logger.debug("{} - Resposta retornoHttp: {}", uniqueID, retornoHttp);

			// tempo final da chamada do serviço
			long tempFinal = System.currentTimeMillis();
			long dif = (tempFinal - tempInicial);
			logger.info("{} - Tempo para consultar o servico: {} ms", uniqueID, dif);

			tempoResposta = dif;

			/* Fecha conexão */
			conn.disconnect();

			statusCode = (Integer) retornoHttp.get("codigo");

			// Verifica se houve um erro HTTP
			if (statusCode != 200) {
				if (statusCode == 400) {
					retorno = "9";
				} else {
					retorno = "0";
				}
			} else {

				retorno = "1";
				if (!retornoHttp.get("resposta").equals("")) {

					// Verifica retorno
					dados = new JSONArray((String) retornoHttp.get("resposta"));

				} else {
					statusCode = 404;
					retorno = "0";
				}
			}

		} catch (Exception e) {
			logger.error("{} - erro: {}", uniqueID, retorno);
			logger.error("{} - erro: {}", uniqueID, e.toString());
			retorno = "9";
		}

		if (statusCode == 200) {
			BaixarDados_Result response = new BaixarDados_Result(200, tempoResposta, dados, uniqueID);
			result = response.getObject();

		} else if (statusCode == 404) {
			BaixarDados_Result response = new BaixarDados_Result(201, tempoResposta, dados, uniqueID);
			result = response.getObject();

		} else {
			BaixarDados_Result response = new BaixarDados_Result(202, tempoResposta, dados, uniqueID);
			result = response.getObject();
		}

		return result;
	}

	class BaixarDados_Result {

		private int statusCode;
		private float tempoResposta;
		private JSONArray dados;
		private String uniqueID;

		public BaixarDados_Result(int statusCode, float tempoResposta, JSONArray dados, String uniqueID) {

			this.statusCode = statusCode;
			this.tempoResposta = tempoResposta;
			this.dados = dados;
			this.uniqueID = uniqueID;
		}

		public JSONObject getObject() {
			JSONObject result = new JSONObject();

			result.put("statusCode", statusCode);
			result.put("tempoResposta", tempoResposta);
			result.put("dados", dados);
			result.put("uniqueID", uniqueID);

			if (statusCode == 200)
				result.put("retorno", "OK");
			else
				result.put("retorno", "NOK");

			return result;

		}
	}
}

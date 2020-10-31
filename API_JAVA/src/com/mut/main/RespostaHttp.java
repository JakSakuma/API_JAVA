package com.mut.main;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RespostaHttp {
	
	private static final Logger logger = LoggerFactory.getLogger(RespostaHttp.class);

	public JSONObject respostaHttp(HttpURLConnection conn) {
		
		if(conn.getConnectTimeout() > 20000 || conn.getConnectTimeout() == 0){
			conn.setConnectTimeout(20000);
		}
		
		if(conn.getReadTimeout() > 20000 || conn.getReadTimeout() == 0){
			conn.setReadTimeout(20000);
		}

		try {
			int statusCode = conn.getResponseCode();

			int nRead;
			byte[] data = new byte[1024];
			ByteArrayOutputStream buffer;

			String respostaServico = "";

			// verifica se ocorreu algum erro na requisicao
			if (statusCode == 200 || statusCode == 201 || statusCode == 202) {
				buffer = new ByteArrayOutputStream();

				// L� as informa��es retornadas na requisi��o e coloca num stream de dados
				data = new byte[4096];

				while ((nRead = conn.getInputStream().read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				buffer.flush();

				// L� as informa��es retornadas no stream
				respostaServico = buffer.toString();
			} else if (statusCode == 404) {
				// quando o usuario nao existe no sistema, � retornado o
				// erro Http 404
			} else {
				// Qualquer outro erro sera retornado aqui
				buffer = new ByteArrayOutputStream();

				while ((nRead = conn.getErrorStream().read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}

				buffer.flush();

				//respostaServico = "Ocorreu um erro Http: " + buffer.toString();
				respostaServico = buffer.toString();
			}

			JSONObject retorno = new JSONObject();
			retorno.put("resposta", respostaServico);
			retorno.put("codigo", statusCode);

			return retorno;
		} catch (Exception e) {			
			JSONObject ret = new JSONObject();
			ret.put("resposta", e);
			ret.put("codigo", 0);
			return ret;
		}
	}
	
	public HttpResponse doPost(URL url, Header[] headers, HttpEntity body, String uniqueID) {
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(url.toURI());
		} catch (Exception e) {
			logger.error("{} - httpPost erro: {}", uniqueID, e);
			return null;
		}
		httpPost.setHeaders(headers);
		httpPost.setEntity(body);
		
		try {
			RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(7000).setConnectionRequestTimeout(7000).build();
			CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
			return httpClient.execute(httpPost);
		} catch (Exception e) {
			logger.error("{} - doPost erro: {}", uniqueID, e);
			return null;
		}
	}
	
	public JSONObject respostaHttpPost(HttpResponse httpResponse, String uniqueID) {
		
		try {
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String respostaServico = "";

			// verifica se ocorreu algum erro na requisicao
			if (statusCode == HttpStatus.SC_OK) {
				respostaServico = EntityUtils.toString(httpResponse.getEntity());
			} else {				
				respostaServico = "Ocorreu um erro Http: " + httpResponse.getStatusLine();
			}

			JSONObject retorno = new JSONObject();
			retorno.put("resposta", respostaServico);
			retorno.put("codigo", statusCode);

			return retorno;
		} catch (Exception e) {
			logger.error("{} - respostaHttpPost erro: {}", uniqueID, e);
			
			JSONObject ret = new JSONObject();
			ret.put("resposta", e);
			ret.put("codigo", 0);
			return ret;
		}
	}

}

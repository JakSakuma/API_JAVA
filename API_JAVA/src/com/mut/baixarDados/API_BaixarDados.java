package com.mut.baixarDados;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class API_BaixarDados {

	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(API_BaixarDados.class);
	private static final String ACCESS_CONTROL = "Access-Control-Allow-Origin";
	private static final String ENCODING_FORMAT = "utf-8";

	@GET
	@Path("/mut/api/v1/baixarDados")	
	public Response baixarDados(@Context HttpServletRequest req) {

		Response resposta = null;

		String uniqueID = UUID.randomUUID().toString();
		logger.info("{} - Solicitacao para Baixar Dados", uniqueID);

		BaixarDados baixarDados = BaixarDados.getInstance();
		JSONObject retorno = baixarDados.executeGetBaixarDados(uniqueID);
		logger.info("{} - Response obtido: {}", uniqueID, retorno);
		resposta = Response.status(200).header(ACCESS_CONTROL, "*").entity(retorno.toString()).encoding(ENCODING_FORMAT)
				.build();

		return resposta;
	}
}
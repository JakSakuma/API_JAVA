package com.mut.salvarDados;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mut.baixarDados.BaixarDados;
import com.mut.dbacess.Mysql;
import com.mut.dbacess.MysqlProducao;
import com.mut.entities.Contato;
import com.mut.entities.Endereco;
import com.mut.entities.Pessoa;

public class SalvarDados {

	// Melhores praticas ** getInstance()- Garante que a classe seja criada uma
	// unica vez
	private static SalvarDados instance = null;

	public static SalvarDados getInstance() {
		if (instance == null) {
			instance = new SalvarDados();
		}
		return instance;
	}

	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(SalvarDados.class);

	public JSONObject executePostSalvarDados(String uniqueID) {

		JSONObject resultado = null;
		Mysql mysql = null;

		mysql = MysqlProducao.getInstance();
		logger.debug("{} - Instancia de banco: {}", uniqueID, mysql);

		resultado = postSalvarDados(mysql, uniqueID);
		logger.debug("{} - Resultado: {}", uniqueID, resultado);

		return resultado;
	}

	public JSONObject postSalvarDados(Mysql mysql, String uniqueID) {

		String retorno = "OK";
		String output = "";
		ArrayList<Pessoa> pessoas = new ArrayList<Pessoa>();
		ArrayList<Endereco> enderecos = new ArrayList<Endereco>();
		ArrayList<Contato> contatos = new ArrayList<Contato>();

		logger.info("{} - Request Method: {}", uniqueID, "POST");

		// USA METODO BAIXAR DADOS PRA PEGAR OS DADOS
		BaixarDados baixarDados = new BaixarDados();
		JSONObject retornoBaixarDados = baixarDados.getBaixarDados(uniqueID);
		JSONArray dados = retornoBaixarDados.getJSONArray("dados");

		for (int i = 0; i < dados.length(); i++) {
			JSONObject dadosI = dados.getJSONObject(i);

			if (dadosI.has("id")) {
				if (dadosI.has("address")) {
					JSONObject adress = dadosI.getJSONObject("address");
					if (adress.has("suite")) {
						String suite = adress.getString("suite");
						String conteudo = "Suite";

						if (suite.toUpperCase().contains(conteudo.toUpperCase())) {

							Pessoa objPessoa = new Pessoa(dadosI.getInt("id"), dadosI.getString("name"),
									dadosI.getString("username"), dadosI.getString("email"));

							JSONObject geo = adress.getJSONObject("geo");
							Endereco objEndereco = new Endereco(dadosI.getInt("id"), adress.getString("street"),
									adress.getString("suite"), adress.getString("city"), adress.getString("zipcode"),
									geo.getString("lat"), geo.getString("lng"));

							JSONObject company = dadosI.getJSONObject("company");
							Contato objContato = new Contato(dadosI.getInt("id"), dadosI.getString("phone"),
									dadosI.getString("website"), company.getString("name"),
									company.getString("catchPhrase"), company.getString("bs"));

							pessoas.add(objPessoa);
							enderecos.add(objEndereco);
							contatos.add(objContato);

						}
					}
				}
			}
		}
		
		pessoas = ordena_array(pessoas);

		logger.info("{} - Arraylist Pessoas em ordem alfabética: {}", uniqueID, pessoas);
		logger.info("{} - Arraylist Enderecos: {}", uniqueID, enderecos);
		logger.info("{} - Arraylist Contatos: {}", uniqueID, contatos);
		
		
		// tempo inicial da chamada do servico
		long tempInicial = System.currentTimeMillis();

		// conecta com banco
		Connection conn = mysql.connectMysql(uniqueID);

		if (conn != null) {
			// executa inserção
			DadosDAOImpl dadosDAOImpl = new DadosDAOImpl();
			output = dadosDAOImpl.insertPessoa(conn, pessoas, uniqueID);
			logger.info("{} - Output Insert Pessoas: {}", uniqueID, output);

			output = dadosDAOImpl.insertEndereco(conn, enderecos, uniqueID);
			logger.info("{} - Output Insert Enderecos: {}", uniqueID, output);

			output = dadosDAOImpl.insertContato(conn, contatos, uniqueID);
			logger.info("{} - Output Insert Contatos: {}", uniqueID, output);

		} else {
			retorno = "NOK | Não foi possível se conectar ao BD";
		}

		// tempo final da chamada do servico
		long tempFinal = System.currentTimeMillis();

		float tempoResposta = tempFinal - tempInicial;
		logger.info("{} - Tempo para inserir os dados: {} ms", uniqueID, tempoResposta);
		logger.debug("{} - Retorno MySQL: {}", uniqueID, retorno);

		SalvarDados_Result response;
		if (retorno.equals("OK")) {
			response = new SalvarDados_Result(200, tempoResposta, retorno, output, uniqueID);
		} else if (retorno.equals("NOK")) {
			response = new SalvarDados_Result(201, tempoResposta, retorno, output, uniqueID);
		} else {
			response = new SalvarDados_Result(202, tempoResposta, retorno, output, uniqueID);
		}

		return response.getObject();
	}

	class SalvarDados_Result {

		private int statusCode;
		private float tempoResposta;
		private String uniqueID;
		private String output;

		public SalvarDados_Result(int statusCode, float tempoResposta, String retorno, String output, String uniqueID) {

			this.statusCode = statusCode;
			this.tempoResposta = tempoResposta;
			this.uniqueID = uniqueID;
			this.output = output;
		}

		public JSONObject getObject() {
			JSONObject result = new JSONObject();

			result.put("statusCode", statusCode);
			result.put("tempoResposta", tempoResposta);
			result.put("uniqueID", uniqueID);
			result.put("output", output);

			if (statusCode == 200)
				result.put("retorno", "OK");
			else
				result.put("retorno", "NOK");

			return result;

		}
	}

	public ArrayList<Pessoa> ordena_array(ArrayList<Pessoa> pessoaArrayList) {
		try {

			Collections.sort(pessoaArrayList, new Comparator<Pessoa>() {

				@Override
				public int compare(Pessoa pessoaA, Pessoa pessoaB) {
					int compare = 0;

					try {

						compare = pessoaA.getName().compareTo(pessoaB.getName());

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					return compare;
				}
			});

		} catch (Exception jEx) {
			jEx.printStackTrace();
		}

		return pessoaArrayList;
	}
}

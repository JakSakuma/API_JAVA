package com.mut.salvarDados;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mut.entities.Contato;
import com.mut.entities.Endereco;
import com.mut.entities.Pessoa;

public class DadosDAOImpl implements DadosDAO {

	// Loggers
	private static final Logger logger = LoggerFactory.getLogger(DadosDAOImpl.class);

	public String insertPessoa(Connection conn, ArrayList<Pessoa> dados, String uniqueID) {

		String output = "OK";

		String sql = "INSERT INTO tb_infpessoa (id, name, username, email) VALUES (?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int insertFailures = 0;

			for (int i = 0; i < dados.size(); i++) {
				Pessoa dadosI = dados.get(i);

				try {
					ps.setInt(1, dadosI.getId());
					ps.setString(2, dadosI.getName());
					ps.setString(3, dadosI.getUsername());
					ps.setString(4, dadosI.getEmail());

					ps.executeUpdate();
				} catch (SQLException e) {
					insertFailures++;
					logger.error("{} - Não foi possível inserir o registro {}:{}", uniqueID, i, e);
				}
			}

			if (insertFailures != 0) {
				output = "NOK | " + insertFailures + " de " + dados.size() + " não foram inseridos!";
			}

		} catch (SQLException e) {
			output = "NOK | Erro para criar statement";
			logger.error("{} - Não foi possível criar o statement: {}", uniqueID, e);
		}

		return output;
	}

	@Override
	public String insertEndereco(Connection conn, ArrayList<Endereco> dados, String uniqueID) {
		String output = "OK";

		String sql = "INSERT INTO tb_endereco (idPessoa, street, suite, city, zipcode, lat, lng) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int insertFailures = 0;

			for (int i = 0; i < dados.size(); i++) {
				Endereco dadosI = dados.get(i);

				try {
					ps.setInt(1, dadosI.getIdPessoa());
					ps.setString(2, dadosI.getStreet());
					ps.setString(3, dadosI.getSuite());
					ps.setString(4, dadosI.getCity());
					ps.setString(5, dadosI.getZipcode());
					ps.setString(6, dadosI.getLat());
					ps.setString(7, dadosI.getLng());

					ps.executeUpdate();
				} catch (SQLException e) {
					insertFailures++;
					logger.error("{} - Não foi possível inserir o registro {}:{}", uniqueID, i, e);
				}
			}

			if (insertFailures != 0) {
				output = "NOK | " + insertFailures + " de " + dados.size() + " não foram inseridos!";
			}

		} catch (SQLException e) {
			output = "NOK | Erro para criar statement";
			logger.error("{} - Não foi possível criar o statement: {}", uniqueID, e);
		}

		return output;
	}

	@Override
	public String insertContato(Connection conn, ArrayList<Contato> dados, String uniqueID) {
		String output = "OK";

		String sql = "INSERT INTO tb_contato (idPessoa, phone, website, company_name, company_catchPhrase, company_bs) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			int insertFailures = 0;

			for (int i = 0; i < dados.size(); i++) {
				Contato dadosI = dados.get(i);

				try {
					ps.setInt(1, dadosI.getIdPessoa());
					ps.setString(2, dadosI.getPhone());
					ps.setString(3, dadosI.getWebsite());
					ps.setString(4, dadosI.getCompany_name());
					ps.setString(5, dadosI.getCompany_catchPhrase());
					ps.setString(6, dadosI.getCompany_bs());

					ps.executeUpdate();
				} catch (SQLException e) {
					insertFailures++;
					logger.error("{} - Não foi possível inserir o registro {}:{}", uniqueID, i, e);
				}
			}

			if (insertFailures != 0) {
				output = "NOK | " + insertFailures + " de " + dados.size() + " não foram inseridos!";
			}

		} catch (SQLException e) {
			output = "NOK | Erro para criar statement";
			logger.error("{} - Não foi possível criar o statement: {}", uniqueID, e);
		}

		return output;
	}
}
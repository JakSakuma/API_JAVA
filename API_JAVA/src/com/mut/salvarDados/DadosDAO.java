package com.mut.salvarDados;

import java.sql.Connection;
import java.util.ArrayList;

import com.mut.entities.Contato;
import com.mut.entities.Endereco;
import com.mut.entities.Pessoa;

public interface DadosDAO {
	public String insertPessoa(Connection conn, ArrayList<Pessoa> dados, String uniqueID);
	public String insertEndereco(Connection conn, ArrayList<Endereco> dados, String uniqueID); 
	public String insertContato(Connection conn, ArrayList<Contato> dados, String uniqueID); 
}
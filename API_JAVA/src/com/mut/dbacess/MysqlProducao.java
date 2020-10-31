package com.mut.dbacess;

import com.mut.main.Config;

public class MysqlProducao extends Mysql{
	
	private static MysqlProducao instance = null;
	
	Config configuracao;
	
	protected MysqlProducao(){
		configuracao = Config.getInstance();
	}
	
	public static MysqlProducao getInstance(){
		if(instance == null){
			instance = new MysqlProducao();
		}
		return instance;
	}
	
	void obterConfiguracoes(){
		server = configuracao.obterPropriedade("mysql_host1");
		port = configuracao.obterPropriedade("mysql_port");
		service_name = configuracao.obterPropriedade("mysql_service_name");
		user = configuracao.obterPropriedade("mysql_user");
		passwd = configuracao.obterPropriedade("mysql_password");
	}
}

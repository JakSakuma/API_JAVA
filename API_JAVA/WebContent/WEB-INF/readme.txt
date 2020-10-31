
------------------------------------------
Observações:

- Infelizmente nao consegui fazer a interface do swagger funcionar. Via postman tudo funciona (vide prints na pasta 'evidencias')
- Nao consegui fazer com que salvasse em ordem alfabetica no banco, mesmo ordenando o arrayList.
- Nao sei qual o motivo, mas no banco esta salvando Endereco e Contato de forma duplicada :(.

------------------------------------------

Script SQL:

CREATE DATABASE myDB;
USE myDB;

CREATE TABLE tb_infpessoa
(
	id INT NOT NULL PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	username VARCHAR(80) NOT NULL,
	email VARCHAR(80) NOT NULL
);


CREATE TABLE tb_endereco
(
	idEndereco INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	idPessoa INT NOT NULL,
	street VARCHAR(50) NOT NULL,
	suite VARCHAR(50) NOT NULL,
	city VARCHAR(50) NOT NULL,
	zipcode VARCHAR(20) NOT NULL,
	lat VARCHAR(20) NOT NULL,
	lng VARCHAR(20) NOT NULL,
	
	FOREIGN KEY (idPessoa) REFERENCES tb_infpessoa (id)
);


CREATE TABLE tb_contato
(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	idPessoa INT NOT NULL,
	phone VARCHAR(100) NOT NULL,
	website VARCHAR(100) NOT NULL,
	company_name VARCHAR(1000) NOT NULL,
	company_catchPhrase VARCHAR(100) NOT NULL,
	company_bs VARCHAR(100) NOT NULL,
	
	FOREIGN KEY (idPessoa) REFERENCES tb_infpessoa (id)
);

------------------------------------------
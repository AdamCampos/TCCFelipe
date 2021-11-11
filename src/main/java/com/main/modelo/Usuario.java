package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@Entity
@Named
@Table(name = "Usuario")
public class Usuario {

	public Usuario(int matricula, String nome, int senha, String foto) {

		try {
			log.debug("::instanciando usuario parametrizado - Nome: " + this.getNome());
		} catch (Exception e) {
			log.debug("::instanciando usuario parametrizado - Erro " + e);
		}

		this.matricula = matricula;
		this.nome = nome;
		this.senha = senha;
		this.foto = foto;

	}

	public Usuario() {

		try {
			log.debug("::instanciando usuario - Nome: " + this.getNome());
			log.debug("::instanciando usuario - Senha: " + this.getSenha());
			log.debug("::instanciando usuario - Matricula: " + this.getMatricula());
		} catch (Exception e) {
			log.debug("::instanciando usuario - Erro " + e);
		}

	}

	@Id
	private int matricula = 999;
	private String nome = "Anonimous";
	private int senha = 0;
	private String foto = "fotoAnonima";

}
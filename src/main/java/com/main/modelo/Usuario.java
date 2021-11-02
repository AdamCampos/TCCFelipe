package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
	@NotNull(message = "matricula cannot be null")
	@NotEmpty(message = "matricula cannot be empty")
	private int matricula = 999;
	@NotNull(message = "nome cannot be null")
	@NotEmpty(message = "nome cannot be empty")
	private String nome = "Anonimous";
	@NotNull(message = "senha cannot be null")
	@NotEmpty(message = "senha cannot be empty")
	private int senha = 0;
	@NotNull(message = "foto cannot be null")
	@NotEmpty(message = "foto cannot be empty")
	private String foto = "fotoAnonima";

}
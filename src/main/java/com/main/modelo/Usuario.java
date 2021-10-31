package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Named
@Table(name = "Usuario")
public class Usuario {

	public Usuario(int matricula, String nome, int senha, String foto) {
		this.matricula = matricula;
		this.nome = nome;
		this.senha = senha;
		this.foto = foto;

	}

	public Usuario() {
	}

	@Id
	private int matricula;
	private String nome;
	private int senha;
	private String foto;

}
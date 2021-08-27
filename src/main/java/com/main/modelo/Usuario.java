package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Named
@Table(name = "Usuario")
public class Usuario {

	public Usuario(int matricula, String nome) {
		this.matricula = matricula;
		this.nome = nome;
	}

	public Usuario() {
	}

	@Id
	private int matricula;
	private String nome;

}

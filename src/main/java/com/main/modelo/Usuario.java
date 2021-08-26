package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Named
@Table(name = "Usuario")
public class Usuario {

	public Usuario(int matricula, String nome) {
		// TODO Auto-generated constructor stub
	}

	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	@Id
	private int matricula;
	private String nome;

}

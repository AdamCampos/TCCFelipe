package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Named
@Table(name = "Extintor")
public class Extintor {

	public Extintor(int id, String classe, String agente, String dataCompra, String volume, String foto) {
		this.id = id;
		this.classe = classe;
		this.agente = agente;
		this.dataCompra = dataCompra;
		this.volume = volume;
		this.foto = foto;
	}

	public Extintor() {
	}

	@Id
	private int id = 0;
	private String classe = null;
	private String agente = null;
	private String dataCompra = "1999-01-01";
	private String volume = "1999-01-01";
	private String foto = null;

}
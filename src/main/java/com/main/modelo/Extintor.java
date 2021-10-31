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

	public Extintor(int id, String classe, String agente, String dataCompra, double volume, String foto) {
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
	private String 	classe;
	private String agente;
	private String dataCompra;
	private double volume;
	private String foto;
	
}
package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Named
@Table(name = "Vistoria")
public class Vistoria {

	@Id
	private int id;
	private String dataUltima;
	private int fkExtintor;
	private String dataProxima;
	private String obs;
	private String foto;
	private int fkUsuario;

	public Vistoria(int id, String dataUltima, String dataProxima, int fkExtintor, String obs,String foto, int fkUsuario) {
		this.id = id;
		this.dataUltima = dataUltima;
		this.dataProxima = dataProxima;
		this.fkExtintor = fkExtintor;
		this.obs = obs;
		this.foto = foto;
		this.fkUsuario = fkUsuario;
	}

	public Vistoria() {
	}
}

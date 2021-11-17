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
	private int fkExtintor;
	private int fkUsuario;
	private String dataUltima;
	private String dataProxima;
	private String dataTeste;
	private String foto;
	/* private String localizacao; */
	private String obs;

	public Vistoria(int id, int fkExtintor, int fkUsuario, String dataUltima, String dataProxima, String dataTeste,
			String foto, /* String localizacao, */ String obs) {
		this.id = id;
		this.fkExtintor = fkExtintor;
		this.fkUsuario = fkUsuario;
		this.dataUltima = dataUltima;
		this.dataProxima = dataProxima;
		this.dataTeste = dataTeste;
		this.foto = foto;
		/* this.localizacao; */
		this.obs = obs;
	}

	public Vistoria() {
	}

}

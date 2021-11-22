package com.main.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Entity
@Named
@Table(name = "Vistoria")
@Log4j2
public class Vistoria {

	@Id
	private int id;
	private int fkExtintor;
	private int fkUsuario;
	private int periodo;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataUltima;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataProxima;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataTeste;
	private byte[] foto;
	private String localizacao;
	private String obs;
	@Transient
	private Date dataUltimaDate;
	@Transient
	private Date dataProximaDate;
	@Transient
	private String fotoBanco;

	public Vistoria(int id, int fkExtintor, int fkUsuario, Date dataUltima, Date dataProxima, int periodo,
			Date dataTeste, byte[] foto, String localizacao, String obs) {
		this.id = id;
		this.fkExtintor = fkExtintor;
		this.fkUsuario = fkUsuario;
		this.periodo = periodo;
		this.dataUltima = dataUltima;
		this.dataProxima = dataProxima;
		this.dataTeste = dataTeste;
		this.foto = foto;
		this.localizacao = localizacao;
		this.obs = obs;

		// this.dataUltimaDate = this.getData(dataUltima);
		// this.dataProximaDate = this.getData(dataProxima);
	}

	public Vistoria() {
	}

	public Date getData(String dataString) {

		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date dataFormatada = formato.parse(dataString);
			log.debug("::Data formatada " + dataFormatada);
			return dataFormatada;
		} catch (ParseException e) {
			log.debug("::Erro ao formatar " + dataString + " " + e);
			return new Date();
		}

	}

	public String getFotoBanco(byte[] imagem) {

		String fotoString = Base64.encodeBase64String(imagem);
		return fotoString;
	}

}

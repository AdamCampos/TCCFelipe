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
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Entity
@Named
@Table(name = "Vistoria")
@Log4j2
public class Vistoria implements Validator {

	@Id
	private int id = 0;
	private int fkExtintor = 0;
	private int fkUsuario;
	private int periodo = 12;
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

	// Confere se este objeto é validável.
	@Override
	public boolean supports(Class<?> clazz) {
		return Vistoria.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {
		/*
		 * log.debug("::Validando " + obj);
		 * 
		 * ValidationUtils.rejectIfEmpty(e, "name", "name.empty"); Vistoria v =
		 * (Vistoria) obj; if (v.getFkUsuario() < 0) { e.rejectValue("fkUsuario",
		 * "negativevalue"); } else if (v.getFkExtintor() > 110) {
		 * e.rejectValue("fkExtintor", "too.darn.old"); }
		 */
	}

}

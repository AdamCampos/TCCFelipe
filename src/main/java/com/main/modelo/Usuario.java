package com.main.modelo;

import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.codec.binary.Base64;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Entity
@Named
@Table(name = "Usuario")
@Log4j2
public class Usuario implements Validator {

	@Id
	@NotNull
	private int matricula = 999;
	private String nome = "Anonimous";
	@NotNull
	private int senha = 0;
	private String foto = "fotoAnonima";
	private byte[] img;
	private String fotoBanco;

	public Usuario(int matricula, String nome, int senha, String foto, byte[] img) {

		this.matricula = matricula;
		this.nome = nome;
		this.senha = senha;
		this.foto = foto;
		this.img = img;

	}

	public Usuario() {
	}

	// Confere se este objeto é validável.
	@Override
	public boolean supports(Class<?> clazz) {
		return Vistoria.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors e) {

		log.debug("::Validando " + obj);

		ValidationUtils.rejectIfEmpty(e, "matricula", "name.empty");
		Usuario u = (Usuario) obj;
		if (u.getMatricula() < 0) {
			e.rejectValue("matricula", "negativevalue");
		} else if (u.getSenha() < 0) {
			e.rejectValue("senha", "negativevalue");
		}
	}

	public String getFotoBanco(byte[] imagem) {

		String fotoString = Base64.encodeBase64String(imagem);
		return fotoString;
	}
}
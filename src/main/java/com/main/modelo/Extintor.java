package com.main.modelo;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.main.GeradorQRCode;

import lombok.Data;

@Data
@Entity
@Named
@Table(name = "Extintor")
public class Extintor {

	@Id
	private int id = 0;
	private String classe = null;
	private String agente = null;
	private String dataCompra = "1999-01-01";
	private String dataTeste = "1999-01-01";
	private String volume = "1999-01-01";
	private String foto = null;
	@Transient
	private byte[] byteImageQRCode;
	@Transient
	private Image imagemQRCode;

	public Extintor(int id, String classe, String agente, String dataCompra, String dataTeste, String volume,
			String foto) {

		this.id = id;
		this.classe = classe;
		this.agente = agente;
		this.dataCompra = dataCompra;
		this.dataTeste = dataTeste;
		this.volume = volume;
		this.foto = foto;
		this.byteImageQRCode = this.getImagemQRCode(String.valueOf(id));
	}

	public Extintor() {
	}

	private byte[] getImagemQRCode(String barCode) {

		ByteArrayInputStream bais = new ByteArrayInputStream(GeradorQRCode.getByte(barCode));
		try {
			this.imagemQRCode = ImageIO.read(bais);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GeradorQRCode.getByte(barCode);
	}

}
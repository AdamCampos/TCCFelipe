package com.main.modelo;

import javax.persistence.Table;

/*@Data
@Entity
@Named*/
@Table(name = "CheckList")
public class CheckList {

	/*
	 * @Id private int id = 0; private String classe = null; private String agente =
	 * null; private String dataCompra = "1999-01-01"; private String volume =
	 * "1999-01-01"; private String foto = null;
	 * 
	 * @Transient private byte[] byteImageQRCode;
	 * 
	 * @Transient private Image imagemQRCode;
	 * 
	 * public CheckList(int id, String classe, String agente, String dataCompra,
	 * String volume, String foto) {
	 * 
	 * this.id = id; this.classe = classe; this.agente = agente; this.dataCompra =
	 * dataCompra; this.volume = volume; this.foto = foto; this.byteImageQRCode =
	 * this.getImagemQRCode(String.valueOf(id)); }
	 * 
	 * public CheckList() { }
	 * 
	 * private byte[] getImagemQRCode(String barCode) {
	 * 
	 * ByteArrayInputStream bais = new
	 * ByteArrayInputStream(GeradorQRCode.getByte(barCode)); try { this.imagemQRCode
	 * = ImageIO.read(bais); } catch (IOException e) { e.printStackTrace(); } catch
	 * (Exception e) { e.printStackTrace(); } return GeradorQRCode.getByte(barCode);
	 * }
	 */

}
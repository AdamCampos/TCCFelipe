package com.main;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GeradorQRCode {

	public static BufferedImage generateQRCodeImage(String barcodeText) throws Exception {

		QRCodeWriter barcodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);

		// log.debug("::Gerando QR " + bitMatrix.toString() + " para " + barcodeText);

		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public static byte[] getByte(String barCode) {

		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			ImageIO.write(GeradorQRCode.generateQRCodeImage(barCode), "jpg", bao);
			File outputfile = new File("src/main/resources/static/images/qr/extintorId/imageQR" + barCode + ".jpg");
			ImageIO.write(GeradorQRCode.generateQRCodeImage(barCode), "jpg", outputfile);
			// log.debug("::Gerando QR para " + barCode + " " + bao.toByteArray().length + "
			// file: "
			// + outputfile.getAbsolutePath());
		} catch (IOException e) {
			// log.debug("::Erro 1 Gerando QR " + e);
			e.printStackTrace();
		} catch (Exception e) {
			// log.debug("::Erro 2 Gerando QR " + e);
			e.printStackTrace();
		}
		return bao.toByteArray();
	}

}

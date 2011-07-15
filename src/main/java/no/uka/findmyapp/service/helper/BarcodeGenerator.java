package no.uka.findmyapp.service.helper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
/**
 * Generates QR codes with user-defined input
 * 
 * http://www.vineetmanohar.com/2010/09/java-barcode-api/
 * @author audun.sorheim
 *
 */

public class BarcodeGenerator {	
	public BarcodeGenerator() {
}

	/**
	 * Generates QR codes with a predefined size of 400x300 and custom value
	 * @param text the value to be stored in the QR code
	 * @throws WriterException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void generateQR(String text) throws WriterException, FileNotFoundException, IOException {		
		int width = 400;  
		int height = 300; // change the height and width as per your requirement  
		  
		// (ImageIO.getWriterFormatNames() returns a list of supported formats)  
		String imageFormat = "png"; // could be "gif", "tiff", "jpeg"   
		  
		BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File("qrcode.png"))); 
	}
	
	/**
	 * Generates QR codes with a custom size and custom value
	 * @param text value to be encoded
	 * @param width width of the QR code
	 * @param height height of the QR code
	 * @throws WriterException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void generateQR(String text, int width, int height) throws WriterException, FileNotFoundException, IOException {		
		// (ImageIO.getWriterFormatNames() returns a list of supported formats)  
		String imageFormat = "png"; // could be "gif", "tiff", "jpeg"   
		  
		BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File("qrcode.png"))); 
	}
  
}

package org.characterbuilder.pdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author <a href="mailto:jens.brimberg@gmail.com">Jens Brimberg</a>
 */
public class RollspelPdfManager {

	private PdfReader reader;
	private PdfStamper stamper;
	private AcroFields form;
	private ByteArrayOutputStream baos;

	/**
	 * Build the pdfManipulator.
	 *
	 * @param templatePdfFile
	 */
	public RollspelPdfManager(String templatePdfFile) throws IOException, DocumentException {

		reader = new PdfReader(templatePdfFile);
		baos = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, baos);
		form = stamper.getAcroFields();
	}

	public void writeToField(String fieldName, String newVal) throws IOException, DocumentException {
		if (fieldName != null && newVal != null) {
			form.setField(fieldName, newVal);
		}
	}

	public void writeToField(String fieldName, Integer newVal) throws IOException, DocumentException {
		if (fieldName != null && newVal != null) {
			form.setField(fieldName, newVal + "");
		}
	}

	public void writeToField(String fieldName, Double newVal) throws IOException, DocumentException {
		if (fieldName != null && newVal != null) {
			form.setField(fieldName, newVal + "");
		}
	}

	public void writeToField(Map<String, String> map) throws IOException, DocumentException {
		for (String key : map.keySet()) {
			writeToField(key, map.get(key));
		}
	}

	public ByteArrayOutputStream getOutputStream() throws DocumentException, IOException {
		//the PdfStamper.close() writes to the ByteArrayOutputStream
		stamper.close();
		return baos;
	}
}

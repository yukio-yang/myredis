package com.yukio.abc.utils.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author yukio
 * @create 2022-02-17 14:15
 * pdf模板生成
 */
public class PDFUtil {

	public void createPDF(Map<String, String> map) {
		//模板路径
		String templatePath = "D://xx.pdf";
		//生成新的文件路径
		String newPDFPath = "D://test2//xx.pdf";
		PdfReader reader;
		FileOutputStream out;
		ByteArrayOutputStream bos;
		PdfStamper stamper;

		try{
			out = new FileOutputStream(newPDFPath);
			reader = new PdfReader(templatePath);
			bos = new ByteArrayOutputStream();
			stamper = new PdfStamper(reader, bos);
			AcroFields form = stamper.getAcroFields();
			form.setField("debentures",map.get("debentures"));
			//...
			stamper.setFormFlattening(true); //如果为false那么生成的pdf文件还能编辑
			stamper.close();

			Document doc = new Document();
			PdfCopy pdfCopy = new PdfCopy(doc, bos);
			doc.open();
			PdfImportedPage importedPage = pdfCopy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
			pdfCopy.addPage(importedPage);
 			doc.close();

		}catch (IOException e){

		}catch (DocumentException e){

		}

	}
}

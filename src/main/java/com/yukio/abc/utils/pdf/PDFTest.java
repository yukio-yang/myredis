package com.yukio.abc.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.io.StringReader;

/**
 * @author yukio
 * @create 2022-02-17 14:43
 */
public class PDFTest {
	public static void main(String[] args) throws Exception{
//		test1();
//		test2();
		test10();
	}

	//简单创建pdf
	public static void test1() throws Exception{
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("D://test//aa.pdf"));
		document.open();
		document.add(new Paragraph("hello world"));
		document.close();
	}
	private static  final String FILE_DIR = "D://test//";
	//添加水印
	public static void test2() throws Exception{

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("D://test//aa.pdf"));
		document.open();
		document.add(new Paragraph("First page"));

		document.newPage();
		document.add(new Paragraph("New page"));
		writer.setPageEmpty(false);

		document.newPage();
		document.add(new Paragraph("New page"));
		document.close();
		//
		PdfReader reader = new PdfReader(FILE_DIR + "aa.pdf");
		PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "aa2.pdf"));
		Image image = Image.getInstance(FILE_DIR+ "rabbit.jpg");//"resources/xx.jpg"
		image.setAbsolutePosition(150, 400);
		PdfContentByte under = stamper.getUnderContent(1);
		under.addImage(image);

		//文字水印
		PdfContentByte over = stamper.getOverContent(2);
		over.beginText();
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
		over.setFontAndSize(bf, 18);
		over.setTextMatrix(30, 30);
		over.showTextAligned(Element.ALIGN_LEFT, "DUPLICATE", 230, 430, 45);
		over.endText();

		//背景图
		Image img2 = Image.getInstance(FILE_DIR + "rabbit.jpg");// "resources/xx.jpg"
		img2.setAbsolutePosition(0 , 0);
		PdfContentByte under2= stamper.getUnderContent(3);
		under2.addImage(img2);

		stamper.close();
		reader.close();
	}

	public static void test10() throws  Exception{
		Document doc = new Document(PageSize.LETTER);
		PdfWriter.getInstance(doc, new FileOutputStream(FILE_DIR + "aa.pdf"));
		doc.open();
		HTMLWorker worker = new HTMLWorker(doc);
		worker.parse(new StringReader("<h1><font color='red'>html</font></h1>"));
		doc.close();
	}
}

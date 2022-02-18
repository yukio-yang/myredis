package com.yukio.abc.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.yukio.abc.entity.Category;
import com.yukio.abc.mapper.CategoryMapper;
import com.yukio.abc.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.omg.CORBA.SystemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author yukio
 * @create 2022-02-17 15:55
 */
@Service
@Slf4j
public class PDFServiceImpl {

	@Autowired
	CategoryMapper categoryMapper;

	public Category getDetail(Long id) {
		// 获取业务数据
		return categoryMapper.selectById(id);
	}

	public Map<String, String> getPdfMapping(Category dto) {
		// TODO Auto-generated method stub
		// 获取pdf与数据库的数据字段映射map
		return BeanUtils.bean2Map(dto);
	}

//	@Override
	public void download(Long id, String templatePath, OutputStream out) {
		Category dto  =  getDetail(id);
		Map<String, String> fieldMapping = getPdfMapping(dto);
		String filePath;
		byte[] pdfTemplate;
		ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
		try {
			//获取模板文件路径
			filePath = ResourceUtils.getURL(templatePath).getPath();
			//获取模板文件字节数据
			pdfTemplate = IOUtils.toByteArray(new FileInputStream(filePath));
			//获取渲染数据后pdf字节数组数据
			byte[] pdfByteArray = generatePdfByTemplate(pdfTemplate, fieldMapping);
			pdfOutputStream.write(pdfByteArray);
			pdfOutputStream.writeTo(out);
			pdfOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				pdfOutputStream.close();
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

//	@Override
	//itextPdf/AcroFields完成PDF数据渲染
	public byte[] generatePdfByTemplate(byte[] pdfTemplate, Map<String, String> pdfParamMapping) {
		Assert.notNull(pdfTemplate, "template is null");
		if (pdfParamMapping == null || pdfParamMapping.isEmpty()) {
			throw new IllegalArgumentException("pdfParamMapping can't be empty");
		}

		PdfReader pdfReader = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfStamper stamper = null;
		try {
			// 读取pdf模板
			pdfReader = new PdfReader(pdfTemplate);
			stamper = new PdfStamper(pdfReader, baos);
			//获取所有表单字段数据
			AcroFields form = stamper.getAcroFields();
			form.setGenerateAppearances(true);

			// 设置
			ArrayList<BaseFont> fontList = new ArrayList<>();
			fontList.add(getMsyhBaseFont());
			form.setSubstitutionFonts(fontList);

			// 填充form
			for (String formKey : form.getFields().keySet()) {
				form.setField(formKey, pdfParamMapping.getOrDefault(formKey, StringUtils.EMPTY));
			}
			// 如果为false那么生成的PDF文件还能编辑，一定要设为true
			stamper.setFormFlattening(true);
			stamper.close();
			return baos.toByteArray();
		} catch (DocumentException | IOException e) {
//			LOGGER.error(e.getMessage(), e);
			log.error(e.getMessage());
		} finally {
			if (stamper != null) {
				try {
					stamper.close();
				} catch (DocumentException | IOException e) {
//					LOGGER.error(e.getMessage(), e);
					log.error(e.getMessage());
				}
			}
			if (pdfReader != null) {
				pdfReader.close();
			}

		}
		return new byte[2];
//		throw new SystemException("pdf generate failed");
	}

	/**
	 * 默认字体
	 *
	 * @return
	 */
	private BaseFont getDefaultBaseFont() throws IOException, DocumentException {
		return BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
	}

	/**
	 * 微软宋体字体
	 *
	 * @return
	 */
	//设定字体
	private BaseFont getMsyhBaseFont() throws IOException, DocumentException {
		try {
			return BaseFont.createFont("/msyh.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		} catch (DocumentException | IOException e) {
//			LOGGER.error(e.getMessage(), e);
			log.error(e.getMessage());
		}
		return getDefaultBaseFont();
	}

}

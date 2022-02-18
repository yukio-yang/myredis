package com.yukio.abc.utils.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * @author yukio
 * @create 2022-02-11 10:22
 */
@Slf4j
public class MyStringImageConverter implements Converter<String> {
	@Override
	public Class supportJavaTypeKey() {
		return String.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.IMAGE;
	}

	@Override
	public String convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
									GlobalConfiguration globalConfiguration) {
		throw new UnsupportedOperationException("Cannot convert images to string");
	}

	//图片失效处理
	@Override
	public CellData convertToExcelData(String value, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) throws IOException {
		//绝对路径
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/img/gril.jpg";
		path = path.replaceAll("/","\\\\").substring(1);
		log.info("path="+path);
//		File file = new File(value);
		File file = new File(path);
		if(file.exists()){
			//文件存在
//			return new CellData(FileUtils.readFileToByteArray(new File(value)));
			return new CellData(FileUtils.readFileToByteArray(new File(path)));
		}
		//直接返回文字描述
		//FileUtils.readFileToByteArray(new File("/home/test.jpg"))
		return new CellData("无法加载图片");
	}



}

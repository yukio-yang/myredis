package com.yukio.abc.utils.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yukio
 * @create 2022-02-10 14:54
 * 数字转字符串说明
 */
@Slf4j
public class SetConsumePayTypeConverter implements Converter<Integer> {
	@Override
	public Class supportJavaTypeKey() {
		return null;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return null;
	}

	@Override
	public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
		String res = cellData.getStringValue();;
		log.info("convertToJavaData:"+res);
		if("上架".equals(res)) {
			return new Integer(1);
		}else{
			return new Integer(2);
		}
	}

	@Override
	public CellData convertToExcelData(Integer value, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
//		if(StringUtils.isEmpty(value)) {
//			return null;
//		}
//		if(value.equals("1")) {
		log.info("convertToExcelData:"+value);
		if(value==1){
			return new CellData("上架");
		}else {//"2"
			return new CellData("下架");
		}
	}
}

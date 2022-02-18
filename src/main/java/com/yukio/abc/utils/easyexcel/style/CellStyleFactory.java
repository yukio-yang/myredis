package com.yukio.abc.utils.easyexcel.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author yukio
 * @create 2022-02-14 14:29
 * 样式工厂，用于组装多个样式
 */
public class CellStyleFactory {
	private static final FontRedStyle FONT_RED_STYLE = new FontRedStyle();
	private static final DefaultStyle NORMAL_STYLE_BUILD = new DefaultStyle();

	public static CellStyle style(Cell cell) {
		String value = cell.getStringCellValue();
//		if(value.startsWith("*")) {
		if(value.startsWith("上架")) {
			return FONT_RED_STYLE.createCellStyle(cell);
		}else{
			return NORMAL_STYLE_BUILD.createCellStyle(cell);
		}
	}

	public static  void release() {
		FONT_RED_STYLE.releaseStyle();
		NORMAL_STYLE_BUILD.releaseStyle();
	}
}

package com.yukio.abc.utils.easyexcel.style;

import org.apache.poi.ss.usermodel.*;

/**
 * @author yukio
 * @create 2022-02-14 14:40
 * 公共样式
 */
public class DefaultStyle extends AbstractThreadLocalStyle{

	@Override
	public CellStyle doCreteStyle(Cell cell) {
		Workbook workbook = cell.getSheet().getWorkbook();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		DataFormat dataFormat = workbook.createDataFormat();
		cellStyle.setDataFormat(dataFormat.getFormat("@"));
		return cellStyle;
	}
}

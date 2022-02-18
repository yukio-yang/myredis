package com.yukio.abc.utils.easyexcel.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author yukio
 * @create 2022-02-14 14:25
 * 具体实现的某种style（例如红色字体）
 */
public class FontRedStyle extends AbstractThreadLocalStyle {

	@Override
	public CellStyle doCreteStyle(Cell cell) {
		Workbook workbook = cell.getSheet().getWorkbook();
		Font font = workbook.createFont();
		font.setColor(Font.COLOR_RED);
		font.setFontHeightInPoints((short) 12);
		//这玩意不能创建太多，超过4030个报错（单元格样式）
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		return cellStyle;
	}
}

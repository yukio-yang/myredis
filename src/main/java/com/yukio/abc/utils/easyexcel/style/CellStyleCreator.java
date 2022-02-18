package com.yukio.abc.utils.easyexcel.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author yukio
 * @create 2022-02-14 14:15
 * style缓存接口
 */
public interface CellStyleCreator {
	CellStyle createCellStyle(Cell cell);
}

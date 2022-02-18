package com.yukio.abc.utils.easyexcel.style;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author yukio
 * @create 2022-02-14 14:16
 * 抽象style缓存实现，其他具体style继承就可以实现缓存
 */
public abstract class AbstractThreadLocalStyle implements CellStyleCreator{
	private final ThreadLocal<CellStyle> THREAD_LOCAL_STYLE = new ThreadLocal<>();
	@Override
	public CellStyle createCellStyle(Cell cell) {
		CellStyle cellStyle = THREAD_LOCAL_STYLE.get();
		if(cellStyle == null) {
			cellStyle = doCreteStyle(cell);
		}
		return cellStyle;
	}

	public abstract CellStyle doCreteStyle(Cell cell);

	public void releaseStyle() {
		THREAD_LOCAL_STYLE.remove();
	}
}

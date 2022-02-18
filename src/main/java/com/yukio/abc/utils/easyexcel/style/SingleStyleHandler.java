package com.yukio.abc.utils.easyexcel.style;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;

import java.util.List;

/**
 * @author yukio
 * @create 2022-02-14 10:38
 * easyExcel单个样式
 */
@Slf4j
public class SingleStyleHandler implements CellWriteHandler {
	@Override
	public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer integer, Integer integer1, Boolean aBoolean) {

	}

	@Override
	public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer integer, Boolean aBoolean) {

	}

	@Override//这个控制具体某一个单元格的渲染
	public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> list, Cell cell, Head head, Integer integer, Boolean aBoolean) {
		//这里可以设置其他的任意格式，这边只是处理了背景颜色
		Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
		CellStyle cellStyle = cellStyle(workbook);
		Font font = workbook.createFont();
		try{
			//单独处理的一个
			if(cell.getRowIndex() > 0 && cell.getColumnIndex() == 2) {//除了标题行外的其他第6列
				log.info("第2列单元格的值:"+cell.getStringCellValue());
				String stringCellValue = cell.getStringCellValue();
				if("上架".equals(stringCellValue)) {
//					cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
					font.setColor(Font.COLOR_RED);
					cellStyle.setFont(font);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		cell.setCellStyle(cellStyle);
	}

	//通用样式1
	public CellStyle cellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		//居中
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		//设置边框
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		return cellStyle;
	}

	///临时样式2
	public static HorizontalCellStyleStrategy defaultStrategy() {
		//表头样式策略
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		//设置表头居中对齐
		headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		//表头前景设置淡蓝色
		headWriteCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		WriteFont headWriteFont = new WriteFont();
		headWriteFont.setBold(true);
		headWriteFont.setFontName("宋体");
		headWriteFont.setFontHeightInPoints((short) 12);
		headWriteCellStyle.setWriteFont(headWriteFont);

		//内容样式策略
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		//设置背景颜色白色
		contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		//设置垂直居中对齐
		contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		//这只靠左对齐
		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
		//设置细边框
		contentWriteCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		contentWriteCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		contentWriteCellStyle.setBorderRight(BorderStyle.MEDIUM);
		contentWriteCellStyle.setBorderTop(BorderStyle.MEDIUM);

		//创建字体对象
		WriteFont contentWriteFont = new WriteFont();
		contentWriteFont.setFontName("宋体");
		contentWriteFont.setFontHeightInPoints((short) 14);
		contentWriteCellStyle.setWriteFont(contentWriteFont);

		//初始化表格样式
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
		return  horizontalCellStyleStrategy;
	}

}

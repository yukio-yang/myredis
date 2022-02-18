package com.yukio.abc.utils.easyexcel;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yukio.abc.entity.Category;
import com.yukio.abc.entity.CategoryExcel;
import com.yukio.abc.mapper.CategoryMapper;
import com.yukio.abc.utils.easyexcel.exception.ExcelDataRepeatException;
import com.yukio.abc.utils.easyexcel.listener.CateGroyListener;
import com.yukio.abc.utils.easyexcel.style.StyleHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author yukio
 * @create 2022-02-10 15:15
 */
@Slf4j
public class EasyExcelUtils {

	public static void excelExport(HttpServletResponse response, List<Category> list, String title) throws IOException {
//		List<LinkedHashMap<String, Object>> userOrgs = null;//ModuleUtil.getManagedOrg();
//		List<HksplatformsetCardManage> list = cardManageMapper.getAllDataByExcel(userOrgs);
//		List<Category> list = null;
		log.error("easyExcel_error:start");
		String time = DateUtil.format(new Date(), "yyyyMMdd");
//		String fileNameT = "餐卡管理" + time;
		String fileNameT = title + time;
//		try {
			WriteCellStyle headWriteCellStyle = new WriteCellStyle();
			//设置背景颜色IndexedColors.WHITE.getIndex()
			headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			//设置头字体
			WriteFont headWriteFont = new WriteFont();
			headWriteFont.setFontHeightInPoints((short)13);
			headWriteFont.setBold(true);
			headWriteCellStyle.setWriteFont(headWriteFont);
			//设置头居中
			headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

			//内容策略
			WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
			//设置 水平居中
			contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
			//设置 垂直居中
			contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");
			// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
			String fileName = URLEncoder.encode(fileNameT, "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			// 这里需要设置不关闭流
//			EasyExcel.write(response.getOutputStream(), Category.class)
//					.registerWriteHandler(horizontalCellStyleStrategy).sheet("sheet1")
//					.doWrite(list);
		EasyExcel.write(response.getOutputStream(), Category.class)//换一个样式
				.registerWriteHandler(new StyleHandler()).sheet("sheet1")//
				.doWrite(list);
		log.error("easyExcel_error:end");
//		} catch (Exception e) {
//			log.error("easyExcel_error:"+e.getMessage());
//			// 重置response
//			response.reset();
//			response.setContentType("application/json");
//			response.setCharacterEncoding("utf-8");
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("status", "failure");
//			map.put("message", "下载文件失败" + e.getMessage());
//			response.getWriter().println(JSON.toJSONString(map));
//		}
	}

	@Autowired
	private CategoryMapper categoryMapper;

	public void excelImport(MultipartFile file) throws IOException,ExcelDataRepeatException {
		//没有重复的才执行下面的插入操作
		InputStream inputStream = new BufferedInputStream(file.getInputStream());
		//实例化实现了AnalysisEventListener接口的类(cardManageMapper就是一个mapper接口，需要使用里面的insert方法)
		CateGroyListener excelListener = new CateGroyListener(categoryMapper);
		ExcelReader reader = new ExcelReader(inputStream,null,excelListener);
		//读取信息
		reader.read(new Sheet(1,1,CategoryExcel.class));
	}


//	public static void readExcel(InputStream inputStream, int headLineNum, ReadListener readListener ) throws IOException,ExcelDataRepeatException {
//		ExcelReaderBuilder excelBuild = EasyExcelFactory.read(inputStream, readListener);
//		excelBuild.headRowNumber(headLineNum);
//		ExcelReader reader = excelBuild.build();
//		reader.read();
//	}
	///第二种----------------------------------------------------------
	public static void writeExcel(HttpServletResponse response, List<? extends Object> data, String fileName, String sheetName, Class clazz) throws Exception {
		//表头样式
		WriteCellStyle headWriteCellStyle = new WriteCellStyle();
		//设置表头居中对齐
		headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		//内容样式
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		//设置内容靠左对齐
		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
		EasyExcel.write(getOutputStream(fileName, response), clazz).excelType(ExcelTypeEnum.XLSX).sheet(sheetName).registerWriteHandler(horizontalCellStyleStrategy).doWrite(data);
	}

	private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf8");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
		return response.getOutputStream();
	}

	//模板导出
	public static void downloadTemplate(HttpServletResponse response) throws Exception{
		String fileName = "导入目录模板";
		String sheetName = "导入目录模板";
		List<Category> userList = new ArrayList<>();
		Category category = new Category();
		category.setCreateTime(new Date());//LocalDateTime.now()
		category.setCreator(1l);
		category.setLevel(2);
		category.setName("小明");
		category.setParentId(1l);
		category.setPath("/aaa");
		category.setState(1);
		category.setUpdateTime(LocalDateTime.now());
		userList.add(category);
//		try {
		EasyExcelUtils.writeExcel(response, userList, fileName, sheetName, Category.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	private static final int MAX_USER_IMPORT = 1000;

	//单sheet导入
	public static void readExcel(MultipartFile file){
		List<Category> categoryList = null;
		// 1.excel同步读取数据
		try {
			categoryList = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(Category.class).sheet().doReadSync();
			for (Category aa:
				 categoryList) {
				log.info("导入数据:"+aa);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 2.检查是否大于1000条
//		if (categoryList.size() > MAX_USER_IMPORT) {
//			throw new GlobalException(CodeMsg.OVER_MAX_USER_IMPORT_LIMIT.fillArgs(MAX_USER_IMPORT));
//		}
		// 3.导入校验所有行列格式
//		checkImportData(userExcelList);
		// 4.将 userExcelList 转成 userList
//		List<User> userList = userExcelList2UserList(userExcelList);
		// 5.入库操作
//		userService.batchInsertOrUpdate(userList);
	}

	//多sheet导入
	public static void readExcelByMoreSheet(MultipartFile file){
		List<Category> userExcelList = new ArrayList<>();
		// 1.excel同步读取数据
		ExcelReader excelReader = null;
		try {
			excelReader = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(Category.class).build();
			List<ReadSheet> sheetList = excelReader.excelExecutor().sheetList();
			List<Category> childUserExcelList = new ArrayList<>();
			for (ReadSheet sheet : sheetList) {
				childUserExcelList = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(Category.class).sheet(sheet.getSheetNo()).doReadSync();
			}
			userExcelList.addAll(childUserExcelList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null != excelReader) {
				excelReader.finish();//关流
			}
		}
		// 2.检查是否大于1000条
//		if (userExcelList.size() > MAX_USER_IMPORT) {
//			throw new GlobalException(CodeMsg.OVER_MAX_USER_IMPORT_LIMIT.fillArgs(MAX_USER_IMPORT));
//		}
		// 3.导入校验所有行列格式
//		checkImportData(userExcelList);
		// 4.将 userExcelList 转成 userList
//		List<User> userList = userExcelList2UserList(userExcelList);
		// 5.入库操作
//		userService.batchInsertOrUpdate(userList);
	}

	//-----------------------------------------------------------------

	//模板填充
	public static void moduleFill(HttpServletResponse response, List<Category> data, String templateFileName) throws Exception {
		ExcelWriter excelWriter = null;
		Map<String, String> header = new HashMap<>(2);
		header.put("proname","测试填充项目名");
		header.put("modelname","测试填充模块名");
		// 这里代码在准备需要填充的数据 list 和 header, 这里不做展示了
// 模板读取
		String exportName = "环境风险放射性数据导出表";
		try {
			OutputStream outputStream = response.getOutputStream();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("utf-8");
			// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
			String fileName = URLEncoder.encode(exportName, "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
			excelWriter = EasyExcel.write(outputStream)//.registerConverter(new SetConsumePayTypeConverter())
					.withTemplate(templateFileName)
					.build();
			WriteSheet writeSheet = EasyExcel.writerSheet().build();
//			WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo).build();
			FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
			excelWriter.fill(data, fillConfig, writeSheet);
			excelWriter.fill(header, writeSheet);

		} catch (Exception e) {
			log.error("环境风险放射性数据导出异常:", e);
			throw new Exception("环境风险放射性数据导出异常");
		} finally {
			excelWriter.finish();
//			try {
//				if(null != inputStream) {
//					inputStream.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}
	//模板填充

}

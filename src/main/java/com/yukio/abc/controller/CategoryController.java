package com.yukio.abc.controller;


import com.yukio.abc.entity.vo.CategoryVo;
import com.yukio.abc.service.ICategoryService;
import com.yukio.abc.utils.easyexcel.exception.ExcelDataRepeatException;
import com.yukio.common.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 商品分类 前端控制器
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@Api(value = "商品分类")
@Controller
@RequestMapping("/abc/category")
@Slf4j
public class CategoryController {
	@Autowired
	ICategoryService iCategoryService;

	@ApiOperation(value = "商品分类分页查询", notes = "传入条件是商品分类对象")
	@PostMapping("/selectPage")
	@ResponseBody
	public R select1(@ApiParam(value="商品分类分页对象")@RequestBody CategoryVo categoryVo) {
//		log.info("-----user.dir---:"+System.getProperty("user.dir"));
//		log.info("-----reource.dir---:"+Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/img/girl.jpg");
		return R.ok("请求成功", iCategoryService.select1(categoryVo));
	}


	@PostMapping("/importCategory")
	@ApiOperation("导入")
	public R importCardManage(@RequestParam("file") MultipartFile file) throws IOException {
		try{
			iCategoryService.excelImport(file);
			return R.ok();
		}catch (ExcelDataRepeatException e) {//表格数据有重复的

			return R.error(HttpStatus.FAILED_DEPENDENCY, e.getMessage());
		}catch (Exception e) {
			log.info(this.getClass().getName()+".importCardManage方法调用异常");
			e.printStackTrace();
//			return R.build(Type.EXCEPTION);
			return R.error(HttpStatus.BAD_REQUEST, "系统错误,请联系管理员");
		}

	}

	@GetMapping("/exportCategory")
	@ApiOperation("导出")
	public void exportCardManage(HttpServletResponse response) throws IOException {
		iCategoryService.excelExport(response);
	}

	/**
	 * 导出
	 */
	@GetMapping("/excel/export")
	@ResponseBody
	public R exportData(HttpServletResponse response) {
		try{
			iCategoryService.exportData(response);
			return R.ok("下载成功");
		}catch (Exception e){
			return R.error(HttpStatus.BAD_REQUEST, "下载失败");
		}

	}

	/**
	 * 导入模板
	 */
	@PostMapping("/excel/import")
	@ResponseBody
	public R readExcel(@RequestParam("file") MultipartFile file) throws IOException{
		try{
			iCategoryService.readExcel(file);
			return R.ok("导入成功");
		}catch (Exception e){
			return R.error(HttpStatus.BAD_REQUEST, "导入失败");
		}
	}

	/**
	 * 导出模板
	 */
	@GetMapping("/excel/exportModel")
	@ResponseBody
	public void exportDataModel(HttpServletResponse response) throws Exception {
		iCategoryService.exportDataModel(response);
	}


	/**
	 * 导出填充数据模板
	 */
	@GetMapping("/excel/exportFillModel")
	@ResponseBody
	public void exportFillModel(HttpServletResponse response) throws Exception {
		iCategoryService.exportFillModel(response);
	}


}

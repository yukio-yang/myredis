package com.yukio.abc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yukio.abc.entity.Category;
import com.yukio.abc.entity.vo.CategoryVo;
import com.yukio.common.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 * 商品分类 服务类
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
public interface ICategoryService extends IService<Category> {
	IPage select1(CategoryVo categoryVo);
	void excelImport(MultipartFile file) throws IOException;
	void excelExport(HttpServletResponse response) throws IOException;
	void exportData(HttpServletResponse response) throws Exception;
	void readExcel(MultipartFile file) throws IOException;
	void exportDataModel(HttpServletResponse response) throws Exception;
	void exportFillModel(HttpServletResponse response) throws Exception;


}

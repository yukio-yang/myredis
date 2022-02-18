package com.yukio.abc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukio.abc.entity.Category;
import com.yukio.abc.entity.vo.CategoryVo;
import com.yukio.abc.mapper.CategoryMapper;
import com.yukio.abc.service.ICategoryService;
import com.yukio.abc.utils.easyexcel.EasyExcelUtils;
import com.yukio.common.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 商品分类 服务实现类
 * </p>
 *
 * @author yukio
 * @since 2022-02-08
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
	@Autowired
	public CategoryMapper categoryMapper;

	@Override
	public IPage select1(CategoryVo category) {
		return lambdaQuery().like(StrUtil.isNotEmpty(category.getName()), Category::getName, category.getName())
				.eq(StrUtil.isNotEmpty(category.getId().toString()), Category::getId, category.getId())
				.page(new Page<>(category.getPageNum(), category.getPageSize()));
	}

	@Override
	public void excelImport(MultipartFile file) throws IOException{
		new EasyExcelUtils().excelImport(file);
	}

	@Override
	public void excelExport(HttpServletResponse response) throws IOException{
		List<Category> list = lambdaQuery().page(new Page<>(1, 2)).getRecords();
		log.warn("查询结果："+list);
		EasyExcelUtils.excelExport(response,list, "目录清单");
	}

	@Override
	public void exportData(HttpServletResponse response) throws Exception{
		List<Category> list = lambdaQuery().page(new Page<>(1, 2)).getRecords();
		log.warn("查询结果："+list);
		EasyExcelUtils.writeExcel(response,list,"目录清单","目录清单",Category.class);
	}

	@Override
	public void readExcel(MultipartFile file) throws IOException {
		EasyExcelUtils.readExcel(file);
	}

	@Override
	public void exportDataModel(HttpServletResponse response) throws Exception{
		EasyExcelUtils.downloadTemplate(response);
	}

	@Override
	public void exportFillModel(HttpServletResponse response) throws Exception {
		List<Category> list = lambdaQuery().page(new Page<>(1, 2)).getRecords();
		log.warn("查询结果："+list);
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"templates/model_fill.xlsx";
		path = path.replaceAll("/","\\\\").substring(1);
		//工程路径下有个模板文件
		//D:/github_project/project_2/hou/myredis/src/main/resources\templates\model_fill.xlsx
		//templates/model_fill.xlsx
//		String modelPath = "classpath:static/XXX/xxxxxx.xlsx";
//		String modelPath = "classpath:templates/model_fill.xlsx";
		//读取模板文件
//		InputStream inputStream = ResourceUtils.getURL(modelPath).openStream();
		EasyExcelUtils.moduleFill(response,list,path);
	}

}

package com.yukio.abc.controller;


import com.yukio.abc.entity.vo.CategoryVo;
import com.yukio.abc.service.ICategoryService;
import com.yukio.common.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class CategoryController {
	@Autowired
	ICategoryService iCategoryService;

	@ApiOperation(value = "商品分类分页查询", notes = "传入条件是商品分类对象")
	@PostMapping("/selectPage")
	@ResponseBody
	public R select1(@ApiParam(value="商品分类分页对象")@RequestBody CategoryVo categoryVo) {
		return R.ok("请求成功", iCategoryService.select1(categoryVo));
	}
}

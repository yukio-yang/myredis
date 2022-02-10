package com.yukio.abc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yukio.abc.entity.Account;
import com.yukio.abc.entity.Category;
import com.yukio.abc.entity.vo.AccountVo;
import com.yukio.abc.entity.vo.CategoryVo;
import com.yukio.abc.mapper.AccountMapper;
import com.yukio.abc.mapper.CategoryMapper;
import com.yukio.abc.service.ICategoryService;
import com.yukio.common.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
}

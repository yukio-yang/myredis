package com.yukio.abc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yukio.abc.entity.Category;
import com.yukio.abc.entity.vo.CategoryVo;
import com.yukio.common.service.IService;

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

}

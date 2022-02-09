package com.yukio.abc.service.impl;

import com.yukio.abc.entity.Category;
import com.yukio.abc.mapper.CategoryMapper;
import com.yukio.abc.service.ICategoryService;
import com.yukio.common.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

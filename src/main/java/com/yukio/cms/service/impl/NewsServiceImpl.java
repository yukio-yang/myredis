package com.yukio.cms.service.impl;

import com.yukio.cms.entity.News;
import com.yukio.cms.mapper.NewsMapper;
import com.yukio.cms.service.INewsService;
import com.yukio.common.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 * 自媒体图文内容信息表 服务实现类
 * </p>
 *
 * @author yukio
 * @since 2020-06-18
 */
@Service
@CacheConfig(cacheNames = "com.yukio.cms.news", cacheManager = "redisCacheManager")
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements INewsService {

    @Override
    @Cacheable(key = "#id") //适用查询
    public News getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    @Transactional
//    @CachePut(key = "#news.id")
    public News saveOrUpdate(News news) {
        super.saveOrUpdate(news);
//        int i =  100/0;
        return news;
    }

    @Override
    @CachePut(key = "#news.id")
    public News updateById(News news) {
        return super.updateById(news);
    }

    @Override
    @CacheEvict(key = "#id")
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

}

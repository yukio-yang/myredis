package com.yukio.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yukio
 * @create 2020-06-17 22:46
 * 分页
 */

@Configuration
public class MyBatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        //原本还要在MybatisSqlSessionFactoryBean工厂内注入，但是依赖那点创建的时候就会初始化这个工厂
        PaginationInterceptor interceptor = new PaginationInterceptor();
        //设置请求的页面大于最大页面后的操作，true调回到首页  false继续请求，默认false
        interceptor.setOverflow(true);
        interceptor.setDialectType("mysql");
        //设置最大单页限制数量，默认500条， -1不受限制
        interceptor.setLimit(20);//500
        return interceptor;
    }
}

package com.yukio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yukio
 * @create 2022-01-25 11:06
 */
@SpringBootApplication
@EnableTransactionManagement //开启事物管理
@EnableCaching //开启缓存
@MapperScan("com.yukio.*.mapper")
public class SpringbootApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}
}

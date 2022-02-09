package com.yukio;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author yukio
 * @create 2022-02-08 9:47
 * mybatis自动生成实体类
 */
public class MyBatisPlusGenerator {

	/**
	 * 读取控制台内容
	 */
	public static String scannr(String tip) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("请输入"+tip+":");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StringUtils.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的"+ tip + "!");
	}

	public static void main(String[] args) {
		//代码生成器
		AutoGenerator mpg = new AutoGenerator();
		//全局配置
		GlobalConfig gc = new GlobalConfig();
		String projectPath = System.getProperty("user.dir");
		gc.setOutputDir(projectPath + "/src/main/java");
		gc.setAuthor("yukio");
		gc.setOpen(false);
//        gc.setSwagger2(true); //实体注解生成
		gc.setBaseResultMap(true);
		gc.setBaseColumnList(true);
		mpg.setGlobalConfig(gc);

		//数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc.setUrl("jdbc:mysql://localhost:3306/test22?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimeZone=GMT");
		dsc.setDriverName("com.mysql.jdbc.Driver");
		dsc.setUsername("root");
		dsc.setPassword("111111");
		mpg.setDataSource(dsc);

		//包配置
		PackageConfig pc = new PackageConfig();
		pc.setModuleName(scannr("模块名"));
		pc.setParent("com.yukio");
		mpg.setPackageInfo(pc);

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		// 如果模板引擎是 freemarker
		String templatePath = "/templates/mapper.xml.ftl";
		// 如果模板引擎是 velocity
		// String templatePath = "/templates/mapper.xml.vm";

		// 自定义输出配置
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig(templatePath) {
			@Override
			public String outputFile(TableInfo tableInfo) {
				//自定义输入文件名称
				return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
						+ "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
			}
		});
		cfg.setFileOutConfigList(focList);
		mpg.setCfg(cfg);
		mpg.setTemplate(new TemplateConfig().setXml(null));

		//策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel);
		strategy.setColumnNaming(NamingStrategy.underline_to_camel);
		strategy.setEntityLombokModel(true);
		strategy.setInclude(scannr("表名").split(","));//scanner("表名，多个英文逗号分割").split(",")
		strategy.setSuperServiceClass("com.yukio.common.service.IService");
		strategy.setSuperServiceImplClass("com.yukio.common.service.impl.ServiceImpl");

		//Controller层配置
		strategy.setSkipView(false);//要不要跳过cotroller层
		strategy.setControllerMappingHyphenStyle(true);//requestMappinig对应地址
		strategy.setRestControllerStyle(false);

		strategy.setTablePrefix(pc.getModuleName()+ "_");
		mpg.setStrategy(strategy);
		// 选择freemarker引擎需要指定如下加，注意pom依赖必须有
		mpg.setTemplateEngine(new FreemarkerTemplateEngine());
		mpg.execute();
	}

}

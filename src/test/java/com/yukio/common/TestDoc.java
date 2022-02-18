package com.yukio.common;

import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yukio
 * @create 2022-02-15 16:44performRequest
 * es添加文档
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDoc {

	@Autowired
	@Qualifier("restHighLevelClient")
	RestHighLevelClient client;

	//添加文档 格式如下： PUT /{index}/{type}/{id} { "field": "value", ... }  (如果不指定id，ES会自动生成)
	@Test
	public void testAddDoc() throws IOException {
		//准备json数据
		Map<String, Object> jsonMap = new HashMap<>();
		jsonMap.put("name", "spring cloud实战");
		jsonMap.put("description", "本课程主要从四个章节进行讲解： 1.微服务架构入门 2.spring cloud 基础入门 3.实战Spring Boot 4.注册中心eureka。");
		jsonMap.put("studymodel", "201001");
		SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
		jsonMap.put("timestamp", dateFormat.format(new Date()));
		jsonMap.put("price", 5.6f);
		//索引请求对象
		IndexRequest indexRequest = new IndexRequest("xc_course","doc","kdGV8ybAR2--bAovGjQddQ");
		//指定索引文档内容
		indexRequest.source(jsonMap);
		//索引响应对象
		IndexResponse indexResponse = client.index(indexRequest);
		//获取响应结果
		DocWriteResponse.Result result = indexResponse.getResult();
		System.out.println(result);
	}

	//查询文档 格式如下： GET /{index}/{type}/{id}
	@Test
	public void getDoc() throws IOException {
		GetRequest getRequest = new GetRequest( "xc_course", "doc", "kdGV8ybAR2--bAovGjQddQ");
		GetResponse getResponse = client.get(getRequest);
		boolean exists = getResponse.isExists();
		Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
		System.out.println(sourceAsMap);
	}

	//局部更新文档 post: http://localhost:9200/xc_test/doc/3/_update
	@Test
	public void updateDoc() throws IOException {
		UpdateRequest updateRequest = new UpdateRequest("xc_course", "doc","kdGV8ybAR2--bAovGjQddQ");
		Map<String, String> map = new HashMap<>();
		map.put("name", "spring cloud实战2");
		updateRequest.doc(map);
		UpdateResponse update = client.update(updateRequest);
		RestStatus status = update.status();
		System.out.println(status);
	}

	//根据id删除文档 DELETE /{index}/{type}/{id}
	@Test
	public void testDelDoc() throws IOException {
		//删除文档id
		String id = "J1aLbhuTuGsNS6s9E4HzA";
		//删除索引请求对象
		DeleteRequest deleteRequest = new DeleteRequest("xc_course","doc",id);
		//响应对象
		DeleteResponse deleteResponse = client.delete(deleteRequest);
		//获取响应结果
		DocWriteResponse.Result result = deleteResponse.getResult();
		System.out.println(result);
	}
}

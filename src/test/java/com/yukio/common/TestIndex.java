package com.yukio.common;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

//import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;

/**
 * @author yukio
 * @create 2022-02-15 16:38
 * es创建索引库
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestIndex {

	@Autowired
	@Qualifier("restHighLevelClient")
	RestHighLevelClient client;

	@Autowired
	RestClient restClient;
	//创建索引库 put http://localhost:9200/索引名称
	@Test
	public void testCreateIndex() throws IOException {
		//创建索引请求对象，并设置索引名称
		CreateIndexRequest createIndexRequest = new CreateIndexRequest("xc_course");
		//设置索引参数
		createIndexRequest.settings(Settings.builder().put("number_of_shards",1) .put("number_of_replicas",0));
		//设置映射
		createIndexRequest.mapping("doc",
				" {\n" +
						" \t\"properties\": {\n" +
							" \"name\": {\n" +
								" \"type\": \"text\",\n" +
								" \"analyzer\":\"ik_max_word\",\n" +
								" \"search_analyzer\":\"ik_smart\"\n" +
							" },\n" +
							" \"description\": {\n" +
								" \"type\": \"text\",\n" +
								" \"analyzer\":\"ik_max_word\",\n" +
								" \"search_analyzer\":\"ik_smart\"\n" +
							" },\n" +
							" \"studymodel\": {\n" +
								" \"type\": \"keyword\"\n" +
							" },\n" +
							" \"price\": {\n" +
								" \"type\": \"float\"\n" +
							" },\n" +
							" \"timestamp\": {\n" +
								" \"type\": \"date\",\n" +
								" \"format\": \"yyyy‐MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\"\n" +
							" }\n" +
						" }\n" +
						"}", XContentType.JSON);
		//创建索引操作客户端
		IndicesClient indices = client.indices();
		//创建响应对象
		CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);
		//得到响应结果
		boolean acknowledged = createIndexResponse.isAcknowledged();
		System.out.println(acknowledged);
	}
	//删除索引库 DELETE /{index}
	@Test
	public void testDeleteIndex() throws IOException {
		//删除索引请求对象
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("xc_course");
		//删除索引
		DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest);
		//删除索引响应结果
		boolean acknowledged = deleteIndexResponse.isAcknowledged();
		System.out.println(acknowledged);
	}
}

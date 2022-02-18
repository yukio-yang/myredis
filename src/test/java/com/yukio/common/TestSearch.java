package com.yukio.common;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author yukio
 * @create 2022-02-16 10:47
 * 测试各种搜索
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSearch {

	@Autowired
	@Qualifier("restHighLevelClient")
	RestHighLevelClient client;

	@Autowired
	@Qualifier("restClient")
	RestClient restClient;

	//搜索type下的全部记录
	@Test
	public void testSearchAll() throws IOException {
		SearchRequest searchRequest = new SearchRequest("xc_course");
		searchRequest.types("doc");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		//2-设置精确查询termquery
//		searchSourceBuilder.query(QueryBuilders.termQuery("name", "spring"));
		//3-精确查询termsquery之多个id匹配
//		searchSourceBuilder.query(QueryBuilders.termsQuery("_id", Arrays.asList("1","2")));
		//4-全文检索(match query与Term query区别是match query在搜索前先将搜索关键字分词，再拿各各词语去索引中搜索)
		//4-atchQuery匹配关键字 or满足分词的任意一个  and满足分词的所有
//		searchSourceBuilder.query(QueryBuilders.matchQuery("description", "spring开发").operator(Operator.OR));
		//5-匹配关键字的几个(例如下面text可以拆成3个词，3*0.8=2 匹配上2个就行)
//		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("description", "spring开发框架")
//				.minimumShouldMatch("80%");//设置匹配占比
//		searchSourceBuilder.query(matchQueryBuilder);
		//6-multi_query多字段匹配(上面是单字段匹配)
//		MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("spring框架", "name", "description").minimumShouldMatch("50%");
//		multiMatchQueryBuilder.field("name", 10);//提升该字段的权重，说白了就是这个为主先展示，其实都能查出来
//		searchSourceBuilder.query(multiMatchQueryBuilder);
		//7-BoolQuery布尔查询 must=and should=or must_not=!
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		boolQueryBuilder.must(multiMatchQueryBuilder);
//		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "201001");
//		boolQueryBuilder.must(termQueryBuilder);
//		searchSourceBuilder.query(boolQueryBuilder);
		//8-过滤器(判断的是文档是否匹配，不去计算和判断文档的匹配度得分，所以过滤器性能比查询要高)
		//8-注意：range和term一次只能对一个Field设置范围过虑。
//		boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "201001"))
//				.filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
//		searchSourceBuilder.query(boolQueryBuilder);
		//9-排序
//		searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description", "price"}, new String[]{});
//		BoolQueryBuilder boolQueryBuilder_sort = QueryBuilders.boolQuery();
//		boolQueryBuilder_sort.must(multiMatchQueryBuilder);
//		boolQueryBuilder_sort.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
//		searchSourceBuilder.query(boolQueryBuilder_sort);
//		searchSourceBuilder.sort("studymodel",SortOrder.DESC);
//		searchSourceBuilder.sort("price", SortOrder.ASC);
		//10-高亮
		searchSourceBuilder.fetchSource(new String[]{"name", "studymodel", "description", "price"}, new String[]{});
		searchRequest.source(searchSourceBuilder);
		MultiMatchQueryBuilder multiMatchQueryBuilder_highlig = QueryBuilders.multiMatchQuery("开发框架", "name", "description").minimumShouldMatch("50%").type(MultiMatchQueryBuilder.Type.BEST_FIELDS);
		multiMatchQueryBuilder_highlig.field("name", 10);
		BoolQueryBuilder boolQueryBuilder_highlig = QueryBuilders.boolQuery();
		boolQueryBuilder_highlig.must(multiMatchQueryBuilder_highlig);
		boolQueryBuilder_highlig.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
		searchSourceBuilder.query(boolQueryBuilder_highlig);
		searchSourceBuilder.sort("price", SortOrder.ASC);
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<tag>");
		highlightBuilder.postTags("</tag>");
		highlightBuilder.fields().add(new HighlightBuilder.Field("name"));
		highlightBuilder.fields().add(new HighlightBuilder.Field("description"));
		searchSourceBuilder.highlighter(highlightBuilder);

		//1-下面可以设置分页(分页起始)
//		searchSourceBuilder.from(0);
		//1-每页展示个数
//		searchSourceBuilder.size(1);

		//source源字段过虑
//		searchSourceBuilder.fetchSource(new String[]{"name","studymodel"}, new String[]{});
//		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest);
		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			String index = hit.getIndex();
			String type = hit.getType();
			String id = hit.getId();
			float score = hit.getScore();
			String sourceAsString = hit.getSourceAsString();
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			String name = (String) sourceAsMap.get("name");
			String studymodel = (String) sourceAsMap.get("studymodel");
			//如果源字段没拿属性，即便有值也取不出来
			String description = (String) sourceAsMap.get("description");
			System.out.println(name);
			System.out.println(studymodel);
			System.out.println(description);
			System.out.println(sourceAsMap.get("price"));
			//下面是取出高亮的数据
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			if(highlightFields != null) {
//				HighlightField highlightField = highlightFields.get("name");
//				if(highlightField != null) {
//					Text[] fragments = highlightField.getFragments();
//					StringBuffer buffer = new StringBuffer();
//					for (Text fragment : fragments) {
//						buffer.append(fragment.toString());
//					}
//					hightStr = buffer.toString();
//				}
				String hightStr = "";
				Set<String> strings = highlightFields.keySet();
				for (String string : strings) {
					HighlightField highlightField = highlightFields.get(string);
									if(highlightField != null) {
						Text[] fragments = highlightField.getFragments();
						StringBuffer buffer = new StringBuffer();
						for (Text fragment : fragments) {
							buffer.append(fragment.toString());
						}
						hightStr += buffer.toString();
					}
				}
				System.out.println("highlig: "+hightStr);
			}

		}
	}

}

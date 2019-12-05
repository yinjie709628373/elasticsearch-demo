package com.example.demo.service.impl;

import com.example.demo.dao.AnswerMapper;
import com.example.demo.entity.Answer;
import com.example.demo.service.AnswerService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AnswerServiceImpl implements AnswerService {

  @Autowired
  private AnswerMapper mapper;
  @Autowired
  private TransportClient transportClient;


  @Override
  public int insert(Answer answer) {
    answer.setId(UUID.randomUUID().toString());
    answer.setContents("测试555");
    answer.setCreateTime(new Date());
    answer.setTitles("测试titleFive");
    mapper.save(answer);
    return 0;
  }

  @Override
  public List<Answer> findByKeys(String keys) {
//// 构建查询内容
    List<Answer> list = new ArrayList<Answer>();
    Map<String, Object> map = new HashMap<>();
    BoolQueryBuilder bqb = QueryBuilders.boolQuery();
    if (!StringUtils.isEmpty(keys)) {
      //TODO 或者条件还不清楚
      String[] str = {"contents", "titles"};
      bqb.should(QueryBuilders.multiMatchQuery("*" + keys + "*", str).slop(50));
    }
    Iterable<Answer> searchResult = mapper.search(bqb);
    Iterator<Answer> iterable = searchResult.iterator();
    while (iterable.hasNext()) {
      list.add(iterable.next());
    }
    return list;
  }

  @Override
  public Page<Answer> findPageByKeys(String keys, Integer pageSize, Integer pageNum) {
    return null;
  }

  @Override
  public List<Answer> highlightBykeys(String keys) {
    HighlightBuilder highlightBuilder = new HighlightBuilder();
    //高亮显示规则
    highlightBuilder.preTags("<span style='color:red'>");
    highlightBuilder.postTags("</span>");
    //指定高亮字段
    highlightBuilder.field("contents");
    highlightBuilder.field("titles");
    //添加查询的字段内容
    String[] fileds = {"contents", "titles"};
    QueryBuilder matchQuery = QueryBuilders.multiMatchQuery(keys, fileds);
    //搜索数据
    SearchResponse response = transportClient.prepareSearch("adminanswer")
        .setQuery(matchQuery)
        .highlighter(highlightBuilder)
        .execute().actionGet();

    SearchHits searchHits = response.getHits();
    System.out.println("记录数-->" + searchHits.getTotalHits());

    //List<String> list = new ArrayList<>();
    List<Answer> list = new ArrayList<>();

    for (SearchHit hit : searchHits) {
      Answer entity = new Answer();
      Map<String, Object> entityMap = hit.getSourceAsMap();
      //高亮字段
      if (!StringUtils.isEmpty(hit.getHighlightFields().get("contents"))) {
        Text[] text = hit.getHighlightFields().get("contents").getFragments();
        entity.setContents(
            String.valueOf(entityMap.get("contents")).replace(keys, text[0].toString()));
      } else {
        entity.setContents(String.valueOf(entityMap.get("contents")));
      }

      if (!StringUtils.isEmpty(hit.getHighlightFields().get("titles"))) {
        Text[] text = hit.getHighlightFields().get("titles").getFragments();
        entity
            .setTitles(String.valueOf(entityMap.get("titles")).replace(keys, text[0].toString()));
      } else {
        entity.setTitles(String.valueOf(entityMap.get("titles")));
      }
      if (!CollectionUtils.isEmpty(entityMap)) {
        if (!StringUtils.isEmpty(entityMap.get("id"))) {
          entity.setId(String.valueOf(entityMap.get("id")));
        }
        if (!StringUtils.isEmpty(entityMap.get("language"))) {
          entity.setCreateTime(new Date(Long.valueOf(String.valueOf(entityMap.get("language")))));
        }
      }

      list.add(entity);
    }
    return list;
  }

}

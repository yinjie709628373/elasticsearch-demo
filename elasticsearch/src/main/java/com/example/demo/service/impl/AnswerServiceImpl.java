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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {

  @Autowired
  private AnswerMapper mapper;


  @Override
  public int insert(Answer answer) {
    answer.setId(UUID.randomUUID().toString());
    answer.setContents("测试");
    answer.setCreateTime(new Date());
    answer.setTitles("测试title");
    mapper.save(answer);
    return 0;
  }

  @Override
  public List<Answer> findByKeys(String keys) {
//// 构建查询内容
//    QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(keys);
//    // 查询的字段
//    queryBuilder.field("titles").field("contents");
//    Iterable<Answer> searchResult = mapper.search(queryBuilder);
    List<Answer> list = new ArrayList<Answer>();
    Map<String, Object> map = new HashMap<>();
    BoolQueryBuilder bqb = QueryBuilders.boolQuery();
    //TODO 或者条件还不清楚

    bqb.should(QueryBuilders.matchQuery("contents", keys));
    bqb.should(QueryBuilders.matchQuery("titles", keys));
    //高亮未设置
//    HighlightBuilder highlightBuilder=new HighlightBuilder()
//        .field("contents")
//        .preTags("<span style='color:red;font-weight:bold;font-size:15px;'>").postTags("</span>");

    Iterable<Answer> searchResult = mapper.search(bqb);
    Iterator<Answer> iterable = searchResult.iterator();
    while (iterable.hasNext()) {
      list.add(iterable.next());
    }
    return list;
  }

}

package com.example.demo.service;

import com.example.demo.entity.Answer;
import java.util.List;
import org.springframework.data.domain.Page;

public interface AnswerService {

  int insert(Answer record);

  List<Answer> findByKeys(String keys);

  Page<Answer> findPageByKeys(String keys, Integer pageSize, Integer pageNum);

  /**
   * 高亮获取
   */
  List<Answer> highlightBykeys(String keys);

}

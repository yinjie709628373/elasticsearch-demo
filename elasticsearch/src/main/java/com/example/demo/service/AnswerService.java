package com.example.demo.service;

import com.example.demo.entity.Answer;
import java.util.List;

public interface AnswerService {

  int insert(Answer record);

  List<Answer> findByKeys(String keys);

}

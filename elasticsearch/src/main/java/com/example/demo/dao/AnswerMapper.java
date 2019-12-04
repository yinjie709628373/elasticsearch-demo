package com.example.demo.dao;

import com.example.demo.entity.Answer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface AnswerMapper extends ElasticsearchRepository<Answer, String> {

}

package com.example.demo.controller;

import com.example.demo.entity.Answer;
import com.example.demo.service.AnswerService;
import java.util.List;
import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es")
public class TestController {

  @Autowired
  private TransportClient transportClient;
  @Autowired
  private AnswerService answerService;

  @RequestMapping("add")
  public String add() {
    return answerService.insert(new Answer()) + "";
  }

  @RequestMapping("find")
  public List<Answer> find(@RequestParam String keys) {
    return answerService.findByKeys(keys);
  }


}

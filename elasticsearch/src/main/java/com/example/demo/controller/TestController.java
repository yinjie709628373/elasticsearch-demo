package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.Entity.Article;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es")
public class TestController {
    @Autowired
    private TransportClient transportClient;

    @RequestMapping("add")
    public String add() {
        Article employee = new Article();
        employee.setAttUrl("123");
        employee.setContext("54654");
        employee.setGeographicalLocation("schj");
        employee.setGrade("123321");
        transportClient.prepareIndex("cc1", "cc1").setSource(JSON.toJSONString(employee),XContentType.JSON).get();
        System.err.println("add a obj");
        return "success";
    }


}

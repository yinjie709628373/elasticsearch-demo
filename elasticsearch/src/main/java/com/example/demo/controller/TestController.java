package com.example.demo.controller;

import com.example.demo.Entity.Employee;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.AliasQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("es")
public class TestController {
@Autowired
    private  TransportClient  transportClient;

    //省去service逻辑
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @RequestMapping("add")
    public String add() {
        Employee employee = new Employee();
        employee.setFirstName("xuxu");
        employee.setLastName("zh");
        employee.setAge(26);
        employee.setAbout("i am in peking");
        transportClient.prepareIndex("cc","cc","1").setSource(employee.toString(), XContentType.JSON);
        System.err.println("add a obj");
        return "success";
    }

    @RequestMapping("get")
    public Object get() {
        return  transportClient.prepareGet("cc","cc","1").get().getSource();
    }



}

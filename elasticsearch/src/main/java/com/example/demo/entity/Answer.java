package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "adminanswer", type = "answer")
@Data
public class Answer implements Serializable {

  private static final long serialVersionUID = 4533293713700337519L;
  @Id
  private String id;

  @Field(type = FieldType.Text)
  private String titles;

  @Field(type = FieldType.Date)
  private Date createTime;

  @Field(type = FieldType.Text)
  private String contents;


}

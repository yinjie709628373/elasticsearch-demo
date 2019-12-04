package com.example.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author linzhiqiang
 * @date 2018/5/16
 */
@Getter
@Setter
public class Article implements Serializable {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * 资源所属分类
   */
  private String resourceClassification;

  /**
   * 资源编号
   */
  private String resourceNumber;

  /**
   * 资源类别
   */
  private String resourceType;

  /**
   * 资源名字
   */
  private String resourceName;

  /**
   * 资源标签 以逗号隔开
   */
  private String resourceLable;

  /**
   * 地理位置
   */
  private String geographicalLocation;

  /**
   * 文化资源数量
   */
  private Integer resourceQuantity;

  /**
   * 区域
   */
  private String region;

  /**
   * 年代
   */
  private String years;

  /**
   * 占地面积
   */
  private String areaCovered;

  /**
   * 等级
   */
  private String grade;

  /**
   * 创建时间
   */
  private LocalDateTime createTime;

  /**
   * 最后编辑时间
   */
  private LocalDateTime lastUpdate;

  /**
   * 附件存储路径
   */
  private String attUrl;

  /**
   * 图片路径集合
   */
  private String imgUrl;

  /**
   * 正文内容
   */
  private String context;
}

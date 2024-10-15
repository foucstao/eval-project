package com.base.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class SysTest extends BaseEntity {

		/** */
     private Long id;
     
		/** 名字*/
     private String name;
     
		/** 年龄*/
     private Integer age;
     
		/** 备注*/
     private String remark;
     
}
package com.base.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class SysDictType extends BaseEntity {

	private Long id;

	private String dictName;

	private String dictType;

	private String remark;

}
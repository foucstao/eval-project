package com.base.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class SysDictData extends BaseEntity {

	private Long id;

	private Integer dictSort;

	private String dictLabel;

	private String dictValue;

	private String dictType;

	private String remark;

}
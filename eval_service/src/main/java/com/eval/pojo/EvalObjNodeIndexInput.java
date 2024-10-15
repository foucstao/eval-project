package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalObjNodeIndexInput extends BaseEntity {

	private Long id;

	private String evalInstance;

	private String evalObjCode;

	private String nodeCode;

	private String indexCode;

	private Double inputValue;

	private String pcode;

	private String nodeType;

	private String relationType;
}
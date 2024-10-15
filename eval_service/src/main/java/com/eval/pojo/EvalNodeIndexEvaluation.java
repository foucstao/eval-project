package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalNodeIndexEvaluation extends BaseEntity {

	private Long id;
	private String evalInstance;
	private String indexModel;
	private String evalObjCode;
	private String nodeCode;
	private String indexCode;
	private Double inputValue;
	private String membership;
	private String rating;
	private Double evalValue;
	private String description;

}
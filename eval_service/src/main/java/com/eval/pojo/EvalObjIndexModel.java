package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalObjIndexModel extends BaseEntity {

	private Long id;

	private String evalInstance;

	private String evalObjCode;

	private String indexModel;

	private String importanceVersion; // importance matrix version;

	private String clusterFunction; // cluster_function: max, min

	private String rateVersion;

	private String institution;

}
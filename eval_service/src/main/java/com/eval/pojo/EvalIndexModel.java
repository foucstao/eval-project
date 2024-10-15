package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalIndexModel {
	// CREATE TABLE `eval_index_model` (
	// `id` int NOT NULL AUTO_INCREMENT,
	// `index_model` varchar(100) NOT NULL,
	// `node_type` varchar(100) NOT NULL,
	// `index_code` varchar(100) DEFAULT NULL,
	// PRIMARY KEY (`id`)
	// )

	private Long id;

	private String indexModel;

	private String nodeType;

	private String indexCode;

	private Double weight;

	private String membership;

}
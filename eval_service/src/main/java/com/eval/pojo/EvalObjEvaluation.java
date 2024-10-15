package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalObjEvaluation extends BaseEntity {
	// CREATE TABLE `eval_obj_evaluation` (
	// `id` int NOT NULL AUTO_INCREMENT,
	// `eval_obj_code` varchar(100) NOT NULL,
	// `rating` varchar(50) DEFAULT NULL,
	// `eval_value` double DEFAULT NULL,
	// `description` varchar(100) DEFAULT NULL,
	// PRIMARY KEY (`id`)
	// )

	private Long id;
	private String evalInstance;
	private String evalObjCode;
	private String rating;
	private Double evalValue;
	private String description;

}
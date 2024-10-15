package com.eval.pojo;

import com.base.common.BaseDo;
import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalObject extends BaseEntity {
	// CREATE TABLE `eval_object` (
	// `oid` int NOT NULL AUTO_INCREMENT,
	// `eval_obj` varchar(100) DEFAULT NULL,
	// `eval_code` varchar(100) NOT NULL,
	// `eval_method` varchar(100) DEFAULT NULL,
	// `description` varchar(100) DEFAULT NULL,
	// PRIMARY KEY (`oid`),
	// UNIQUE KEY `eval_code` (`eval_code`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

	private Long id;

	private String evalObj;

	private String evalCode;

	private String evalMethod;

	private String description;

	private String institution;

}
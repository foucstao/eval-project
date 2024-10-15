package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class EvalObjNodeLink extends BaseEntity {
	// CREATE TABLE `evalobj_node_link` (
	// `id` int NOT NULL AUTO_INCREMENT,
	// `eval_obj_code` varchar(100) NOT NULL,
	// `node_code` varchar(100) NOT NULL,
	// `node_name` varchar(100) DEFAULT NULL,
	// `node_type` varchar(100) DEFAULT NULL,
	// PRIMARY KEY (`id`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

	private Long id;

	private String evalObjCode;

	private String nodeCode;

	private String nodeName;

	private String nodeType;

	private String institution;

}
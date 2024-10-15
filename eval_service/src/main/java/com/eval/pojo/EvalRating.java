package com.eval.pojo;

import lombok.Data;

@Data
public class EvalRating {
	// CREATE TABLE `eval_grade` (
	// `gid` int NOT NULL AUTO_INCREMENT,
	// `code` varchar(100) NOT NULL,
	// `name` varchar(100) DEFAULT NULL,
	// `description` varchar(100) DEFAULT NULL,
	// `value` double DEFAULT NULL,
	// `version` varchar(45) DEFAULT 'v1',
	// PRIMARY KEY (`gid`),
	// UNIQUE KEY `code` (`code`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

	private Long id;

	private String code;

	private String name;

	private String description;

	private Double value;

	private String version;

}
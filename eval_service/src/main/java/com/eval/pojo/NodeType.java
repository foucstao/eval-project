package com.eval.pojo;

import lombok.Data;

@Data
public class NodeType {
	// CREATE TABLE `resource_type` (
	// `rtid` int NOT NULL AUTO_INCREMENT,
	// `code` varchar(100) NOT NULL,
	// `name` varchar(100) DEFAULT NULL,
	// `description` varchar(100) DEFAULT NULL,
	// PRIMARY KEY (`rtid`),
	// UNIQUE KEY `code` (`code`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

	private Long id;

	private String code; // MACHINE ...

	private String name;

	private String relationType;

	private String description;

}
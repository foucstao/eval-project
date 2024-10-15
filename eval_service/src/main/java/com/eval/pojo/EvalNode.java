package com.eval.pojo;

import java.util.ArrayList;
import java.util.List;

import com.base.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvalNode extends BaseEntity {
	// CREATE TABLE `eval_node` (
	// `nid` int NOT NULL AUTO_INCREMENT,
	// `pid` int DEFAULT NULL COMMENT 'parent id',
	// `source` varchar(100) DEFAULT NULL COMMENT 'source node id',
	// `sink` varchar(100) DEFAULT NULL COMMENT 'target node id',
	// `node_type` varchar(100) DEFAULT NULL,
	// `code` varchar(100) NOT NULL,
	// `name` varchar(100) DEFAULT NULL,
	// `is_leaf` tinyint DEFAULT NULL COMMENT '0:not leaf, 1: is leaf node',
	// `description` varchar(100) DEFAULT NULL,
	// `relation_type` varchar(45) NOT NULL COMMENT 'two types: relation, thing',
	// PRIMARY KEY (`nid`),
	// UNIQUE KEY `code` (`code`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

	private Long id;

	private Long pid;

	private String code;

	private String pcode;

	private String name;

	private String source;

	private String sink;

	private String nodeType;

	private String relationType; // two type: thing, relation

	private Boolean isLeaf;

	private String description;

	private String institution;

	private List<EvalNode> children = new ArrayList<EvalNode>();

}
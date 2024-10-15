package com.eval.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class IndexImportance {
	// CREATE TABLE `index_importance` (
	// `imid` int NOT NULL AUTO_INCREMENT,
	// `index_first` varchar(45) NOT NULL,
	// `index_second` varchar(45) NOT NULL,
	// `importance` double NOT NULL COMMENT 'first vs second importance',
	// `version` varchar(45) NOT NULL DEFAULT 'v1',
	// PRIMARY KEY (`imid`)
	// ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

	private Long id;

	private String indexFirst;

	private String indexSecond;

	private Double importance;

	private String version;

}
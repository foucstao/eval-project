package com.codegen.domain;

import lombok.Data;

@Data
public class GenTable {

	private Long dbId;

	private String tableName;

	private String tableComment;

	private String className;

	private String businessName;

	private String businessNameBig;

	/** 生成包路径 */
	private String packageName;

	private String moduleName;
}

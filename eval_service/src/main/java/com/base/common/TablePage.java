package com.base.common;

import lombok.Data;


@Data
public class TablePage {

	/** 第几页 */
	private Integer pageNum;

	/** 每页显示多少条数据 */
	private Integer pageSize;
}

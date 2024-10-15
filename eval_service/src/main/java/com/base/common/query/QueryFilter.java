package com.base.common.query;

import java.util.ArrayList;
import java.util.List;

import com.base.common.TablePage;

import lombok.Data;

/**
 * @author 作者: 天涯浪子:
 * @version 创建时间：2021年8月17日 下午5:01:19
 * 
 */
@Data
public class QueryFilter {

	private TablePage tablePage;

	private List<BaseQuery> queryList = new ArrayList<BaseQuery>();

	public void addCondition(String name, String operation, String value) {
		BaseQuery baseQuery = new BaseQuery();
		baseQuery.setName(name);
		baseQuery.setOperation(operation);
		baseQuery.setValue(value);
		queryList.add(baseQuery);
	}

}

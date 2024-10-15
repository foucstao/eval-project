package com.base.common.query;

import java.util.List;

import com.base.common.utils.StringUtils;

public class QueryUtil {

	public static String queryAll(List<BaseQuery> querieList) {
		StringBuffer query = new StringBuffer();
		if (!StringUtils.isEmpty(querieList)) {
			querieList.forEach(item -> {
				if (!StringUtils.isEmpty(item.getValue())) {
					query.append(createsql(item));
				}
			});
		}
		return query.toString();
	}

	private static String createsql(BaseQuery baseQuery) {
		String name = baseQuery.getName();
		String operation = baseQuery.getOperation();
		String value = baseQuery.getValue();
		String sql = "";
		switch (operation) {
		case "like":
			sql = " and " + name + " like '%" + value + "%'";
			break;
		case "=":
			sql = " and " + name + " = '" + value + "'";
			break;
		case "<=":
			sql = " and " + name + " <= '" + value + "'";
			break;
		case ">=":
			sql = " and " + name + " >= '" + value + "'";
			break;
		case "date(>=)":
			sql = "and date_format(" + name + ",'%y%m%d') >= date_format(" + value + ",'%y%m%d')";
			break;
		case "date(<=)":
			sql = "and date_format(" + name + ",'%y%m%d') <= date_format(" + value + ",'%y%m%d')";
			break;
		default:
			break;
		}
		return sql;
	}
}

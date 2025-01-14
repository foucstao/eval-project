package com.codegen.service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.codegen.domain.GenTable;
import com.codegen.domain.GenTableCloumn;
import com.base.common.exception.BaseException;

@Component
public class DBService {

	/**
	 * 正则表达式规则: 匹配: _字母
	 */
	private static final Pattern PATTERN = Pattern.compile("_(\\w)");

	public static String dbLink = "";
	public static String username = "";
	public static String pwd = "";

	@Autowired
	private DataSource dataSource;

	/**
	 * 获取数据库连接
	 *
	 * @return 返回连接对象
	 */
	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BaseException("无法连接数据库，请检查数据源");
		}
		return conn;
	}

	/**
	 * 获取所有表信息
	 * 
	 * @throws SQLException
	 * 
	 */
	public List<GenTable> getTableList() throws SQLException {
		List<GenTable> tableList = new ArrayList<>();
		Connection conn = getConnection();
		DatabaseMetaData dbMetaData = conn.getMetaData();
		Statement state = conn.createStatement();
		ResultSet dbSet = state.executeQuery("select database();");
		String dbString = "";
		while (dbSet.next()) {
			dbString = dbSet.getString(1);
		}
		ResultSet rs = dbMetaData.getTables(dbString, null, null, new String[] { "TABLE" });
		while (rs.next()) {// ///TABLE_TYPE/REMARKS
			GenTable table = new GenTable();
			table.setTableName(rs.getString("TABLE_NAME"));
			table.setTableComment(getTableComment(rs.getString("TABLE_NAME")));
			table.setPackageName("com.test");
			tableList.add(table);
		}
		dbSet.close();
		state.close();
		conn.close();
		return tableList;
	}

	/**
	 * 获取表的中文名称
	 *
	 * @param tableName 表名
	 * @return 返回表的注释
	 */
	public String getTableComment(String tableName) throws SQLException {
		Connection conn = getConnection();
		Statement state = conn.createStatement();
		ResultSet res = state.executeQuery("select table_comment from information_schema.TABLES where TABLE_NAME = '" + tableName + "';");
		String tableNameCh = "";
		while (res.next()) {
			tableNameCh = res.getString("table_comment");
		}
		res.close();
		state.close();
		conn.close();
		return tableNameCh;
	}

	/**
	 * 获取所有列信息
	 *
	 * @param tableName 表名
	 * @return 返回属性的Field集合
	 * @throws Exception 抛出异常
	 */
	public List<GenTableCloumn> getColumnByTableName(String tableName) throws Exception {
		List<GenTableCloumn> fieldList = new ArrayList<>();
		Connection conn = getConnection();
		Statement state = conn.createStatement();

		ResultSet res = state.executeQuery("show full columns from " + tableName);
		while (res.next()) {
			String columnName = res.getString("Field");
			String type = res.getString("Type");
			String comment = res.getString("Comment");
			String nullAble = res.getString("Null");
			GenTableCloumn field = new GenTableCloumn();
			field.setName(columnName);
			field.setNameHump(lineToHump(columnName));
			field.setNameBigHump(lineToBigHump(columnName));
			field.setComment(comment);
			field.setJavaType(sqlTypeToJavaType(type));
			field.setType(type);
			if (comment.contains("|")) {
				field.setNameCn(comment.substring(0, comment.indexOf("|")));
			} else {
				field.setNameCn(comment);
			}
			field.setNullAble("YES".equals(nullAble));
			if (type.toUpperCase().contains("VARCHAR")) {
				String lengthStr = type.substring(type.indexOf("(") + 1, type.length() - 1);
				field.setLength(Integer.valueOf(lengthStr));
			} else {
				field.setLength(0);
			}
			fieldList.add(field);
		}
		res.close();
		state.close();
		conn.close();
		return fieldList;
	}

	public GenTableCloumn getPKColumnByTableName(String tableName) throws Exception {
		Connection conn = getConnection();
		ResultSet rst = conn.getMetaData().getPrimaryKeys(null, null, tableName);
		GenTableCloumn field = new GenTableCloumn();
		String pkColumnName = "";
		if (!rst.isAfterLast()) {
			rst.next();
			if (StringUtils.isEmpty(rst.getString("COLUMN_NAME"))) {
				throw new BaseException("该表未绑定主键，无法生成");
			}
			pkColumnName = rst.getString("COLUMN_NAME");
			field.setName(pkColumnName);
			field.setNameHump(lineToHump(pkColumnName));
			field.setNameBigHump(lineToBigHump(pkColumnName));
		}
		rst.close();
		conn.close();
		return field;
	}

	/**
	 * 下划线转小驼峰
	 *
	 * @param str 字符串
	 * @return 小驼峰
	 */
	private static String lineToHump(String str) {
		Matcher matcher = PATTERN.matcher(str.toLowerCase());
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 下划线转大驼峰
	 *
	 * @param str 字符串
	 * @return 大驼峰
	 */
	public static String lineToBigHump(String str) {
		String s = lineToHump(str);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 数据库类型转为 Java 类型
	 *
	 * @param sqlType 数据库类型
	 * @return 返回 Java 类型
	 */
	private static String sqlTypeToJavaType(String sqlType) {
		final String varchar = "VARCHAR";
		final String chr = "CHAR";
		final String text = "TEXT";
		final String datetime = "DATETIME";
		final String integer = "INT";
		final String aLong = "BIGINT";
		final String decimal = "DECIMAL";
		if (sqlType.toUpperCase().contains(varchar) || sqlType.toUpperCase().contains(chr) || sqlType.toUpperCase().contains(text)) {
			return "String";
		} else if (sqlType.toUpperCase().contains(datetime)) {
			return "Date";
		} else if (sqlType.toUpperCase().contains(aLong)) {
			return "Long";
		} else if (sqlType.toUpperCase().contains(integer)) {
			return "Integer";
		} else if (sqlType.toUpperCase().contains(decimal)) {
			return "BigDecimal";
		} else {
			return "String";
		}
	}
}

package com.base.pojo;

import java.util.ArrayList;
import java.util.List;

import com.base.common.BaseEntity;

import lombok.Data;

@Data
public class SysMenu extends BaseEntity {

	/** 菜单ID */
	private Long id;

	/** 路由访问路径 */
	private String path;

	/** 菜单名称 */
	private String name;

	/** 父菜单ID */
	private Long parentId;

	/** 菜单图标 */
	private String icon;

	/** 菜单类型 */
	private Integer type;

	/** 权限标识 */
	private String perms;

	/** 显示顺序 */
	private Integer sort;

	/** 是否内置页面 */
	private Integer iframe;

	/** 子菜单 */
	private List<SysMenu> children = new ArrayList<SysMenu>();
}
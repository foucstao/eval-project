package com.base.pojo;

import lombok.Data;

@Data
public class SysMenuRole {

	/** 主键标识 */
	private Long id;

	/** 角色ID */
	private Long roleId;

	/** 菜单ID数组 */
	private String menuIds;

	/** 选中的菜单ID */
	private String selectIds;
}
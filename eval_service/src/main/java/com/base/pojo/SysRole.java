package com.base.pojo;

import com.base.common.BaseEntity;
import com.base.common.excel.Excel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SysRole extends BaseEntity {

	/** 角色ID */
	private Long id;

	/** 角色名称 */
	@Excel(name = "角色名称")
	private String roleName;

	/** 标识字符 */
	@Excel(name = "标识字符")
	private String roleKey;

	/** 显示顺序 */
	@Excel(name = "显示顺序")
	private Integer roleSort;
}
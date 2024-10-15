package com.base.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SysRoleVo {
	private Long id;

	private String roleName;

	private String roleKey;

	private Integer roleSort;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	private String remark;

	private Long[] menuIds;

	private Long[] selectIds;
}

package com.base.pojo;

import com.base.common.BaseEntity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 用户ID */
	private Long id;

	/** 用户名称 */
	private String name;

	/** 用户密码 */
	private String password;

	/** 用户角色 */
	private String role;
}
package com.base.mapper;

import java.util.List;

import com.base.pojo.SysMenuRole;
import com.base.pojo.SysMenuRole;
import com.base.pojo.SysRole;

public interface SysMenuRoleMapper  {

	/**
	 * 根据id数组批量删除数据
	 *
	 * @param array id数组
	 * @return
	 */
	int deletesByPrimaryKey(Long[] array);

	/**
	 * 插入一条数据
	 *
	 * @param t 插入对象
	 * @return
	 */
	int insert(SysMenuRole t);

	/**
	 * 修改一条数据
	 *
	 * @param t 修改对象
	 * @return
	 */
	int updateByPrimaryKey(SysMenuRole t);

	/**
	 * 根据id查看数据
	 *
	 * @param id
	 * @return
	 */
	SysMenuRole selectByPrimaryKey(Long id);

	List<SysMenuRole> selectByRoleId(Long[] array);


	/**
	 * 查看所有数据，可根据名称进行模糊查询
	 *
	 * @param
	 * @return
	 */
	List<SysMenuRole> selectList(String condition);

	int deleteByRoleId(Long roleId);

	int updateByRoleId(SysMenuRole sysMenuRole);
}
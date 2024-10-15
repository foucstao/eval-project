package com.base.mapper;

import java.util.List;

import com.base.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

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
	int insert(SysUser t);

	/**
	 * 修改一条数据
	 *
	 * @param t 修改对象
	 * @return
	 */
	int updateByPrimaryKey(SysUser t);

	/**
	 * 根据id查看数据
	 *
	 * @param id
	 * @return
	 */
	SysUser selectByPrimaryKey(Long id);

	/**
	 * 查看所有数据，可根据名称进行模糊查询
	 *
	 * @param
	 * @return
	 */
	List<SysUser> selectList(String condition);

	SysUser selectByName(String name);

	List<SysUser> checkRole(Long[] array);
}
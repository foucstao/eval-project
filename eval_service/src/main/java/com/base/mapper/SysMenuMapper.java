package com.base.mapper;

import java.util.List;

import com.base.pojo.SysMenu;
import com.base.pojo.SysMenu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysMenuMapper{

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
	int insert(SysMenu t);

	/**
	 * 修改一条数据
	 *
	 * @param t 修改对象
	 * @return
	 */
	int updateByPrimaryKey(SysMenu t);

	/**
	 * 根据id查看数据
	 *
	 * @param id
	 * @return
	 */
	SysMenu selectByPrimaryKey(Long id);

	/**
	 * 查看所有数据，可根据名称进行模糊查询
	 *
	 * @param
	 * @return
	 */
	List<SysMenu> selectList(String condition);
	/**
	 * 查询菜单多个数据
	 * 
	 * @param array 菜单id数组
	 * @return
	 */
	List<SysMenu> selectByIds(Long[] array);

}
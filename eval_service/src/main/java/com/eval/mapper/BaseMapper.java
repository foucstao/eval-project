package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BaseMapper<T> {

	int deleteByPrimaryKey(Long id);

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
	int insert(T t);

	/**
	 * 修改一条数据
	 * 
	 * @param t 修改对象
	 * @return
	 */
	int updateByPrimaryKey(T t);

	/**
	 * 根据id查看数据
	 * 
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(Long id);

	/**
	 * 查看所有数据，可根据名称进行模糊查询
	 * 
	 * @param name
	 * @return
	 */
	List<T> selectList(String condition);

}

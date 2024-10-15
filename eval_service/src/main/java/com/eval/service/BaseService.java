package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.eval.mapper.BaseMapper;

/**
 * 
 * @author 天涯浪子
 *
 *         2020年7月1日-下午6:34:55
 * 
 */
@Service("BaseService")
public class BaseService<M> {

	@Autowired
	protected BaseMapper<M> baseMapper;

	protected Class<M> entityClass;

	public List<M> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return baseMapper.selectList(condition);
	}

	public M selectById(Long id) {
		return (M) baseMapper.selectByPrimaryKey(id);
	}

	public int insert(M m) {
		return baseMapper.insert(m);
	}

	public int update(M m) {
		return baseMapper.updateByPrimaryKey(m);
	}

	public int delete(Long[] ids) {
		return baseMapper.deletesByPrimaryKey(ids);
	}

	public int delete(Long id) {
		return baseMapper.deleteByPrimaryKey(id);
	}
}

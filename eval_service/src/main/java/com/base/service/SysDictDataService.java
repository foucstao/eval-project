package com.base.service;

import java.util.List;

import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.base.pojo.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.mapper.SysDictDataMapper;
import com.base.pojo.SysDictData;

@Service
public class SysDictDataService{

	@Autowired
	SysDictDataMapper sysDictDataMapper;

	public List<SysDictData> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return sysDictDataMapper.selectList(condition);
	}

	public SysDictData selectById(Long id) {
		return  sysDictDataMapper.selectByPrimaryKey(id);
	}

	public int insert(SysDictData m) {
		return sysDictDataMapper.insert(m);
	}

	public int update(SysDictData m) {
		return sysDictDataMapper.updateByPrimaryKey(m);
	}

	public int delete(Long[] ids) {
		return sysDictDataMapper.deletesByPrimaryKey(ids);
	}
	
	public List<SysDictData> selectDictDataByType(String dictType) {
		return sysDictDataMapper.selectDictDataByType(dictType);
	}
}

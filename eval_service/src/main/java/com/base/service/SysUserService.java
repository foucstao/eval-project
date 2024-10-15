package com.base.service;

import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;
import com.base.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.base.pojo.SysUser;

import java.util.List;

@Service
public class SysUserService {

	@Autowired
	SysUserMapper sysUserMapper;

	public List<SysUser> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return sysUserMapper.selectList(condition);
	}

	public SysUser selectById(Long id) {
		return  sysUserMapper.selectByPrimaryKey(id);
	}

	public int insert(SysUser m) {
		return sysUserMapper.insert(m);
	}

	public int update(SysUser m) {
		return sysUserMapper.updateByPrimaryKey(m);
	}

	public int delete(Long[] ids) {
		return sysUserMapper.deletesByPrimaryKey(ids);
	}
	
	public SysUser selectByName(String name) {
		return sysUserMapper.selectByName(name);
	}
}

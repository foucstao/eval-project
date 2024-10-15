package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.IndexMembershipMapper;
import com.eval.pojo.IndexMembership;

@Service
public class IndexMembershipService extends BaseService<IndexMembership> {

	@Autowired
	IndexMembershipMapper indexMapper;

	public List<IndexMembership> selectByCode(String index_code) {
		return indexMapper.selectByCode(index_code);
	}

	public List<IndexMembership> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	public List<IndexMembership> selectByCondition(IndexMembership member) {
		return indexMapper.selectByCondition(member);
	}

}

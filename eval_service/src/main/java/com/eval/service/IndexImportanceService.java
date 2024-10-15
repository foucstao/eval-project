package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.IndexImportanceMapper;
import com.eval.pojo.IndexImportance;

@Service
public class IndexImportanceService extends BaseService<IndexImportance> {

	@Autowired
	IndexImportanceMapper indexMapper;

	public List<IndexImportance> selectByIndexFirst(String index_first) {
		return indexMapper.selectByIndexFirst(index_first);
	}

	// List<IndexImportance> selectByObj(IndexImportance imp);
	public List<IndexImportance> selectByObj(IndexImportance imp) {
		return indexMapper.selectByObj(imp);
	}

	public List<IndexImportance> selectDataAll() {
		return indexMapper.selectDataAll();
	}
}

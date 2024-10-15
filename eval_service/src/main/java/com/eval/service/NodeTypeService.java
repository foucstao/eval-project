package com.eval.service;

import java.util.List;

import com.eval.pojo.EvalNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.NodeTypeMapper;
import com.eval.pojo.NodeType;

@Service
public class NodeTypeService extends BaseService<NodeType> {

	@Autowired
	NodeTypeMapper indexMapper;

	public List<NodeType> selectByCode(String code) {
		return indexMapper.selectByCode(code);
	}

	public List<NodeType> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	public List<NodeType> selectNodeByType(String relationType) {
		return indexMapper.selectNodeByType(relationType);
	}

}

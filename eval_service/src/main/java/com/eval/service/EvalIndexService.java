package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalIndexMapper;
import com.eval.pojo.EvalIndex;

@Service
public class EvalIndexService extends BaseService<EvalIndex> {

	@Autowired
	EvalIndexMapper indexMapper;

	public List<EvalIndex> selectByIndexCode(String code) {
		return indexMapper.selectByIndexCode(code);
	}

	public List<EvalIndex> selectIndexAll() {
		return indexMapper.selectIndexAll();
	}

	public List<EvalIndex> selectIndexByType(String relationType) {
		return indexMapper.selectIndexByType(relationType);
	}

	public List<EvalIndex> selectIndexByNodeType(String nodeType) {
		return indexMapper.selectIndexByNodeType(nodeType);
	}

	public List<EvalIndex> getTree(List<EvalIndex> nodeList) {
		List<EvalIndex> trees = new ArrayList<EvalIndex>();
		for (EvalIndex node : nodeList) {
			if (node.getPid() == 0) {
				trees.add(node);
			}
			for (EvalIndex it : nodeList) {
				if (it.getPid() == node.getId()) {
					node.getChildren().add(it);
				}
			}
		}
		return trees;
	}
}

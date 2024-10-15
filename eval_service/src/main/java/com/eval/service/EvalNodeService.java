package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalNodeMapper;
import com.eval.pojo.EvalNode;

@Service
public class EvalNodeService extends BaseService<EvalNode> {

	@Autowired
	EvalNodeMapper indexMapper;

	public List<EvalNode> selectByCode(String code) {
		return indexMapper.selectByCode(code);
	}

	// selectByPcode(String pcode);
	public List<EvalNode> selectByPcode(String pcode) {
		return indexMapper.selectByPcode(pcode);
	}

	// List<EvalNode> findByInstitution(String institution);
	public List<EvalNode> findByInstitution(String institution) {
		return indexMapper.findByInstitution(institution);
	}

	public List<EvalNode> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	public List<EvalNode> selectNodeByType(String relationType) {
		return indexMapper.selectNodeByType(relationType);
	}

	// selectNodesByObj
	public List<EvalNode> selectNodesByObj(EvalNode node) {
		return indexMapper.selectNodesByObj(node);
	}

	public List<EvalNode> getTree(List<EvalNode> nodeList) {
		List<EvalNode> trees = new ArrayList<EvalNode>();
		for (EvalNode node : nodeList) {
			if (node.getPid() == 0) {
				trees.add(node);
			}
			for (EvalNode it : nodeList) {
				if (it.getPid() == node.getId()) {
					node.getChildren().add(it);
				}
			}
		}
		return trees;
	}

	public int updatePidByInstitution(String institution) {
		return indexMapper.updatePidByInstitution(institution);
	}
}

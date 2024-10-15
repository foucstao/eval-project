package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalObjNodeLinkMapper;
import com.eval.pojo.EvalObjNodeLink;
import com.eval.vo.EvalObjNodeVo;

@Service
public class EvalObjNodeLinkService extends BaseService<EvalObjNodeLink> {

	@Autowired
	EvalObjNodeLinkMapper indexMapper;

	public List<EvalObjNodeLink> selectByCode(String code) {
		return indexMapper.selectByCode(code);
	}

	// all data
	public List<EvalObjNodeVo> selectAllJoinNode(String code) {
		return indexMapper.selectAllJoinNode(code);
	}

	public List<EvalObjNodeLink> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	// findByInstitution(String institution);
	public List<EvalObjNodeLink> findByInstitution(String institution) {
		return indexMapper.findByInstitution(institution);
	}

	// get tree
	public List<EvalObjNodeVo> getTree(List<EvalObjNodeVo> nodeList) {
		List<EvalObjNodeVo> trees = new ArrayList<EvalObjNodeVo>();
		for (EvalObjNodeVo node : nodeList) {
			if (node.getPid() == 0) {
				trees.add(node);
			}
			for (EvalObjNodeVo it : nodeList) {
				if (it.getPid() == node.getId()) {
					node.getChildren().add(it);
				}
			}
		}
		return trees;
	}

	public List<EvalObjNodeVo> genNodeTree(String eval_obj) {
		List<EvalObjNodeVo> indexList = selectAllJoinNode(eval_obj);
		return getTree(indexList);
	}

	// selectByCondition
	public List<EvalObjNodeLink> selectByCondition(EvalObjNodeLink objNode) {
		return indexMapper.selectByCondition(objNode);
	}
}

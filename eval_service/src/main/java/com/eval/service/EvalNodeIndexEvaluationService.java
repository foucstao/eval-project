package com.eval.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalNodeIndexEvaluationMapper;
import com.eval.pojo.EvalNodeIndexEvaluation;
import com.eval.pojo.EvalObjIndexModel;
import com.eval.vo.EvalNodeIndexEvaluationVo;
import com.eval.vo.NodeTypeEvalVo;

@Service
public class EvalNodeIndexEvaluationService extends BaseService<EvalNodeIndexEvaluation> {

	@Autowired
	EvalNodeIndexEvaluationMapper indexMapper;

	public List<EvalNodeIndexEvaluation> selectByCode(String code) {
		return indexMapper.selectByCode(code);
	}

	public List<EvalNodeIndexEvaluation> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	public List<EvalNodeIndexEvaluationVo> selectAllNodesJoinIndexesByEvalObj(String eval_obj) {
		return indexMapper.selectAllNodesJoinIndexesByEvalObj(eval_obj);
	}

	//创建一个新的service方法，改变输出的数据结构
	public List<EvalNodeIndexEvaluation> selectByInstanceSimple(String instance){
		return indexMapper.selectByInstanceSimple(instance);
	}
	public List<EvalNodeIndexEvaluationVo> selectNodeIndexesByInstance(String evalInstance) {
		return indexMapper.selectNodeIndexesByInstance(evalInstance);
	}


	public List<EvalNodeIndexEvaluation> selectByCondition(EvalNodeIndexEvaluation enie) {
		return indexMapper.selectByCondition(enie);
	}

	public List<NodeTypeEvalVo> selectEvalGroupbyNodeTypeRating(EvalObjIndexModel model) {
		return indexMapper.selectEvalGroupbyNodeTypeRating(model);
	}

	public List<NodeTypeEvalVo> evalOnNodeTypeRating(EvalObjIndexModel model) {
		return indexMapper.evalOnNodeTypeRating(model);
	}

	public List<EvalNodeIndexEvaluationVo> getTree(List<EvalNodeIndexEvaluationVo> nodeList) {
		List<EvalNodeIndexEvaluationVo> trees = new ArrayList<EvalNodeIndexEvaluationVo>();
		for (EvalNodeIndexEvaluationVo node : nodeList) {
			if (node.getPid() == 0) {
				trees.add(node);
			}
			for (EvalNodeIndexEvaluationVo it : nodeList) {
				if (it.getPid() == node.getId()) {
					node.getChildren().add(it);
				}
			}
		}
		return trees;
	}
}

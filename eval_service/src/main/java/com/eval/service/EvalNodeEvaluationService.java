package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//pojo 表示数据结构  mapper表示映射
import com.eval.mapper.EvalNodeEvaluationMapper;
import com.eval.pojo.EvalNodeEvaluation;

@Service
public class EvalNodeEvaluationService extends BaseService<EvalNodeEvaluation> {

	@Autowired
	EvalNodeEvaluationMapper indexMapper;

	public List<EvalNodeEvaluation> selectByObj(String obj) {

		return indexMapper.selectByObj(obj);
	}

	public List<EvalNodeEvaluation> selectByEvalInstance(String evalInstance) {
		return indexMapper.selectByEvalInstance(evalInstance);
	}

	public List<EvalNodeEvaluation> selectByCode(String code) {

		return indexMapper.selectByCode(code);
	}

	public List<EvalNodeEvaluation> selectByCondition(EvalNodeEvaluation nodeEvaluation) {

		return indexMapper.selectByCondition(nodeEvaluation);
	}

}

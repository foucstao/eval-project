package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//pojo 表示数据结构  mapper表示映射
import com.eval.mapper.EvalObjEvaluationMapper;
import com.eval.pojo.EvalObjEvaluation;
import com.eval.pojo.EvalObjIndexModel;

@Service
public class EvalObjEvaluationService extends BaseService<EvalObjEvaluation> {

	@Autowired
	EvalObjEvaluationMapper indexMapper;

	public List<EvalObjEvaluation> selectByObj(String obj) {

		return indexMapper.selectByObj(obj);
	}

	public List<EvalObjEvaluation> selectByInstance(String evalInstance) {

		return indexMapper.selectByInstance(evalInstance);
	}

	public List<EvalObjEvaluation> selectObjMinEvalGroupbyRating(String evalInstance) {

		return indexMapper.selectObjMinEvalGroupbyRating(evalInstance);
	}

	public List<EvalObjEvaluation> selectObjMaxEvalGroupbyRating(String evalInstance) {

		return indexMapper.selectObjMaxEvalGroupbyRating(evalInstance);
	}

	// List<EvalObjEvaluation> selectEvalGroupbyObjRating(EvalObjIndexModel model);
	public List<EvalObjEvaluation> selectEvalGroupbyObjRating(EvalObjIndexModel model) {

		return indexMapper.selectEvalGroupbyObjRating(model);
	}

	// List<EvalObjEvaluation> selectByCondition(EvalObjEvaluation objEval);
	public List<EvalObjEvaluation> selectByCondition(EvalObjEvaluation objEval) {

		return indexMapper.selectByCondition(objEval);
	}

}

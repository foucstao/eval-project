package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//pojo 表示数据结构  mapper表示映射
import com.eval.mapper.ObjNodeTypeEvaluationMapper;
import com.eval.pojo.ObjNodeTypeEvaluation;

@Service
public class ObjNodeTypeEvaluationService extends BaseService<ObjNodeTypeEvaluation> {

	@Autowired
	ObjNodeTypeEvaluationMapper indexMapper;

	public List<ObjNodeTypeEvaluation> selectByObj(String obj) {

		return indexMapper.selectByObj(obj);
	}

	public List<ObjNodeTypeEvaluation> selectByEvalInstance(String evalInstance) {
		return indexMapper.selectByEvalInstance(evalInstance);
	}

	public List<ObjNodeTypeEvaluation> selectByCode(String code) {

		return indexMapper.selectByCode(code);
	}

	public List<ObjNodeTypeEvaluation> selectByCondition(ObjNodeTypeEvaluation typeEvaluation) {

		return indexMapper.selectByCondition(typeEvaluation);
	}

}

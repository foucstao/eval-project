package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//pojo 表示数据结构  mapper表示映射
import com.eval.mapper.EvalIndexModelMapper;
import com.eval.pojo.EvalIndexModel;

@Service
public class EvalIndexModelService extends BaseService<EvalIndexModel> {

	@Autowired
	EvalIndexModelMapper indexMapper;

	public List<EvalIndexModel> selectByIndexModel(String indexModel) {

		return indexMapper.selectByIndexModel(indexModel);
	}

	public List<EvalIndexModel> selectByIndexModelAndNodeType(String indexModel, String nodeType) {

		return indexMapper.selectByIndexModelAndNodeType(indexModel, nodeType);
	}

	public List<EvalIndexModel> selectModelByObj(EvalIndexModel model) {

		return indexMapper.selectModelByObj(model);
	}

}

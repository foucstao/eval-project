package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalObjIndexModelMapper;
import com.eval.pojo.EvalObjIndexModel;

@Service
public class EvalObjIndexModelService extends BaseService<EvalObjIndexModel> {

	@Autowired
	EvalObjIndexModelMapper indexMapper;

	public List<EvalObjIndexModel> selectDataAll() {

		return indexMapper.selectDataAll();
	}

	public List<EvalObjIndexModel> selectByObj(String obj) {

		return indexMapper.selectByObj(obj);
	}

	public EvalObjIndexModel selectByInstance(String evalInstance) {

		return indexMapper.selectByInstance(evalInstance);
	}

	// List<EvalObjIndexModel> findByInstitution(String institution);

	public List<EvalObjIndexModel> findByInstitution(String institution) {

		return indexMapper.findByInstitution(institution);
	}

	public List<EvalObjIndexModel> findByCondition(EvalObjIndexModel indexModel) {

		return indexMapper.selectByCondition(indexModel);
	}

}

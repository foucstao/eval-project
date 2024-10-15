package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//pojo 表示数据结构  mapper表示映射
import com.eval.mapper.EvalObjectMapper;
import com.eval.pojo.EvalObject;

@Service
public class EvalObjectService extends BaseService<EvalObject> {

	@Autowired
	EvalObjectMapper indexMapper;

	public List<EvalObject> selectByCode(String eval_code) {

		return indexMapper.selectByCode(eval_code);
	}

	public List<EvalObject> selectDataAll() {
		return indexMapper.selectDataAll();
	}

	// List<EvalObject> findByInstitution(institution);

	public List<EvalObject> findByInstitution(String institution) {
		return indexMapper.findByInstitution(institution);
	}
}

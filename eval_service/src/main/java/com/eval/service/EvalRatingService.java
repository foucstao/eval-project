package com.eval.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eval.mapper.EvalRatingMapper;
import com.eval.pojo.EvalRating;

@Service
public class EvalRatingService extends BaseService<EvalRating> {

	@Autowired
	EvalRatingMapper indexMapper;

	public List<EvalRating> selectByCode(String code) {
		return indexMapper.selectByCode(code);
	}

	public List<EvalRating> selectDataAll() {
		return indexMapper.selectDataAll();
	}
	// List<EvalRating> selectByVersion(String version);

	public List<EvalRating> selectByVersion(String version) {
		return indexMapper.selectByVersion(version);
	}
}

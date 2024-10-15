package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.eval.pojo.EvalRating;

@Mapper
public interface EvalRatingMapper extends BaseMapper<EvalRating> {

	List<EvalRating> selectByCode(String code);

	List<EvalRating> selectDataAll();

	// selectByVersion
	List<EvalRating> selectByVersion(String version);

}
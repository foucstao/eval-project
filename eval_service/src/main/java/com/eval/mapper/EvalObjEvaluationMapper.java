package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.EvalObjEvaluation;
import com.eval.pojo.EvalObjIndexModel;

@Mapper
@Repository
public interface EvalObjEvaluationMapper extends BaseMapper<EvalObjEvaluation> {

	List<EvalObjEvaluation> selectByObj(String obj);

	List<EvalObjEvaluation> selectByInstance(String evalInstance);

	List<EvalObjEvaluation> selectObjMinEvalGroupbyRating(String evalInstance);

	List<EvalObjEvaluation> selectObjMaxEvalGroupbyRating(String evalInstance);

	List<EvalObjEvaluation> selectEvalGroupbyObjRating(EvalObjIndexModel model);

	List<EvalObjEvaluation> selectByCondition(EvalObjEvaluation objEval);

}
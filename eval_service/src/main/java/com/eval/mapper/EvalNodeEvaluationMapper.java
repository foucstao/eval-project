package com.eval.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.eval.pojo.EvalNodeEvaluation;

@Mapper
@Repository
public interface EvalNodeEvaluationMapper extends BaseMapper<EvalNodeEvaluation> {

	List<EvalNodeEvaluation> selectByCode(String code);

	List<EvalNodeEvaluation> selectByObj(String obj);

	List<EvalNodeEvaluation> selectByEvalInstance(String evalInstance);

	List<EvalNodeEvaluation> selectByCondition(EvalNodeEvaluation nodeEvaluation);

}